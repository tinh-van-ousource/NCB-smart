package com.tvo;

import com.tvo.config.FileBannerStorageProperties;
import com.tvo.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class, FileBannerStorageProperties.class})
public class NcbSmartApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NcbSmartApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(NcbSmartApplication.class);
	}	
}
