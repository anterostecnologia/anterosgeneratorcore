package br.com.anteros.generator;

import static br.com.anteros.generator.AnterosGenerationConstants.TEMPLATES;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.library.ClassLibraryBuilder;
import com.thoughtworks.qdox.library.SortedClassLibraryBuilder;
import com.thoughtworks.qdox.model.JavaClass;

import br.com.anteros.core.scanner.ClassFilter;
import br.com.anteros.core.scanner.ClassPathScanner;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerTemplateLoader;
import br.com.anteros.persistence.metadata.annotation.Entity;
import freemarker.template.Configuration;

public class AnterosGeneratorManager {

	private static AnterosGeneratorManager instance;

	private AnterosGeneratorManager() {
	}

	public static AnterosGeneratorManager getInstance() {
		if (instance == null) {
			instance = new AnterosGeneratorManager();
		}

		return instance;
	}

	public void generate(AnterosGenerationConfig config, Class<?> baseClassLoader) throws Exception {
		List<JavaClass> allEntityClasses = getAllEntityClasses(config,config.isGenerateForAbstractClass(),
				config.getPackageScanEntity(), config.getClassPathURLs());
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(new AnterosFreeMarkerTemplateLoader(baseClassLoader, TEMPLATES));
		FileUtils.forceMkdir(new File(config.getPackageDirectory()));
		config.setConfiguration(configuration);

		if (config.isIncludeSecurity()) {
			if (config.isGenerateJavaConfiguration()) {
				AnterosGenerator.create(AnterosGenerator.GENERATION_APP_CONFIGURATION).generate(config);
				AnterosGenerator.create(AnterosGenerator.GENERATION_MVC_CONFIGURATION).generate(config);
				AnterosGenerator.create(AnterosGenerator.GENERATION_RESOURCE_PERSISTENCE_CONFIGURATION)
						.generate(config);
				AnterosGenerator.create(AnterosGenerator.GENERATION_SECURITY_CONFIGURATION).generate(config);
			}
		} else {
			if (config.isGenerateJavaConfiguration()) {
				AnterosGenerator.create(AnterosGenerator.GENERATION_APP_CONFIGURATION).generate(config);
				AnterosGenerator.create(AnterosGenerator.GENERATION_MVC_CONFIGURATION).generate(config);
				AnterosGenerator.create(AnterosGenerator.GENERATION_RESOURCE_PERSISTENCE_CONFIGURATION)
						.generate(config);
			}
		}

		if (config.isGenerateExceptionHandler()) {
			AnterosGenerator.create(AnterosGenerator.GENERATION_EXCEPTION_HANDLER).generate(config);
		}

		for (JavaClass jc : allEntityClasses) {
			config.getGenerationLog().log(jc.getName());
			config.setClazz(jc);
			if (config.isIncludeSecurity()) {
				if (config.isGenerateService()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_SECURITY_SERVICE).generate(config);
				}
				if (config.isGenerateController()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_RESOURCE).generate(config);
				}

				if (config.isGenerateRepository()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_REPOSITORY).generate(config);
				}

			} else {
				if (config.isGenerateService()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_SERVICE).generate(config);
				}
				if (config.isGenerateJavaConfiguration()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_APP_CONFIGURATION).generate(config);
					AnterosGenerator.create(AnterosGenerator.GENERATION_MVC_CONFIGURATION).generate(config);
					AnterosGenerator.create(AnterosGenerator.GENERATION_RESOURCE_PERSISTENCE_CONFIGURATION)
							.generate(config);
				}
			}
		}
	}

	private List<JavaClass> getAllEntityClasses(AnterosGenerationConfig config, boolean generateForAbstractClass, String sourcesToScanEntities,
			List<URL> urls) throws IOException {
		List<JavaClass> result = new ArrayList<JavaClass>();
		URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[] {}),
				Thread.currentThread().getContextClassLoader());

		Thread.currentThread().setContextClassLoader(urlClassLoader);
		ClassLibraryBuilder libraryBuilder = new SortedClassLibraryBuilder();
		libraryBuilder.appendClassLoader(urlClassLoader);
		JavaProjectBuilder docBuilder = new JavaProjectBuilder(libraryBuilder);

		String[] packages = StringUtils.tokenizeToStringArray(sourcesToScanEntities, ", ;");
		List<Class<?>> scanClasses = ClassPathScanner
				.scanClasses(new ClassFilter().packages(packages).annotation(Entity.class));

		for (Class<?> cl : scanClasses) {
			if (Modifier.isAbstract(cl.getModifiers()) && !generateForAbstractClass) {
				continue;
			}
			
			if (config.getIncludeOnlyTheseEntitiesList().size()>0) {
				int cont=0;
				for (String includeEntity : config.getIncludeOnlyTheseEntitiesList()) {
					if (cl.getSimpleName().equals(includeEntity) || cl.getName().equals(includeEntity)) {
						cont++;
					}
				}
				if (cont==0)
					continue;
			}
			
			
			
			if (config.getExcludeEntitiesList().size()>0) {
				int cont=0;
				for (String excludeEntity : config.getExcludeEntitiesList()) {
					if (cl.getSimpleName().equals(excludeEntity) || cl.getName().equals(excludeEntity)) {
						cont++;
					}
				}
				if (cont>0)
					continue;
			}
			
			JavaClass javaClass = docBuilder.getClassByName(cl.getName());
			if (javaClass != null)
				result.add(javaClass);
		}
		return result;
	}
}
