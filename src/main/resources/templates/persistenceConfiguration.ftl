package ${packageName};

import org.springframework.context.annotation.Configuration;

import br.com.anteros.persistence.session.configuration.PackageScanEntity;
import br.com.anteros.persistence.session.query.ShowSQLType;
import br.com.anteros.spring.config.AnterosSpringPersistenceConfiguration;
import br.com.anteros.spring.config.PooledDataSourceConfiguration;
import br.com.anteros.spring.config.SQLSessionFactoryConfiguration;
import br.com.anteros.spring.config.SingleDataSourceConfiguration;
import br.com.anteros.persistence.session.configuration.AnterosPersistenceProperties;

@Configuration
public class PersistenceConfiguration extends AnterosSpringPersistenceConfiguration{

	@Override
	public PooledDataSourceConfiguration getPooledDataSourceConfiguration() {
		return PooledDataSourceConfiguration.of(getDriverClass(), getJdbcUrl(), getUser(), getPassword())
				.acquireIncrement(getAcquireIncrement()).initialPoolSize(getInitialPoolSize())
				.maxPoolSize(getMaxPoolSize()).minPoolSize(getMinPoolSize())
				.maxIdleTime(getMaxIdleTime())
				.idleConnectionTestPeriod(Integer.valueOf(getIdleConnectionTestPeriod()));
	}

	@Override
	public SingleDataSourceConfiguration getSingleDataSourceConfiguration() {
		return null;
	}

	@Override
	public SQLSessionFactoryConfiguration getSQLSessionFactoryConfiguration() {
		return SQLSessionFactoryConfiguration.create()
				.packageScanEntity(new PackageScanEntity("${packageScanEntity}"))
				.ddlOutputMode(getDdlOutputMode()).dialect(getDialect()).formatSql(getFormatSql())
				.includeSecurityModel(true).jdbcSchema(getDefaultSchema()).showSql(getShowSql())
				.databaseDdlGeneration(getDatabaseDdlGeneration());
	}


    public String getDriverClass() {
		return "** INFORME AQUI A CLASSE DO DRIVER JDBC **";
	}

	public String getJdbcUrl() {
		return "** INFORME AQUI A URL DE CONEXÃO JDBC **";
	}

	public String getUser() {
		return "** INFORME AQUI O USUÁRIO DE CONEXÃO **";
	}

	public String getPassword() {
		return "** INFORME AQUI A SENHA DE CONEXÃO **";
	}

	public int getAcquireIncrement() {
		return 5;
	}

	public int getInitialPoolSize() {
		return 10;
	}

	public int getMaxPoolSize() {
		return 50;
	}

	public int getMinPoolSize() {
		return 1;
	}

	public int getMaxIdleTime() {
		return 20000;
	}

	public int getIdleConnectionTestPeriod() {
		return 5000;
	}

	public String getDialect() {
		return "** INFORME AQUI O DIALETO DA PERSISTÊNCIA **";
	}

	public ShowSQLType getShowSql() {
		return ShowSQLType.ALL;
	}

	public boolean getFormatSql() {
		return true;
	}

	public String getDefaultSchema() {
		return "";
	}

	public String getDatabaseDdlGeneration() {
		return AnterosPersistenceProperties.CREATE_OR_EXTEND;
	}

	public String getDdlOutputMode() {
		return AnterosPersistenceProperties.DDL_DATABASE_OUTPUT;
	}
	
}
