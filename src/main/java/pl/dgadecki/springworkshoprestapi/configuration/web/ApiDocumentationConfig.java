package pl.dgadecki.springworkshoprestapi.configuration.web;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApiDocumentationConfig {

    @Bean
    public OpenAPI openApi() {
        Contact contact = new Contact()
                .name("Anna Gasiorowska")
                .email("annagasiorowska.it@gmail.com");

        License license = new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("Workshop-homework")
                .description("API prepared as a homework for Spring Boot Workshop")
                .version("1.0.0")
                .license(license)
                .contact(contact);

        List<Server> servers = List.of(
                new Server().url("http://localhost:8080").description("Local"),
                new Server().url("http://workshop-dev.pl").description("Development"),
                new Server().url("http://workshop-prod.pl").description("Production"),
                new Server().url("http://workshop-test.pl").description("Test")
        );

        return new OpenAPI().info(info).servers(servers);
    }
    @Bean
    public GroupedOpenApi articlesOpenApi() {
        return GroupedOpenApi.builder()
                .group("Articles")
                .pathsToMatch("/api/v1/articles/**")
                .build();
    }
    @Bean
    public GroupedOpenApi customersOpenApi() {
        return GroupedOpenApi.builder()
                .group("Customers")
                .pathsToMatch("/api/v1/customers/**")
                .build();
    }




}
