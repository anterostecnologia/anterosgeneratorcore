package br.com.anteros.generator;

import br.com.anteros.generator.config.AnterosGenerationConfig;
import br.com.anteros.generator.config.strategy.GenerationConfigurationStrategy;
import br.com.anteros.generator.config.strategy.GenerationControllerStrategy;
import br.com.anteros.generator.config.strategy.GenerationRepositoryStrategy;
import br.com.anteros.generator.config.strategy.GenerationSecurityConfigurationStrategy;
import br.com.anteros.generator.config.strategy.GenerationSecurityServiceStrategy;
import br.com.anteros.generator.config.strategy.GenerationServiceStrategy;

public class AnterosGenerator {
	
	public final static int GENERATION_SERVICE=0;
	public final static int GENERATION_REPOSITORY=1;
	public final static int GENERATION_CONTROLLER=2;
	public final static int GENERATION_CONFIGURATION=3;
	public final static int GENERATION_SECURITY_SERVICE=4;
	public final static int GENERATION_SECURITY_CONFIGURATION=5;
	
	private AnterosGenerationStrategy strategy=null;
	
	private AnterosGenerator(){
		
	}

	public static AnterosGenerator create(int type) {
		AnterosGenerator generator = new AnterosGenerator();
		switch (type) {
		case GENERATION_SERVICE:
			generator.strategy = new GenerationServiceStrategy();
			break;
		case GENERATION_REPOSITORY:
			generator.strategy = new GenerationRepositoryStrategy();
			break;
		case GENERATION_CONTROLLER:
			generator.strategy = new GenerationControllerStrategy();
			break;
		case GENERATION_CONFIGURATION:
			generator.strategy = new GenerationConfigurationStrategy();
			break;
		case GENERATION_SECURITY_SERVICE:
			generator.strategy = new GenerationSecurityServiceStrategy();
			break;
		case GENERATION_SECURITY_CONFIGURATION:
			generator.strategy = new GenerationSecurityConfigurationStrategy();
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
