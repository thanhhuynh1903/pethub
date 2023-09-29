package com.pethub.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// expose a directory on file system - to be accessible by the clients
		String dirName = "user-photos";
		Path userPhotosDir = Paths.get(dirName);

		String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + userPhotosPath + "/");
		// map directory to the physical - the absolute path on the file system
		// with the prefix if file
		// /** allow all the files under this directory will be available to web clients

		String categoryImagesDirName = "../category-images";
		Path categoryImagesDir = Paths.get(categoryImagesDirName);

		String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/category-images/**")
				.addResourceLocations("file:/" + categoryImagesPath + "/");
	}

}
