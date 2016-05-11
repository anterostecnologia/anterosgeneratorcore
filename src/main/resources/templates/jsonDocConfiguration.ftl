package ${packageName};

import org.springframework.context.annotation.Configuration;

import br.com.anteros.jsondoc.springmvc.config.AnterosJSONDocConfiguration;

@Configuration
public class JSONDocConfiguration extends AnterosJSONDocConfiguration {

	private String[] packages = {${packageToScanJsonDoc}};
	
	@Override
	public String[] packagesSourceModelAndController() {
		return packages;
	}

	@Override
	public String versionApi() {
		return "${versionApi}";
	}

	@Override
	public String basePath() {
		return "${basePathApi}";
	}

}
