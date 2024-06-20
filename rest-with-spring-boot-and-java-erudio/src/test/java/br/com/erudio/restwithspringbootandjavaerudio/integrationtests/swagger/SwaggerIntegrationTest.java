package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.swagger;

import static io.restassured.RestAssured.given;

import br.com.erudio.restwithspringbootandjavaerudio.configs.TesteConfigs;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerUiPage() {
        var content = given()
                .baseUri("/swagger-ui/index.html")
                .port(TesteConfigs.SERVER_PORT)
                .when().get()
                .then().statusCode(200)
                .extract().body().asString();

        assertTrue(content.contains("Swagger UI"));
    }
}
