package ${packageName};

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import br.com.anteros.persistence.session.configuration.PackageScanEntity;
import br.com.anteros.persistence.session.query.ShowSQLType;
import br.com.anteros.spring.config.AnterosSpringPersistenceConfiguration;
import br.com.anteros.spring.config.PooledDataSourceConfiguration;
import br.com.anteros.spring.config.SQLSessionFactoryConfiguration;
import br.com.anteros.spring.config.SingleDataSourceConfiguration;

@Configuration
@PropertySource("${propertiesFile}")
public class PersistenceConfiguration extends AnterosSpringPersistenceConfiguration{

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

	@Value("&&{anteros.dialect}&&")
	private String dialect;

	@Value("&&{anteros.showsql}&&")
	private String showSql = "false";

	@Value("&&{anteros.formatsql}&&")
	private String formatSql = "true";

	@Value("&&{anteros.defaultSchema}&&")
	private String defaultSchema = "";

	@Value("&&{anteros.database.ddl.generation}&&")
	private String databaseDdlGeneration = "none";

	@Value("&&{anteros.ddl.output.mode}&&")
	private String ddlOutputMode = "none";
	
	@Override
	public PooledDataSourceConfiguration getPooledDataSourceConfiguration() {
		return PooledDataSourceConfiguration.of(driverClass, jdbcUrl, user, password)
				.acquireIncrement(Integer.valueOf(acquireIncrement)).initialPoolSize(Integer.valueOf(initialPoolSize))
				.maxPoolSize(Integer.valueOf(maxPoolSize)).minPoolSize(Integer.valueOf(minPoolSize))
				.maxIdleTime(Integer.valueOf(maxIdleTime))
				.idleConnectionTestPeriod(Integer.valueOf(idleConnectionTestPeriod));
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
				.includeSecurityModel(true).jdbcSchema(defaultSchema).showSql(ShowSQLType.parse(showSql))
				.databaseDdlGeneration(databaseDdlGeneration);
	}


}
