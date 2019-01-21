package br.com.anteros.generator;

import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.config.strategy.GenerationAppConfigurationStrategy;
import br.com.anteros.generator.config.strategy.GenerationExceptionHandlerStrategy;
import br.com.anteros.generator.config.strategy.GenerationMvcConfigurationStrategy;
import br.com.anteros.generator.config.strategy.GenerationRepositoryStrategy;
import br.com.anteros.generator.config.strategy.GenerationResourcePersistenceConfigurationStrategy;
import br.com.anteros.generator.config.strategy.GenerationResourceRestStrategy;
import br.com.anteros.generator.config.strategy.GenerationSecurityConfigurationStrategy;
import br.com.anteros.generator.config.strategy.GenerationSecurityServiceStrategy;
import br.com.anteros.generator.config.strategy.GenerationServiceStrategy;

public class AnterosGenerator {
	
	public final static int GENERATION_APP_CONFIGURATION=0;
	public final static int GENERATION_SERVICE=1;
	public final static int GENERATION_REPOSITORY=2;
	public final static int GENERATION_RESOURCE=3;
	public final static int GENERATION_MVC_CONFIGURATION=4;
	public final static int GENERATION_RESOURCE_PERSISTENCE_CONFIGURATION=5;
	public final static int GENERATION_SECURITY_SERVICE=6;
	public final static int GENERATION_SECURITY_CONFIGURATION=7;
	public final static int GENERATION_EXCEPTION_HANDLER=8;	
	
	private AnterosGenerationStrategy strategy=null;
	
	private AnterosGenerator(){
		
	}

	public static AnterosGenerator create(int type) {
		AnterosGenerator generator = new AnterosGenerator();
		switch (type) {
		case GENERATION_APP_CONFIGURATION:
			generator.strategy = new GenerationAppConfigurationStrategy();
			break;
		case GENERATION_SERVICE:
			generator.strategy = new GenerationServiceStrategy();
			break;
		case GENERATION_REPOSITORY:
			generator.strategy = new GenerationRepositoryStrategy();
			break;
		case GENERATION_RESOURCE:
			generator.strategy = new GenerationResourceRestStrategy();
			break;
		case GENERATION_MVC_CONFIGURATION:
			generator.strategy = new GenerationMvcConfigurationStrategy();
			break;
		case GENERATION_RESOURCE_PERSISTENCE_CONFIGURATION:
			generator.strategy = new GenerationResourcePersistenceConfigurationStrategy();
			break;	
		case GENERATION_SECURITY_SERVICE:
			generator.strategy = new GenerationSecurityServiceStrategy();
			break;
		case GENERATION_SECURITY_CONFIGURATION:
			generator.strategy = new GenerationSecurityConfigurationStrategy();
			break;
		case GENERATION_EXCEPTION_HANDLER:
			generator.strategy = new GenerationExceptionHandlerStrategy();
			break;
		default:
			throw new RuntimeException("Estratégia não encontrada.");
		}
		return generator;
	}
	
	public void generate(AnterosGenerationConfig config) throws Exception{
		strategy.generate(config);
	}

}
