package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.CONTROLLER;
import static br.com.anteros.generator.AnterosGenerationConstants.DD_MM_YYYY_HH_MM_SS;
import static br.com.anteros.generator.AnterosGenerationConstants.ENTITY_TYPE;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.INTERFACE_SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.REQUEST_MAPPING;
import static br.com.anteros.generator.AnterosGenerationConstants.REST_CONTROLLER_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.TIME;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import freemarker.template.Template;

public class GenerationControllerStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating class controller for " + config.getClazz().getName());
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), CONTROLLER));
		Template templateServiceInterface = config.getConfiguration().getTemplate(REST_CONTROLLER_TEMPLATE);

		SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);

		String serviceName = config.getClazz().getName() + "Service";
		String entityType = config.getClazz().getName();
		String fullEntityName = config.getClazz().getCanonicalName();
		Writer out = null;

		Map<String, Object> dataModel = new HashMap<String, Object>();
		File fileService = new File(config.getPackageDirectory() + File.separatorChar + "controller" + File.separatorChar
				+ config.getClazz().getName() + "Controller.java");
		if (!fileService.exists()) {
			out = new FileWriter(fileService);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".controller");
			dataModel.put(SERVICE_NAME, serviceName);
			dataModel.put(ENTITY_TYPE, entityType);
			dataModel.put(IMPORT_ENTITY, fullEntityName);
			dataModel.put(IMPORT_SERVICE, config.getPackageDestination() + ".service." + config.getClazz().getName() + "Service");
			dataModel.put(TIME, sdf.format(new Date()));
			dataModel.put(REQUEST_MAPPING, "/" + StringUtils.uncapitalize(config.getClazz().getName()));
			dataModel.put(CONTROLLER, config.getClazz().getName() + "Controller");
			dataModel.put(INTERFACE_SERVICE, serviceName);
			dataModel.put(SERVICE, StringUtils.uncapitalize(serviceName));
			templateServiceInterface.process(dataModel, out);
			out.flush();
			out.close();
		}
	}

}
