package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.BASE_PATH_API;
import static br.com.anteros.generator.AnterosGenerationConstants.CONTACT_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.DESCRIPTION_API;
import static br.com.anteros.generator.AnterosGenerationConstants.GLOBAL_METHOD_SECURITY_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.LICENSE_API;
import static br.com.anteros.generator.AnterosGenerationConstants.LICENSE_URL_API;
import static br.com.anteros.generator.AnterosGenerationConstants.NULL;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_COMPONENTS;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_TO_SCAN_JSON_DOC;
import static br.com.anteros.generator.AnterosGenerationConstants.PERSISTENCE_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.PERSISTENCE_CONFIGURATION_WITH_PROPERTIES_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.PROJECT_DISPLAY_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PROPERTIES_FILE;
import static br.com.anteros.generator.AnterosGenerationConstants.SECURITY_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SECURITY_CONFIGURATION_WITH_PROPERTIES_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SECURITY_MVC_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.TERMS_OF_SERVICE_URL;
import static br.com.anteros.generator.AnterosGenerationConstants.TITLE_API;
import static br.com.anteros.generator.AnterosGenerationConstants.VERSION_API;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerFileWriter;
import freemarker.template.Template;

public class GenerationSecurityConfigurationStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating Java configuration");
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), "config"));
		AnterosFreeMarkerFileWriter out = null;
		Template templateGlobalMethod = config.getConfiguration().getTemplate(GLOBAL_METHOD_SECURITY_CONFIGURATION_TEMPLATE);
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File globalMethodFile = new File(config.getPackageDirectory() + File.separatorChar + "config" + File.separatorChar
				+ "GlobalMethodSecurityConfiguration.java");
		if (!globalMethodFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(globalMethodFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
			templateGlobalMethod.process(dataModel, out);
			out.flush();
			out.close();
		}

		if (StringUtils.isNotEmpty(config.getPropertiesFile())) {
			Template templatePersistence = config.getConfiguration()
					.getTemplate(PERSISTENCE_CONFIGURATION_WITH_PROPERTIES_TEMPLATE);
			dataModel = new HashMap<String, Object>();
			File persistenceFile = new File(config.getPackageDirectory() + File.separatorChar + "config" + File.separatorChar
					+ "PersistenceConfiguration.java");
			if (!persistenceFile.exists()) {
				out = new AnterosFreeMarkerFileWriter(persistenceFile);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
				dataModel.put(PROPERTIES_FILE, config.getPropertiesFile());
				dataModel.put(PACKAGE_SCAN_ENTITY, config.getPackageScanEntity());
				templatePersistence.process(dataModel, out);
				out.flush();
				out.close();
			}

			Template templateSecurity = config.getConfiguration().getTemplate(SECURITY_CONFIGURATION_WITH_PROPERTIES_TEMPLATE);
			dataModel = new HashMap<String, Object>();
			File securityConfigutationFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
					+ File.separatorChar + "SecurityConfiguration.java");
			if (!securityConfigutationFile.exists()) {
				out = new AnterosFreeMarkerFileWriter(securityConfigutationFile);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
				dataModel.put(PROPERTIES_FILE, config.getPropertiesFile());
				templateSecurity.process(dataModel, out);
				out.flush();
				out.close();
			}

		} else {
			Template templatePersistence = config.getConfiguration().getTemplate(PERSISTENCE_CONFIGURATION_TEMPLATE);
			dataModel = new HashMap<String, Object>();
			File persistenceFile = new File(config.getPackageDirectory() + File.separatorChar + "config" + File.separatorChar
					+ "PersistenceConfiguration.java");
			if (!persistenceFile.exists()) {
				out = new AnterosFreeMarkerFileWriter(persistenceFile);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
				dataModel.put(PACKAGE_SCAN_ENTITY, config.getPackageScanEntity());
				templatePersistence.process(dataModel, out);
				out.flush();
				out.close();
			}

			Template templateSecurity = config.getConfiguration().getTemplate(SECURITY_CONFIGURATION_TEMPLATE);
			dataModel = new HashMap<String, Object>();
			File securityConfigutationFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
					+ File.separatorChar + "SecurityConfiguration.java");
			if (!securityConfigutationFile.exists()) {
				out = new AnterosFreeMarkerFileWriter(securityConfigutationFile);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
				templateSecurity.process(dataModel, out);
				out.flush();
				out.close();
			}
		}

		Template templateMvc = config.getConfiguration().getTemplate(SECURITY_MVC_CONFIGURATION_TEMPLATE);
		dataModel = new HashMap<String, Object>();
		File persistenceFile = new File(config.getPackageDirectory() + File.separatorChar + "config" + File.separatorChar
				+ "SecurityMvcConfiguration.java");
		if (!persistenceFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(persistenceFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
			String packages = "";
			boolean appendDelimiter = false;
			for (String pck : config.getPackageScanComponentsList()) {
				if (appendDelimiter)
					packages += ",";
				packages += '"' + pck + '"';
				appendDelimiter = true;
			}
			dataModel.put(PACKAGE_SCAN_COMPONENTS, packages);
			dataModel.put(PROJECT_DISPLAY_NAME, config.getProjectDisplayName());

			templateMvc.process(dataModel, out);
			out.flush();
			out.close();
		}

	}
}
