package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
<#if !useAnterosOAuth2Server>
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
</#if>
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import br.com.anteros.security.spring.AnterosSecurityManager;
import br.com.anteros.security.spring.config.AbstractSpringResourceServerConfiguration;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends AbstractSpringResourceServerConfiguration {
	

    private static final String RESOURCE_ID = "${resourceID}";
    private static final String SECURED_PATTERN = "${securedPattern}";
    
    @Autowired
	private TokenStore tokenStore;
    
    @Autowired
    private AnterosSecurityManager authenticationManager;

	@Override
	public String getResourceId() {
		return RESOURCE_ID;
	}

	@Override
	public String getSecuredPattern() {
		return SECURED_PATTERN;
	}

    <#if useAnterosOAuth2Server>	
  	@Override
	public ResourceServerTokenServices getResourceServerTokenServicesToAuthentication() {
		final RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl("${tokenValidationEndPoint}");
        tokenServices.setClientId("${clientID}");
        tokenServices.setClientSecret("${clientSecret}");
        return tokenServices;
	}
	<#else>
	@Override
	public ResourceServerTokenServices getResourceServerTokenServicesToAuthentication() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore);
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(authenticationManager);
		return tokenServices;
	}
	</#if>
	
	

}
