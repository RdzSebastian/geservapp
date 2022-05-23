package com.estonianport.geservapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class GeservappApplication extends SpringBootServletInitializer{
    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GeservappApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(GeservappApplication.class, args);
	}

}
