package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.APP_WEB_INITIALIZER_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.INCLUDE_OAUTH2;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.USE_ANTEROS_OAUTH2_SERVER;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerFileWriter;
import freemarker.template.Template;

public class GenerationAppConfigurationStrategy implements AnterosGenerationStrategy {

	@Override
	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating app initializer");
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), File.separatorChar + "config"));
		
		Template templateAppHandler = config.getConfiguration().getTemplate(APP_WEB_INITIALIZER_TEMPLATE);
		AnterosFreeMarkerFileWriter out = null;
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File appFile = new File(config.getPackageDirectory() + File.separatorChar
				+ "config" + File.separatorChar + "ApplicationWebInitializer.java");
		if (!appFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(appFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + 
					 ".config");
			dataModel.put(USE_ANTEROS_OAUTH2_SERVER, config.isUseAnterosOAuth2Server());
			dataModel.put(INCLUDE_OAUTH2, config.isIncludeOAuth2());

//			dataModel.put(PROJECT_DISPLAY_NAME, config.getProjectDisplayName());

			templateAppHandler.process(dataModel, out);
			out.flush();
			out.close();
		}	
	}
}
