package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.MVC_CONFIGURATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.NO_SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_SCAN_COMPONENTS;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_INTERFACE_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SQL;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerFileWriter;
import freemarker.template.Template;

public class GenerationMvcConfigurationStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating MVC configuration");
		
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), "config"));
		AnterosFreeMarkerFileWriter out = null;

		Template templateMvc = null;
		if (SQL.equals(config.getPersistenceDatabase())){
			templateMvc = config.getConfiguration().getTemplate(SQL+File.separator+MVC_CONFIGURATION_TEMPLATE);
		}
		else {
			templateMvc = config.getConfiguration().getTemplate(NO_SQL+ File.separator +MVC_CONFIGURATION_TEMPLATE);
		}
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File mvcFile = new File(config.getPackageDirectory() + File.separator + "config" + File.separator
				+ "ServerMvcConfiguration.java");
		if (!mvcFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(mvcFile);
			String packages = "";
			boolean appendDelimiter = false;
			for (String pck : config.getPackageScanComponentsList()) {
				if (appendDelimiter)
					packages += ",";
				packages += '"' + pck + '"';
				appendDelimiter = true;
			}
			dataModel.put(PACKAGE_SCAN_COMPONENTS, packages);			
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".config");

			templateMvc.process(dataModel, out);
			out.flush();
			out.close();
		}
	}

}
