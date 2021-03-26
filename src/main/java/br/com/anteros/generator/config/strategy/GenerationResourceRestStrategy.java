package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.DD_MM_YYYY_HH_MM_SS;
import static br.com.anteros.generator.AnterosGenerationConstants.ENTITY_TYPE;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.INTERFACE_SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.NO_SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.REQUEST_MAPPING;
import static br.com.anteros.generator.AnterosGenerationConstants.RESOURCE;
import static br.com.anteros.generator.AnterosGenerationConstants.RESOURCE_REST_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE;
import static br.com.anteros.generator.AnterosGenerationConstants.SERVICE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.TIME;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.generator.AnterosGenerationStrategy;
import br.com.anteros.generator.config.AnterosGenerationConfig;
import freemarker.template.Template;

public class GenerationResourceRestStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating class resource rest for " + config.getClazz().getName());
		Template templateServiceInterface = null;
		if (SQL.equals(config.getPersistenceDatabase())) {
			templateServiceInterface = config.getConfiguration()
					.getTemplate(SQL + File.separator + RESOURCE_REST_TEMPLATE);
		} else {
			templateServiceInterface = config.getConfiguration()
					.getTemplate(NO_SQL + File.separator + RESOURCE_REST_TEMPLATE);
		}

		SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);

		String serviceName = config.getClazz().getName() + "Service";
		String entityType = config.getClazz().getName();
		String fullEntityName = config.getClazz().getCanonicalName();
		Writer out = null;

		Map<String, Object> dataModel = new HashMap<String, Object>();
		
		
		String sourcePackageName = config.getClazz().getPackageName();
		
		List<String> names = config.getPackageReplaceNamesList();
		for (String name : names) {
			sourcePackageName = StringUtils.replaceAll(sourcePackageName, name, config.getPackageDestination());
		}				
		sourcePackageName = StringUtils.replaceAll(sourcePackageName, config.getPackageDestination(), config.getPackageDestination()+'.'+config.getResourceVersion());	

		
		FileUtils.forceMkdir(new File(config.getSourceDestination()+ File.separator + sourcePackageName.replace('.', File.separatorChar)));
		File fileService = new File(config.getSourceDestination()+ File.separator + sourcePackageName.replace('.', File.separatorChar)+ File.separator+ config.getClazz().getName() + "Resource.java");

		if (!fileService.exists()) {
			out = new FileWriter(fileService);
			dataModel.put(PACKAGE_NAME, sourcePackageName);
			dataModel.put(SERVICE_NAME, serviceName);
			dataModel.put(ENTITY_TYPE, entityType);
			dataModel.put(IMPORT_ENTITY, fullEntityName);
			dataModel.put(IMPORT_SERVICE,
					sourcePackageName + "."+config.getClazz().getName() + "Service");
			dataModel.put(TIME, sdf.format(new Date()));
			dataModel.put(REQUEST_MAPPING, "/"+config.getResourceVersion()+"/"+StringUtils.uncapitalize(config.getClazz().getName()));
			dataModel.put(RESOURCE, config.getClazz().getName() + "Resource");
			dataModel.put(INTERFACE_SERVICE, serviceName);
			dataModel.put(SERVICE, StringUtils.uncapitalize(serviceName));
			templateServiceInterface.process(dataModel, out);
			out.flush();
			out.close();
		}
	}

}
