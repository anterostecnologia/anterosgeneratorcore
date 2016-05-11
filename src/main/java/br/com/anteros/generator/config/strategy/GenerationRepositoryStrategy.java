package br.com.anteros.generator.config.strategy;

import static br.com.anteros.generator.AnterosGenerationConstants.DD_MM_YYYY_HH_MM_SS;
import static br.com.anteros.generator.AnterosGenerationConstants.ENTITY_TYPE;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_ENTITY;
import static br.com.anteros.generator.AnterosGenerationConstants.IMPORT_REPOSITORY;
import static br.com.anteros.generator.AnterosGenerationConstants.PACKAGE_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY_INTERFACE_TEMPLATE;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY_NAME;
import static br.com.anteros.generator.AnterosGenerationConstants.REPOSITORY_NAME_IMPL;
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

public class GenerationRepositoryStrategy implements AnterosGenerationStrategy {

	public void generate(AnterosGenerationConfig config) throws Exception {
		config.getGenerationLog().log("Generating class repository interface for " + config.getClazz().getName());
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), REPOSITORY));
		FileUtils.forceMkdir(new File(config.getPackageDirectory(), REPOSITORY + File.separatorChar + "impl"));
		Template templateServiceInterface = config.getConfiguration().getTemplate(REPOSITORY_INTERFACE_TEMPLATE);

		SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);

		String repositoryName = config.getClazz().getName() + "Repository";
		String entityType = config.getClazz().getName();
		String fullEntityName = config.getClazz().getCanonicalName();
		Writer out = null;

		Map<String, Object> dataModel = new HashMap<String, Object>();
		File fileService = new File(config.getPackageDirectory() + File.separatorChar + "repository"
				+ File.separatorChar + config.getClazz().getName() + "Repository.java");
		if (!fileService.exists()) {
			out = new FileWriter(fileService);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".repository");
			dataModel.put(REPOSITORY_NAME, repositoryName);
			dataModel.put(ENTITY_TYPE, entityType);
			dataModel.put(IMPORT_ENTITY, fullEntityName);
			dataModel.put(TIME, sdf.format(new Date()));
			templateServiceInterface.process(dataModel, out);
			out.flush();
			out.close();
		}

		Template templateServiceImpl = config.getConfiguration().getTemplate("repositoryImplementation.ftl");
		dataModel = new HashMap<String, Object>();
		File fileServiceImpl = new File(
				config.getPackageDirectory() + File.separatorChar + "repository" + File.separatorChar + "impl"
						+ File.separatorChar + config.getClazz().getName() + "RepositoryImpl.java");
		if (!fileServiceImpl.exists()) {
			out = new FileWriter(fileServiceImpl);
			dataModel.put(PACKAGE_NAME, config.getPackageDestination() + ".repository.impl");
			dataModel.put(IMPORT_ENTITY, fullEntityName);
			dataModel.put(IMPORT_REPOSITORY,
					config.getPackageDestination() + ".repository." + config.getClazz().getName() + "Repository");
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
