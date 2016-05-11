package ${packageName};

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import br.com.anteros.security.spring.config.AnterosSpringSecurityConfiguration;

@Configuration
@PropertySource("${propertiesFile}")
@ComponentScan(basePackages = { "br.com.anteros.security" })
public class SecurityConfiguration extends AnterosSpringSecurityConfiguration {

	@Value("&&{system.name}&&")
	private String systemName;

	@Value("&&{system.description}&&")
	private String description;

	@Value("&&{system.version}&&")
	private String version;

	@Value("&&{admin.needs.permission}&&")
	private String adminNeedsPermission;

	@Value("&&{package.scan.security}&&")
	private String packageToScanSecurity;

	@Override
	public String systemName() {
		return systemName;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String version() {
		return version;
	}

	@Override
	public Boolean adminNeedsPermission() {
		return Boolean.valueOf(adminNeedsPermission);
	}

	@Override
	public String packageToScanSecurity() {
		return packageToScanSecurity;
	}

}
