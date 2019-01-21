package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.ENCODERS_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.METHOD_SECURITY_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.NO_SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_COMPONENTS;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.PROPERTIES_FILE;
import static br.com.anteros.generator.AnterosGenerationConstants.SECURITY_PERSISTENCE_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVER_SECURITY_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.RESOURCE_SERVER_SECURITY_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.RESOURCE_ID;
import static br.com.anteros.generator.AnterosGenerationConstants.CLIENT_ID;
import static br.com.anteros.generator.AnterosGenerationConstants.CLIENT_SECRET;
import static br.com.anteros.generator.AnterosGenerationConstants.REMOTE_TOKEN_CHECK_ENDPOINT;
import static br.com.anteros.generator.AnterosGenerationConstants.SECURED_PATTERN;
import static br.com.anteros.generator.AnterosGenerationConstants.USE_ANTEROS_OAUTH2_SERVER;
import static br.com.anteros.generator.AnterosGenerationConstants.AUTHENTICATION_SERVER_SECURITY_TEMPLATE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerFileWriter;
import freemarker.template.Template;

public class GenerationSecurityConfigurationStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating Security Java configuration");
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), "config"));
		AnterosFreeMarkerFileWriter out = null;
		Template templateGlobalMethod = config.getConfiguration().getTemplate(METHOD_SECURITY_CONFIGURATION_TEMPLATE);
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File globalMethodFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
				+ File.separatorChar + "MethodSecurityConfiguration.java");
		if (!globalMethodFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(globalMethodFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
			templateGlobalMethod.process(dataModel, out);
			out.flush();
			out.close();
		}

		Template templateEncoders = config.getConfiguration().getTemplate(ENCODERS_TEMPLATE);
		File encodersFile = new File(config.getPackageDirectory() + File.separatorChar + "config" + File.separatorChar
				+ "EncodersConfiguration.java");
		if (!encodersFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(encodersFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
			templateEncoders.process(dataModel, out);
			out.flush();
			out.close();
		}

		Template templatePersistence = null;

		if (SQL.equals(config.getSecurityPersistenceDatabase())) {
			templatePersistence = config.getConfiguration()
					.getTemplate(SQL + File.separatorChar + SECURITY_PERSISTENCE_TEMPLATE);
		} else {
			templatePersistence = config.getConfiguration()
					.getTemplate(NO_SQL + File.separatorChar + SECURITY_PERSISTENCE_TEMPLATE);
		}

		dataModel = new HashMap<String, Object>();
		File persistenceFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
				+ File.separatorChar + "SecurityPersistenceConfiguration.java");
		if (!persistenceFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(persistenceFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
			dataModel.put(PROPERTIES_FILE, config.getPropertiesFile());
			dataModel.put(PACKAGE_SCAN_ENTITY, config.getPackageScanEntity());
			templatePersistence.process(dataModel, out);
			out.flush();
			out.close();
		}

		Template templateServerSecurity = null;

		if (SQL.equals(config.getSecurityPersistenceDatabase())) {
			templateServerSecurity = config.getConfiguration()
					.getTemplate(SQL + File.separatorChar + SERVER_SECURITY_TEMPLATE);
		} else {
			templateServerSecurity = config.getConfiguration()
					.getTemplate(NO_SQL + File.separatorChar + SERVER_SECURITY_TEMPLATE);
		}

		dataModel = new HashMap<String, Object>();
		File serverSecurityFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
				+ File.separatorChar + "ServerSecurityConfiguration.java");
		if (!serverSecurityFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(serverSecurityFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");			
		
			String packages = '"'+"br.com.anteros.security.spring"+'"'+","+(SQL.equals(config.getSecurityPersistenceDatabase())?'"'+"br.com.anteros.security.store.sql"+'"':'"'+"br.com.anteros.security.store.mongo"+'"');
			
			dataModel.put(PACKAGE_SCAN_COMPONENTS, packages);
			dataModel.put(PROPERTIES_FILE, config.getPropertiesFile());

			templateServerSecurity.process(dataModel, out);
			out.flush();
			out.close();
		}

		Template templateResourceServerSecurity = config.getConfiguration()
				.getTemplate(File.separatorChar + RESOURCE_SERVER_SECURITY_TEMPLATE);

		dataModel = new HashMap<String, Object>();
		File resourceServerSecurityFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
				+ File.separatorChar + "ResourceServerConfiguration.java");
		if (!resourceServerSecurityFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(resourceServerSecurityFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
			dataModel.put(RESOURCE_ID, config.getResourceID());
			dataModel.put(CLIENT_ID, config.getClientID());
			dataModel.put(CLIENT_SECRET, config.getClientSecret());
			dataModel.put(SECURED_PATTERN, config.getSecuredPattern());
			dataModel.put(REMOTE_TOKEN_CHECK_ENDPOINT, config.remoteEndPointCheckToken());
			dataModel.put(USE_ANTEROS_OAUTH2_SERVER, config.isUseAnterosOAuth2Server()==true);

			templateResourceServerSecurity.process(dataModel, out);
			out.flush();
			out.close();
		}

		if (!config.isUseAnterosOAuth2Server()) {
			Template templateAuthenticationServerSecurity = config.getConfiguration()
					.getTemplate(File.separatorChar + AUTHENTICATION_SERVER_SECURITY_TEMPLATE);

			dataModel = new HashMap<String, Object>();
			File authenticationServerFile = new File(config.getPackageDirectory() + File.separatorChar + "config"
					+ File.separatorChar + "AuthServerOAuth2Configuration.java");
			if (!resourceServerSecurityFile.exists()) {
				out = new AnterosFreeMarkerFileWriter(authenticationServerFile);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");
				templateAuthenticationServerSecurity.process(dataModel, out);
				out.flush();
				out.close();
			}
		}

	}
}
