package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import br.com.anteros.security.spring.config.AbstractSpringAuthServerOAuth2Configuration;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthServerOAuth2Configuration extends AbstractSpringAuthServerOAuth2Configuration {

	@Autowired
	private PasswordEncoder oauthClientPasswordEncoder;

	@Override
	public PasswordEncoder getOAuth2ClientPasswordEncoder() {
		return oauthClientPasswordEncoder;
	}

}
