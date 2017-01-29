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
import br.com.anteros.core.utils.ReflectionUtils;
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
		List<JavaClass> allEntityClasses = getAllEntityClasses(config.isGenerateForAbstractClass(),
				config.getPackageScanEntity(), config.getClassPathURLs());
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(new AnterosFreeMarkerTemplateLoader(baseClassLoader, TEMPLATES));
		FileUtils.forceMkdir(new File(config.getPackageDirectory()));
		config.setConfiguration(configuration);

		for (JavaClass jc : allEntityClasses) {
			config.getGenerationLog().log(jc.getName());
			config.setClazz(jc);
			if (config.isIncludeSecurity()) {
				if (config.isGenerateService()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_SECURITY_SERVICE).generate(config);
				}
				if (config.isGenerateController()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_CONTROLLER).generate(config);
				}
				if (config.isGenerateRepository()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_REPOSITORY).generate(config);
				}
				if (config.isGenerateJavaConfiguration()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_SECURITY_CONFIGURATION).generate(config);
				}
			} else {
				if (config.isGenerateService()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_SERVICE).generate(config);
				}
				if (config.isGenerateJavaConfiguration()) {
					AnterosGenerator.create(AnterosGenerator.GENERATION_CONFIGURATION).generate(config);
				}
			}
		}
	}

	private List<JavaClass> getAllEntityClasses(boolean generateForAbstractClass, String sourcesToScanEntities,
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
			JavaClass javaClass = docBuilder.getClassByName(cl.getName());
			if (javaClass != null)
				result.add(javaClass);
		}
		return result;
	}
}
