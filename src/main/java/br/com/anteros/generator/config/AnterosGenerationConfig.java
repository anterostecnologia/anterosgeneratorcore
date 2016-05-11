package br.com.anteros.generator.config;

import java.util.List;

import com.thoughtworks.qdox.model.JavaClass;

import br.com.anteros.generator.AnterosGenerationLog;
import freemarker.template.Configuration;

public interface AnterosGenerationConfig {
	
	public AnterosGenerationLog getGenerationLog();
	
	public String getPackageDirectory();

	public JavaClass getClazz();

	public Configuration getConfiguration();

	public String getPackageDestination();

	public String getProjectDisplayName();

	public boolean isGenerateSwaggerConfiguration();

	public boolean isGenerateJSONDocConfiguration();

	public String getPropertiesFile();

	public String getPackageScanEntity();

	public String getTitleAPI();

	public String getDescriptionAPI();

	public String getTermsOfServiceUrl();

	public String getContactName();

	public String getLicenseAPI();

	public String getVersionAPI();

	public String getLicenseUrl();

	public List<String> getPackageScanJSONDocList();

	public String getBasePathJSONDoc();

	public List<String> getPackageScanComponentsList();

	public String getSourceDestination();

	public List<String> getSourcesToScanEntities();

	public boolean isIncludeSecurity();

	public List<String> getPackageBaseList();

	public boolean isGenerateRepository();

	public boolean isGenerateJavaConfiguration();
	
	public void setConfiguration(Configuration configuration);
	
	public void setClazz(JavaClass clazz);

}
