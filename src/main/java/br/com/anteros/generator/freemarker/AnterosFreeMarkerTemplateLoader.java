package br.com.anteros.generator.freemarker;

import java.net.URL;

import br.com.anteros.core.utils.ResourceUtils;
import freemarker.cache.ClassTemplateLoader;

public class AnterosFreeMarkerTemplateLoader extends ClassTemplateLoader {

	private Class<?> cl;

	public AnterosFreeMarkerTemplateLoader(Class<?> clazz, String string) {
		super(clazz, string);
		this.cl = clazz;
	}

	@Override
	protected URL getURL(String name) {
		return ResourceUtils.getResource(name, cl);
	}

}
