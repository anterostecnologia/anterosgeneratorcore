package ${packageName};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import br.com.anteros.nosql.persistence.session.NoSQLSessionFactory;
import br.com.anteros.security.spring.config.AbstractSpringMvcConfiguration;
import br.com.anteros.nosql.spring.web.converter.AnterosNoSQLHttpMessageConverter;

@Configuration
@EnableWebMvc
@Import({ResourcePersistenceConfiguration.class})
@ComponentScan(basePackages = { ${packageScanComponents} })
public class ServerMvcConfiguration extends AbstractSpringMvcConfiguration {

	@Autowired
	private NoSQLSessionFactory sessionFactoryNoSQL;

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new AnterosNoSQLHttpMessageConverter(sessionFactoryNoSQL));
	}	

}
