package com.victorsantos.customer.transaction.application.common.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@Slf4j
public class CommonDocConfiguration {

    private static final String EXAMPLES_LOCATION = "classpath*:swagger-examples";

    @Bean
    public OpenAPI openAPI() {
        var openApi = new OpenAPI();
        openApi.components(components());
        return openApi;
    }

    private Components components() {
        var components = new Components();
        addExamples(components);
        return components;
    }

    private void addExamples(Components components) {
        var resourcesResolver = new PathMatchingResourcePatternResolver();
        try {
            var resources = resourcesResolver.getResources(EXAMPLES_LOCATION + "/**/*.json");
            for (Resource resource : resources) {
                var example = loadExampleFromResource(resource);
                components.addExamples(resource.getFilename(), example);
            }
        } catch (Exception e) {
            log.error("Error loading examples", e);
        }
    }

    private Example loadExampleFromResource(Resource resource) throws IOException {
        String fileContent = resource.getContentAsString(StandardCharsets.UTF_8);
        Example example = new Example();
        example.setValue(fileContent);
        return example;
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }
}
