package br.com.anteros.generator;

import static br.com.anteros.generator.AnterosGenerationConstants.TEMPLATES;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

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
		JavaProjectBuilder builder = getBuilder(config.getSourcesToScanEntities());
		Configuration configuration = new Configuration();
		configuration.setTemplateLoader(new AnterosFreeMarkerTemplateLoader(baseClassLoader, TEMPLATES));
		FileUtils.forceMkdir(new File(config.getPackageDirectory()));		
		config.setConfiguration(configuration);

		for (JavaSource j : builder.getSources()) {
			List<JavaClass> classes = j.getClasses();
			for (JavaClass jc : classes) {
				if (isGenerateForClass(jc, config.getPackageBaseList()) && isContainsAnnotation(jc, Entity.class)) {
					config.setClazz(jc);	
					if (config.isIncludeSecurity()) {
						AnterosGenerator.create(AnterosGenerator.GENERATION_SECURITY_SERVICE).generate(config);
						AnterosGenerator.create(AnterosGenerator.GENERATION_CONTROLLER).generate(config);
						if (config.isGenerateRepository()) {
							AnterosGenerator.create(AnterosGenerator.GENERATION_REPOSITORY).generate(config);
						}
						if (config.isGenerateJavaConfiguration()) {
							AnterosGenerator.create(AnterosGenerator.GENERATION_SECURITY_CONFIGURATION).generate(config);
						}
					} else {
						AnterosGenerator.create(AnterosGenerator.GENERATION_SERVICE).generate(config);
						if (config.isGenerateJavaConfiguration()) {
							AnterosGenerator.create(AnterosGenerator.GENERATION_CONFIGURATION).generate(config);
						}
					}
				}
			}
		}
	}

	private boolean isContainsAnnotation(JavaClass jc, Class<?> ac) {
		for (JavaAnnotation ja : jc.getAnnotations()) {
			if (ja.getType().toString().equals(ac.getName())) {
				return true;
			}
		}
		return false;
	}

	private JavaProjectBuilder getBuilder(List<String> sourcesToScanEntities) {
		JavaProjectBuilder docBuilder = new JavaProjectBuilder();
		for (String r : sourcesToScanEntities) {
			docBuilder.addSourceTree(new File(r));
		}

		return docBuilder;
	}

	private boolean isGenerateForClass(JavaClass sourceJavaClass, List<String> packageBaseList) {
		String classPackage = sourceJavaClass.getPackageName();
		if (packageBaseList != null) {
			for (String source : packageBaseList) {
				if (classPackage.startsWith(source)) {
					return true;
				}
			}
			return false;
		} else {
			return true;
		}
	}

}
