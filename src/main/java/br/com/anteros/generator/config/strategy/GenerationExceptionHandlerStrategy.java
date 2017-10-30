package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.CONTROLLER;
import static br.com.anteros.generator.AnterosGenerationConstants.EXCEPTION_HANDLER_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.PROJECT_DISPLAY_NAME;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.freemarker.AnterosFreeMarkerFileWriter;
import freemarker.template.Template;

public class GenerationExceptionHandlerStrategy implements AnterosGenerationStrategy {

	@Override
	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating error handler");
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), CONTROLLER + File.separatorChar + "handler"));
		
		Template templateExceptionHandler = config.getConfiguration().getTemplate(EXCEPTION_HANDLER_TEMPLATE);
		AnterosFreeMarkerFileWriter out = null;
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File exceptionHandlerFile = new File(config.getPackageDirectory() + File.separatorChar + CONTROLLER + File.separatorChar
				+ "handler" + File.separatorChar + "AnterosExceptionHandler.java");
		if (!exceptionHandlerFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(exceptionHandlerFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + "." 
					+ CONTROLLER + ".handler");

			dataModel.put(PROJECT_DISPLAY_NAME, config.getProjectDisplayName());

			templateExceptionHandler.process(dataModel, out);
			out.flush();
			out.close();
		}	
	}
}
