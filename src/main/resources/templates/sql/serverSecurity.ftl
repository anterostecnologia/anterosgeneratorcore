package ${packageName};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.anteros.persistence.session.SQLSessionFactory;
import br.com.anteros.security.spring.AnterosSecurityManager;
import br.com.anteros.spring.transaction.AnterosTransactionManager;

@Configuration
@EnableWebSecurity
@Import({EncodersConfiguration.class})
@ComponentScan(basePackages = { ${packageScanComponents} })
@PropertySource("${propertiesFile}")
public class ServerSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Value("&&{system.name}&&")
	private String systemName;

	@Value("&&{system.description}&&")
	private String description;

	@Value("&&{system.version}&&")
	private String version;
	
	@Value("&&{package.scan.security}&&")
	private String packageScanSecurity;
	
	@Value("&&{admin.needs.permission}&&")
	private Boolean adminNeedsPermission;

    @Autowired
    private PasswordEncoder userPasswordEncoder;
    
    @Autowired
    private AnterosSecurityManager authenticationManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	authenticationManager.setAdminNeedsPermission(adminNeedsPermission);
    	authenticationManager.setSystemName(systemName);
		authenticationManager.setDescription(description);
		authenticationManager.setVersion(version);
		authenticationManager.setPackageToScanSecurity(packageScanSecurity);
        auth.userDetailsService(authenticationManager).passwordEncoder(userPasswordEncoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().and()
		.csrf().disable().authorizeRequests().anyRequest().permitAll();	    	
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
		web.ignoring()
        .antMatchers(HttpMethod.OPTIONS);
	}
    
    @Bean(name = "securitySessionFactory")
   	public SQLSessionFactory getSecuritySessionFactory(
   			@Autowired @Qualifier("sessionFactorySQL") SQLSessionFactory sessionFactorySQL) {
   		return sessionFactorySQL;
   	}
   
    
    @Autowired
	@Bean(name = "transactionManagerSQL")
	public PlatformTransactionManager txManager(SQLSessionFactory sessionFactorySQL) {
		if (sessionFactorySQL != null) {
			AnterosTransactionManager txManager = new AnterosTransactionManager();
			txManager.setSessionFactory(sessionFactorySQL);
			return txManager;
		}
		return null;
	}
	
}

