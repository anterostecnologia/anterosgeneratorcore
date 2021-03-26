package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.RESOURCE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_INTERFACE_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.EXCEPTION_HANDLER_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.NO_SQL;
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
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), RESOURCE + File.separator + "handler"));
		
		Template templateExceptionHandler = null;
		AnterosFreeMarkerFileWriter out = null;		
		if (SQL.equals(config.getPersistenceDatabase())){
			templateExceptionHandler = config.getConfiguration().getTemplate(SQL + File.separator +EXCEPTION_HANDLER_TEMPLATE);
		}
		else {
			templateExceptionHandler = config.getConfiguration().getTemplate(NO_SQL+ File.separator +EXCEPTION_HANDLER_TEMPLATE);
		}
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		File exceptionHandlerFile = new File(config.getPackageDirectory() + File.separator + RESOURCE + File.separator
				+ "handler" + File.separator + "MvcExceptionHandler.java");
		if (!exceptionHandlerFile.exists()) {
			out = new AnterosFreeMarkerFileWriter(exceptionHandlerFile);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + "." 
					+ RESOURCE + ".handler");

			dataModel.put(PROJECT_DISPLAY_NAME, config.getProjectDisplayName());

			templateExceptionHandler.process(dataModel, out);
			out.flush();
			out.close();
		}	
	}
}
