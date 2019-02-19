package ${packageName};

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ${importEntity};
import ${importService};
import br.com.anteros.spring.service.SpringSQLService;

/**
*  Generated by Anteros Generator Maven Plugin at ${time}
**/

@Service("${service}")
@Scope("prototype")
public class ${serviceNameImpl} extends SpringSQLService<${entityType}, Long> implements ${interfaceService} {

}