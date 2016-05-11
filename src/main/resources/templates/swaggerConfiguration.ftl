package ${packageName};

import org.springframework.context.annotation.Configuration;

import br.com.anteros.swagger.AnterosSwaggerConfiguration;

@Configuration
public class SwaggerConfiguration extends AnterosSwaggerConfiguration{

	@Override
	public String title() {
		return "${titleApi}";
	}

	@Override
	public String description() {
		return "${descriptionApi}";
	}

	@Override
	public String version() {
		return "${versionApi}";
	}

	@Override
	public String termsOfServiceUrl() {
		return "${termsOfServiceUrl}";
	}

	@Override
	public String contactName() {
		return "${contactName}";
	}

	@Override
	public String license() {
		return "${licenseApi}";
	}

	@Override
	public String licenseUrl() {
		return "${licenseUrlApi}";
	}

}