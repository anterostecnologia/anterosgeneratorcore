package ${packageName};

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import br.com.anteros.nosql.persistence.session.NoSQLSessionFactory;
import br.com.anteros.nosql.persistence.session.ShowCommandsType;
import br.com.anteros.nosql.persistence.session.configuration.PackageScanEntity;
import br.com.anteros.nosql.spring.config.AbstractSpringSecurityNoSQLPersistenceConfiguration;
import br.com.anteros.nosql.spring.config.NoSQLDataSourceConfiguration;
import br.com.anteros.nosql.spring.config.NoSQLSessionFactoryConfiguration;

@Configuration
@PropertySource("${propertiesFile}")
public class SecurityPersistenceConfiguration extends AbstractSpringSecurityNoSQLPersistenceConfiguration {
	
	@Value("&&{nosql.hostname}&&")
	private String hostName;
	
	@Value("&&{nosql.port}&&")
	private Integer port;

	@Value("&&{nosql.databasename}&&")
	private String databaseName;

	@Value("&&{nosql.username}&&")
	private String userName;

	@Value("&&{nosql.password}&&")
	private String password;
	
	@Value("&&{nosql.dialect}&&")
	private String dialect;
	
	@Value("&&{package.scan.security}&&")
	private String packageScanSecurity;
	
	@Bean
	@Override
	public NoSQLSessionFactory securitySessionFactory() throws Exception {
		return super.securitySessionFactory();
	}

	@Override
	public NoSQLDataSourceConfiguration getDataSourceConfiguration() {
		return NoSQLDataSourceConfiguration.create().host(hostName).port(port).databaseName(databaseName)
				.userName(userName).password(password);
	}

	@Override
	public NoSQLSessionFactoryConfiguration getSessionFactoryConfiguration() {
		return NoSQLSessionFactoryConfiguration.create()
				.dialect(dialect).databaseName(databaseName)	
				.withoutTransactionControl(true)
				.packageScanEntity(PackageScanEntity.of(packageScanSecurity)).includeSecurityModel(true)
				.showCommands(ShowCommandsType.ALL).formatCommands(true);
	}

}
