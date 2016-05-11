package br.com.anteros.generator;

import br.com.anteros.generator.config.AnterosGenerationConfig;

public interface AnterosGenerationStrategy {
	
	public void generate(AnterosGenerationConfig config) throws Exception;
}
