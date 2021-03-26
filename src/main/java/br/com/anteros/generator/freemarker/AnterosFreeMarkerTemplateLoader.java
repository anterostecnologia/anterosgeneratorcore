package br.com.anteros.generator.freemarker;

import java.io.IOException;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import br.com.anteros.core.utils.ResourceUtils;
import br.com.anteros.core.utils.StringUtils;
import br.com.anteros.generator.AnterosGenerationConstants;
import freemarker.cache.ClassTemplateLoader;

public class AnterosFreeMarkerTemplateLoader extends ClassTemplateLoader {

	private Class<?> cl;

	public AnterosFreeMarkerTemplateLoader(Class<?> clazz, String string) {
		super(clazz, string);
		this.cl = clazz;
	}

	@Override
	protected URL getURL(String name) {
		String scannedPackage = "commons/*";
		if (name.contains(AnterosGenerationConstants.SQL)) {
			scannedPackage = "sql/*";
		} else if (name.contains(AnterosGenerationConstants.NO_SQL)) {
			scannedPackage = "nosql/*";
		}
		PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
		Resource[] resources;
		try {
			resources = scanner.getResources(scannedPackage);
			String normalizedName = StringUtils.replaceAll(name, "\\", "/");
			if (resources != null && resources.length > 0) {
				for (Resource resource : resources) {
					if (normalizedName.contains(resource.getFilename())) {
						return resource.getURL();
					}
				}
			}
		} catch (IOException e1) {
		}

		URL resource = ResourceUtils.getResource(name, cl);
		return resource;
	}

}
