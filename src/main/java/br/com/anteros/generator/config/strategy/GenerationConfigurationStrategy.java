package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.MVC_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.PERSISTENCE_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.PERSISTENCE_CONFIGURATION_WITH_PROPERTIES_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.PROJECT_DISPLAY_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PROPERTIES_FILE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerFileWriter;
import freemarker.template.Template;

public class GenerationConfigurationStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating Java configuration");
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), "config"));
		AnterosFreeMarkerFileWriter out = null;

		Template templateMvc = config.getConfiguration().getTemplate(MVC_CONFIGURATION_TEMPLATE);
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File mvcFile = new File(config.getPackageDirectory() + File.separatorChar + "config" + File.separatorChar
				+ "MvcConfiguration.java");
		if (!mvcFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(mvcFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");

			dataModel.put(PROJECT_DISPLAY_NAME, config.getProjectDisplayName());

			templateMvc.process(dataModel, out);
			out.flush();
			out.close();
		}

		if (StringUtils.isNotEmpty(config.getPropertiesFile())) {
			Template templatePersistence = config.getConfiguration()
					.getTemplate(PERSISTENCE_CONFIGURATION_WITH_PROPERTIES_TEMPLATE);
			dataModel = new HashMap<String, Object>();
			File persistenceFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
					+ File.separatorChar + "PersistenceConfiguration.java");
			if (!persistenceFile.exists()) {
				out = new AnterosFreeMarkerFileWriter(persistenceFile);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
				dataModel.put(PROPERTIES_FILE, config.getPropertiesFile());
				dataModel.put(PACKAGE_SCAN_ENTITY, config.getPackageScanEntity());
				templatePersistence.process(dataModel, out);
				out.flush();
				out.close();
			}
		} else {
			Template templatePersistence = config.getConfiguration().getTemplate(PERSISTENCE_CONFIGURATION_TEMPLATE);
			dataModel = new HashMap<String, Object>();
			File persistenceFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
					+ File.separatorChar + "PersistenceConfiguration.java");
			if (!persistenceFile.exists()) {
				out = new AnterosFreeMarkerFileWriter(persistenceFile);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
				dataModel.put(PACKAGE_SCAN_ENTITY, config.getPackageScanEntity());
				templatePersistence.process(dataModel, out);
				out.flush();
				out.close();
			}
		}

	}

}
