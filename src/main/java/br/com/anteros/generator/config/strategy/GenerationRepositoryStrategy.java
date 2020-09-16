package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.DD_MM_YYYY_HH_MM_SS;
import static br.com.anteros.generator.AnterosGenerationConstants.ENTITY_TYPE;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_REPOSITORY;
import static br.com.anteros.generator.AnterosGenerationConstants.NO_SQL;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY_INTERFACE_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY_IMPLEMENTATION_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY_NAME_IMPL;
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

public class GenerationRepositoryStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating class repository interface for " + config.getClazz().getName());
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), REPOSITORY));
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), REPOSITORY + File.separatorChar + "impl"));
				
		Template templateServiceInterface = null;
		if (SQL.equals(config.getPersistenceDatabase())){
			templateServiceInterface = config.getConfiguration().getTemplate(SQL + File.separatorChar +REPOSITORY_INTERFACE_TEMPLATE);
		}
		else {
		 templateServiceInterface = config.getConfiguration().getTemplate(NO_SQL+ File.separatorChar +REPOSITORY_INTERFACE_TEMPLATE);
		}

		SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);

		String repositoryName = config.getClazz().getName() + "Repository";
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
		
		FileUtils.forceMkdir(new File(config.getSourceDestination()+ File.separatorChar + sourcePackageName.replace('.', File.separatorChar)));
		
		File fileService = new File(config.getSourceDestination()+ File.separatorChar + sourcePackageName.replace('.', File.separatorChar)+File.separatorChar+ config.getClazz().getName() + "Repository.java");
		
		
		if (!fileService.exists()) {
			out = new FileWriter(fileService);
			dataModel.put(PACKAGE_NAME, sourcePackageName);
			dataModel.put(REPOSITORY_NAME, repositoryName);
			dataModel.put(ENTITY_TYPE, entityType);
			dataModel.put(IMPORT_ENTITY, fullEntityName);
			dataModel.put(TIME, sdf.format(new Date()));
			templateServiceInterface.process(dataModel, out);
			out.flush();
			out.close();
		}
		
		Template templateServiceImpl=null;
		if (SQL.equals(config.getPersistenceDatabase())){
			templateServiceImpl = config.getConfiguration().getTemplate(SQL + File.separatorChar +REPOSITORY_IMPLEMENTATION_TEMPLATE);
		}
		else {
			templateServiceImpl = config.getConfiguration().getTemplate(NO_SQL+ File.separatorChar +REPOSITORY_IMPLEMENTATION_TEMPLATE);
		}


		dataModel = new HashMap<String, Object>();
		
		FileUtils.forceMkdir(new File(config.getSourceDestination()+ File.separatorChar + sourcePackageName.replace('.', File.separatorChar)+File.separatorChar));
		
		File fileServiceImpl = new File(config.getSourceDestination()+ File.separatorChar + sourcePackageName.replace('.', File.separatorChar)+File.separatorChar+ config.getClazz().getName() + "RepositoryImpl.java");
		
		if (!fileServiceImpl.exists()) {
			out = new FileWriter(fileServiceImpl);
			dataModel.put(PACKAGE_NAME, sourcePackageName);
			dataModel.put(IMPORT_ENTITY, fullEntityName);
			dataModel.put(IMPORT_REPOSITORY,
					sourcePackageName +"."+ config.getClazz().getName() + "Repository");
			dataModel.put(REPOSITORY, StringUtils.uncapitalize(repositoryName));
			dataModel.put(REPOSITORY_NAME_IMPL, repositoryName + "Impl");
			dataModel.put(REPOSITORY_NAME, repositoryName);
			dataModel.put(ENTITY_TYPE, entityType);
			dataModel.put(TIME, sdf.format(new Date()));
			templateServiceImpl.process(dataModel, out);
			out.flush();
			out.close();
		}
	}

}
