package ${packageName};

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import br.com.anteros.security.spring.config.AnterosSpringGlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class GlobalMethodSecurityConfiguration  extends AnterosSpringGlobalMethodSecurityConfiguration{

}
