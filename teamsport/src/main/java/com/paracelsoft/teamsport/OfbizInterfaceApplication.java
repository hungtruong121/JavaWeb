package com.paracelsoft.teamsport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.paracelsoft.teamsport.config.TaskSchedulerConfig;
import org.springframework.scheduling.TaskScheduler;

@SpringBootApplication
public class OfbizInterfaceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OfbizInterfaceApplication.class, args);
		
		TaskSchedulerConfig task = new TaskSchedulerConfig();
		TaskScheduler a = task.taskScheduler();
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OfbizInterfaceApplication.class);
    }
	
	
}
