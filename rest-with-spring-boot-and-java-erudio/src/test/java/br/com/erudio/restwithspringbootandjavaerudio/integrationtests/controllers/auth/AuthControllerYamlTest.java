package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.controllers.auth;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import br.com.erudio.restwithspringbootandjavaerudio.configs.TesteConfigs;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.AccountCredentialDto;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.TokenDto;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.mapper.YMLMapper;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerYamlTest extends AbstractIntegrationTest {

	private static YMLMapper objectMapper;
	private static TokenDto tokenDto;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
	}
	
	@Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialDto user =
				new AccountCredentialDto("leandro", "admin123");

		RequestSpecification specification = new RequestSpecBuilder()
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		tokenDto = given().spec(specification)
				.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(
								TesteConfigs.CONTENT_TYPE_YML,
								ContentType.TEXT)))
				.accept(TesteConfigs.CONTENT_TYPE_YML)
				.basePath("/auth/signin")
					.port(TesteConfigs.SERVER_PORT)
					.contentType(TesteConfigs.CONTENT_TYPE_YML)
				.body(user, objectMapper)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenDto.class, objectMapper);
		
		assertNotNull(tokenDto.getAccessToken());
		assertNotNull(tokenDto.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {
		
		var newTokenDto = given()
				.config(
					RestAssuredConfig
						.config()
						.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(
								TesteConfigs.CONTENT_TYPE_YML,
								ContentType.TEXT)))
				.accept(TesteConfigs.CONTENT_TYPE_YML)
				.basePath("/auth/refresh")
				.port(TesteConfigs.SERVER_PORT)
				.contentType(TesteConfigs.CONTENT_TYPE_YML)
					.pathParam("username", tokenDto.getUsername())
					.header(TesteConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDto.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenDto.class, objectMapper);

		assertNotNull(newTokenDto.getAccessToken());
		assertNotNull(newTokenDto.getRefreshToken());
	}
}
