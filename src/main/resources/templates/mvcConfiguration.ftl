package ${packageName};

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Configuration;

import br.com.anteros.springWeb.config.AnterosSpringMvcConfiguration;

import ${packageName}.PersistenceConfiguration;


@Configuration
public class MvcConfiguration extends AnterosSpringMvcConfiguration {

	@Override
	public String getDisplayName() {
		return "${projectDisplayName}";
	}

	@Override
	public Class<?> persistenceConfigurationClass() {
		return PersistenceConfiguration.class;
	}

	@Override
	public Class<?> mvcConfigurationClass() {
		return this.getClass();
	}
	
	@Override
	public Class<?>[] registerFirstConfigurationClasses() {
		return null;
	}

	@Override
	public Class<?>[] registerLastConfigurationClasses() {
		return null;
	}

	@Override
	public void addListener(ServletContext servletContext) {
	}

}
