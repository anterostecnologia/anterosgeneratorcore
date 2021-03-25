package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.NO_SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.PROPERTIES_FILE;
import static br.com.anteros.generator.AnterosGenerationConstants.RESOURCE_PERSISTENCE_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SQL;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerFileWriter;
import freemarker.template.Template;

public class GenerationResourcePersistenceConfigurationStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating Resource Persistence configuration");
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), "config"));
		AnterosFreeMarkerFileWriter out = null;

		Map<String, Object> dataModel = new HashMap<String, Object>();

		Template templatePersistence = null;
		if (SQL.equals(config.getPersistenceDatabase())){
			templatePersistence = config.getConfiguration().getTemplate(SQL + File.pathSeparator +RESOURCE_PERSISTENCE_CONFIGURATION_TEMPLATE);
		}
		else {
		 templatePersistence = config.getConfiguration().getTemplate(NO_SQL+ File.pathSeparator +RESOURCE_PERSISTENCE_CONFIGURATION_TEMPLATE);
		}
		
		
		dataModel = new HashMap<String, Object>();
		File persistenceFile = new File(config.getPackageDirectory() + File.pathSeparator + "config"
				+ File.pathSeparator + "ResourcePersistenceConfiguration.java");
		if (!persistenceFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(persistenceFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
			dataModel.put(PROPERTIES_FILE, config.getPropertiesFile());
			dataModel.put(PACKAGE_SCAN_ENTITY, config.getPackageScanEntity());
			templatePersistence.process(dataModel, out);
			out.flush();
			out.close();
		}

	}

}
