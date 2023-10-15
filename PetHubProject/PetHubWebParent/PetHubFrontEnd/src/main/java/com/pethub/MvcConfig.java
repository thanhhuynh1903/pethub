package com.pethub;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		// Category
		exposeDirectory("../category-images", registry);

		// Brand
		exposeDirectory("../brand-logos", registry);

		// Product
		exposeDirectory("../product-images", registry);

		// Setting
		exposeDirectory("../site-logo", registry);
	}

	private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
		Path path = Paths.get(pathPattern);
		String absolutePath = path.toFile().getAbsolutePath();
		String logicalPath = pathPattern.replace("../", "") + "/**";
		// map directory to the physical - the absolute path on the file system
		// with the prefix if file
		// /** allow all the files under this directory will be available to web clients
		registry.addResourceHandler(logicalPath).addResourceLocations("file:/" + absolutePath + "/");

	}
}
