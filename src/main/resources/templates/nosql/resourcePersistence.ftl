package ${packageName};

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.anteros.persistence.session.SQLSessionFactory;
import br.com.anteros.persistence.session.configuration.PackageScanEntity;
import br.com.anteros.persistence.session.query.ShowSQLType;
import br.com.anteros.spring.config.AbstractSQLPersistenceConfiguration;
import br.com.anteros.spring.config.PooledDataSourceConfiguration;
import br.com.anteros.spring.config.SQLSessionFactoryConfiguration;
import br.com.anteros.spring.config.SingleDataSourceConfiguration;
import br.com.anteros.spring.transaction.AnterosTransactionManager;

@Configuration
@PropertySource("${propertiesFile}")
public class ResourcePersistenceConfiguration extends AbstractSQLPersistenceConfiguration {

	@Value("&&{jdbc.driverClassName}&&")
	private String driverClass;

	@Value("&&{jdbc.url}&&")
	private String jdbcUrl;

	@Value("&&{jdbc.username}&&")
	private String user;

	@Value("&&{jdbc.password}&&")
	private String password;

	@Value("&&{c3p0.acquireIncrement}&&")
	private String acquireIncrement = "5";

	@Value("&&{c3p0.initialPoolSize}&&")
	private String initialPoolSize = "5";

	@Value("&&{c3p0.maxPoolSize}&&")
	private String maxPoolSize = "100";

	@Value("&&{c3p0.minPoolSize}&&")
	private String minPoolSize = "5";

	@Value("&&{c3p0.maxIdleTime}&&")
	private String maxIdleTime = "10000";

	@Value("&&{c3p0.idleConnectionTestPeriod}&&")
	private String idleConnectionTestPeriod = "10000";
	
	@Value("&&{c3p0.preferredTestQuery}&&")
	private String preferredTestQuery;

	@Value("&&{c3p0.testConnectionOnCheckout}&&")
	private Boolean testConnectionOnCheckout;

	@Value("&&{c3p0.testConnectionOnCheckin}&&")
	private Boolean testConnectionOnCheckin;

	@Value("&&{c3p0.maxConnectionAge}&&")
	private Long maxConnectionAge;

	@Value("&&{c3p0.acquireRetryAttempts}&&")
	private Long acquireRetryAttempts;
	
	@Value("&&{c3p0.automaticTestTable}&&")
	private String automaticTestTable;

	@Value("&&{anteros.dialect}&&")
	private String dialect;

	@Value("&&{anteros.showsql}&&")
	private String showSql = "false";

	@Value("&&{anteros.formatsql}&&")
	private String formatSql = "true";

	@Value("&&{anteros.defaultSchema}&&")
	private String defaultSchema = "";

	@Value("&&{anteros.database.ddl.generation}&&")
	private String databaseDDLGeneration = "none";
	
	@Value("&&{anteros.script.ddl.generation}&&")
	private String scriptDDLGeneration = "none";

	@Value("&&{anteros.ddl.output.mode}&&")
	private String ddlOutputMode = "none";
	
	@Value("&&{anteros.ddl.application.location}&&")
	private String applicationLocation = "";
	
	@Value("&&{anteros.ddl.createtables.filename}&&")
	private String createTablesFileName = "";
	
	@Value("&&{anteros.ddl.droptables.filename}&&")
	private String dropTablesFileName = "";
	
	@Value("&&{anteros.lock.timeout}&&")
	private Long lockTimeout = 0L;
	
	@Value("&&{anteros.use.bean.validation}&&")
	private Boolean useBeanValidation = true;
	

	@Autowired
	@Bean
	public PlatformTransactionManager txManager(NoSQLSessionFactory sessionFactoryNoSQL) {
		if (sessionFactoryNoSQL != null) {
			AnterosNoSQLTransactionManager txManager = new AnterosNoSQLTransactionManager();
			txManager.setSessionFactory(sessionFactoryNoSQL);
			return txManager;
		}
		return null;
	}
	
	@Override
	public PooledDataSourceConfiguration getPooledDataSourceConfiguration() {
		return PooledDataSourceConfiguration.of(driverClass, jdbcUrl, user, password)
				.acquireIncrement(Integer.valueOf(acquireIncrement)).initialPoolSize(Integer.valueOf(initialPoolSize))
				.maxPoolSize(Integer.valueOf(maxPoolSize)).minPoolSize(Integer.valueOf(minPoolSize))
				.maxIdleTime(Integer.valueOf(maxIdleTime))
				.idleConnectionTestPeriod(Integer.valueOf(idleConnectionTestPeriod))
				.acquireRetryAttempts(acquireRetryAttempts.intValue())
				.automaticTestTable(automaticTestTable)
				.maxConnectionAge(maxConnectionAge.intValue())
				.preferredTestQuery(preferredTestQuery)
				.testConnectionOnCheckin(testConnectionOnCheckin)
				.testConnectionOnCheckout(testConnectionOnCheckout);	
	}

	@Override
	public SingleDataSourceConfiguration getSingleDataSourceConfiguration() {
		return null;
	}

	@Override
	public SQLSessionFactoryConfiguration getSQLSessionFactoryConfiguration() {
		return SQLSessionFactoryConfiguration.create()
				.packageScanEntity(new PackageScanEntity("${packageScanEntity}"))
				.ddlOutputMode(ddlOutputMode).dialect(dialect).formatSql(Boolean.valueOf(formatSql))
				.applicationLocation(applicationLocation)
				.createTablesFileName(createTablesFileName)
				.dropTablesFileName(dropTablesFileName)
				.includeSecurityModel(true).jdbcSchema(defaultSchema).showSql(ShowSQLType.parse(showSql))
				.databaseDDLGeneration(databaseDDLGeneration)
				.useBeanValidation(useBeanValidation)
				.scriptDDLGeneration(scriptDDLGeneration);
	}

}
