import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    private final RouteDefinitionLocator locator;

    public SwaggerConfig(RouteDefinitionLocator locator) {
        this.locator = locator;
    }

    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        
        if (definitions != null) {
            definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId() != null && routeDefinition.getId().matches(".*SERVICE"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("SERVICE", "").toLowerCase();
                    groups.add(GroupedOpenApi.builder()
                        .pathsToMatch("/" + name + "/**")
                        .group(name) 
                        .build());
                });
        }
        
        return groups;
    }
}
