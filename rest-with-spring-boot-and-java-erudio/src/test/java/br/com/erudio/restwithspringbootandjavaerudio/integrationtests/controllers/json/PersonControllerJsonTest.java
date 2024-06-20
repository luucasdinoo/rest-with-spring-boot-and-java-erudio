package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.controllers.json;

import br.com.erudio.restwithspringbootandjavaerudio.configs.TesteConfigs;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    private static ObjectMapper objectMapper;

    private static PersonDto personDto;

    @BeforeAll
    public static void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        personDto = new PersonDto();
    }

    @Test
    @Order(1)
    void testCreate() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TesteConfigs.HEADER_PARAM_ORIGIN,TesteConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/person/v1")
                .setPort(TesteConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TesteConfigs.CONTENT_TYPE_JSON)
                .body(personDto)
                .when().post()
                .then().statusCode(200)
                .extract().body().asString();

        PersonDto createdPerson = objectMapper.readValue(content, PersonDto.class);
        personDto = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallnman", createdPerson.getLastName());
        assertEquals("New York City, New York, US", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(2)
    void testCreateWithWrongOrigin(){
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TesteConfigs.HEADER_PARAM_ORIGIN,TesteConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/person/v1")
                .setPort(TesteConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TesteConfigs.CONTENT_TYPE_JSON)
                .body(personDto)
                .when().post()
                .then().statusCode(403)
                .extract().body().asString();

        assertNotNull(content);

        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    void testFindById() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TesteConfigs.HEADER_PARAM_ORIGIN,TesteConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/person/v1")
                .setPort(TesteConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TesteConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", personDto.getId())
                .when().get("{id}")
                .then().statusCode(200)
                .extract().body().asString();

        PersonDto createdPerson = objectMapper.readValue(content, PersonDto.class);
        personDto = createdPerson;

        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallnman", createdPerson.getLastName());
        assertEquals("New York City, New York, US", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }

    @Test
    @Order(4)
    void testFindByIdWithWrongOrigin(){
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TesteConfigs.HEADER_PARAM_ORIGIN,TesteConfigs.ORIGIN_SEMERU)
                .setBasePath("/api/person/v1")
                .setPort(TesteConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TesteConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", personDto.getId())
                .when().get("{id}")
                .then().statusCode(403)
                .extract().body().asString();

        assertNotNull(content);

        assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        personDto.setFirstName("Richard");
        personDto.setLastName("Stallnman");
        personDto.setAddress("New York City, New York, US");
        personDto.setGender("Male");
    }
}
