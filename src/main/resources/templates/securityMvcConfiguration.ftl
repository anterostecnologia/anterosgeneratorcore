package ${packageName};

import javax.servlet.ServletContext;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

${importJSONDoc}
${importSwagger}

import br.com.anteros.security.spring.config.AnterosSpringSecurityMvcConfiguration;

import ${packageName}.SecurityConfiguration;
import ${packageName}.PersistenceConfiguration;

@Configuration
@ComponentScan(basePackages = { ${packageScanComponents} })
public class SecurityMvcConfiguration extends AnterosSpringSecurityMvcConfiguration implements WebApplicationInitializer {

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
	public Class<?> swaggerConfigurationClass() {
		return ${swaggerConfigurationClass};
	}

	@Override
	public Class<?> securityConfigurationClass() {
		return SecurityConfiguration.class;
	}

	@Override
	public Class<?> jsonDocConfigurationClass() {
		return ${jsonDocConfigurationClass};
	}

	@Override
	public Class<?> globalMethodSecurityConfigurationClass() {
		return GlobalMethodSecurityConfiguration.class;
	}
	
	@Override
	public void addServlet(ServletContext servletContext, AnnotationConfigWebApplicationContext appContext) {
		
	}


}
