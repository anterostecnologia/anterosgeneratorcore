package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.DD_MM_YYYY_HH_MM_SS;
import static br.com.anteros.generator.AnterosGenerationConstants.ENTITY_TYPE;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.INTERFACE_SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_IMPLEMENTATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_INTERFACE_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_NAME_IMPL;
import static br.com.anteros.generator.AnterosGenerationConstants.TIME;
import static br.com.anteros.generator.AnterosGenerationConstants.SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.NO_SQL;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import freemarker.template.Template;

public class GenerationServiceStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception { 
		config.getGenerationLog().log("Generating class service interface for " + config.getClazz().getName());
			FileUtils.forceMkdir(new File(config.getPackageDirectory(), SERVICE));
			FileUtils.forceMkdir(new File(config.getPackageDirectory(), SERVICE + File.separatorChar + "impl"));
			Template templateServiceInterface = null;
			if (SQL.equals(config.getPersistenceDatabase())){
				templateServiceInterface = config.getConfiguration().getTemplate(SQL + File.separatorChar +SERVICE_INTERFACE_TEMPLATE);
			}
			else {
			 templateServiceInterface = config.getConfiguration().getTemplate(NO_SQL+ File.separatorChar +SERVICE_INTERFACE_TEMPLATE);
			}

			SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);

			String serviceName = config.getClazz().getName() + "Service";
			String entityType = config.getClazz().getName();
			String fullEntityName = config.getClazz().getCanonicalName();
			Writer out = null;

			Map<String, Object> dataModel = new HashMap<String, Object>();
			File fileService = new File(config.getPackageDirectory() + File.separatorChar + "service" + File.separatorChar
					+ config.getClazz().getName() + "Service.java");
			if (!fileService.exists()) {
				out = new FileWriter(fileService);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".service");
				dataModel.put(SERVICE_NAME, serviceName);
				dataModel.put(ENTITY_TYPE, entityType);
				dataModel.put(IMPORT_ENTITY, fullEntityName);
				dataModel.put(TIME, sdf.format(new Date()));
				templateServiceInterface.process(dataModel, out);
				out.flush();
				out.close();
			}

			Template templateServiceImpl = config.getConfiguration().getTemplate(SERVICE_IMPLEMENTATION_TEMPLATE);
			dataModel = new HashMap<String, Object>();
			File fileServiceImpl = new File(config.getPackageDirectory() + File.separatorChar + "service" + File.separatorChar + "impl"
					+ File.separatorChar + config.getClazz().getName() + "ServiceImpl.java");
			if (!fileServiceImpl.exists()) {
				out = new FileWriter(fileServiceImpl);
				dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".service.impl");
				dataModel.put(IMPORT_ENTITY, fullEntityName);
				dataModel.put(IMPORT_SERVICE, config.getPackageDestination() + ".service." + config.getClazz().getName() + "Service");
				dataModel.put(SERVICE_NAME_IMPL, serviceName + "Impl");
				dataModel.put(INTERFACE_SERVICE, serviceName);
				dataModel.put(ENTITY_TYPE, entityType);
				dataModel.put(TIME, sdf.format(new Date()));
				templateServiceImpl.process(dataModel, out);
				out.flush();
				out.close();
			}
	}

}
