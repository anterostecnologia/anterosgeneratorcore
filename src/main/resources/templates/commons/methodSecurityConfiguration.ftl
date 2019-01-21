package ${packageName};

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import br.com.anteros.security.spring.config.AbstractSpringGlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled=true)
public class MethodSecurityConfiguration extends AbstractSpringGlobalMethodSecurityConfiguration {


}
