package ${packageName};

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.anteros.security.spring.config.AnterosSpringSecurityConfiguration;

@Configuration
@ComponentScan(basePackages = { "br.com.anteros.security" })
public class SecurityConfiguration extends AnterosSpringSecurityConfiguration {

	@Override
	public String systemName() {
		return "** INFORME AQUI O NOME DO SISTEMA **";
	}

	@Override
	public String description() {
		return "** INFORME AQUI A DESCRIÇÃO DO SISTEMA **" ;
	}

	@Override
	public String version() {
		return "1.0.0";
	}

	@Override
	public Boolean adminNeedsPermission() {
		return false;
	}

	@Override
	public String packageToScanSecurity() {
		return "** INFORME AQUI OS PACOTES PARA PROCURAR RECURSOS/AÇÕES DE SEGURANÇA **";
	}
}
