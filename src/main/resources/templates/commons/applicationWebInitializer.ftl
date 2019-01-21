package ${packageName};

import javax.servlet.ServletContext;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import br.com.anteros.spring.web.config.AbstractSpringWebAppInitializer;


public class ApplicationWebInitializer extends AbstractSpringWebAppInitializer{

	@Override
	public Class<?> oauth2ServerConfigurationClass() {
	<#if !useAnterosOAuth2Server>	
 		return AuthServerOAuth2Config.class;
 	<#else>	
		return null;
	</#if>	
	}

	@Override
	public Class<?> resourceServerConfigurationClass() {
		return ResourceServerConfiguration.class;
	}

	@Override
	public Class<?> globalMethodConfigurationClass() {
		return MethodSecurityConfiguration.class;
	}

	@Override
	public Class<?> securityConfigurationClass() {
		return ServerSecurityConfiguration.class;
	}

	@Override
	public Class<?> mvcConfigurationClass() {
		return ServerMvcConfiguration.class;
	}

	@Override
	public Class<?>[] persistenceConfigurationClass() {
		return new Class<?>[]{SecurityPersistenceConfiguration.class, ResourcePersistenceConfiguration.class};
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

	@Override
	public void addServlet(ServletContext servletContext, AnnotationConfigWebApplicationContext appContext) {
		
	}
}