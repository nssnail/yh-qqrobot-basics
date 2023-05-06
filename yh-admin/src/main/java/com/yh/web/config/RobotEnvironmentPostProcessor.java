package com.yh.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class RobotEnvironmentPostProcessor implements EnvironmentPostProcessor {
    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String propertiesFile = "qqrobot.yml";
        Resource resource=new ClassPathResource(propertiesFile);
        PropertySource<?> propertySource = loadProperties(resource);
        //添加到propertySourcesList中
        environment.getPropertySources().addLast(propertySource);
    }

    private PropertySource<?> loadProperties(Resource resource){
        if(!resource.exists()){
            throw new RuntimeException("file not exist");
        }
        try {
            return loader.load("qqrobot",resource).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}