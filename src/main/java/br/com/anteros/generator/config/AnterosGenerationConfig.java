package br.com.anteros.generator.config;

import java.net.URL;
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

	public String getPropertiesFile();

	public String getPackageScanEntity();

	public String getTitleAPI();

	public String getDescriptionAPI();

	public String getTermsOfServiceUrl();

	public String getContactName();

	public String getLicenseAPI();

	public String getVersionAPI();

	public String getLicenseUrl();

	public List<String> getPackageScanComponentsList();

	public String getSourceDestination();

	public boolean isIncludeSecurity();

	public boolean isGenerateRepository();

	public boolean isGenerateJavaConfiguration();
	
	public boolean isGenerateService();
	
	public boolean isGenerateController();
	
	public boolean isGenerateForAbstractClass();
	
	public void setConfiguration(Configuration configuration);
	
	public void setClazz(JavaClass clazz);
	
	public List<URL> getClassPathURLs();

}
