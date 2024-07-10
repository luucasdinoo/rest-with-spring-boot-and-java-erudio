package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.controllers.auth;

import br.com.erudio.restwithspringbootandjavaerudio.configs.TesteConfigs;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.AccountCredentialDto;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.TokenDto;
import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerXMLTest extends AbstractIntegrationTest {

	private static TokenDto tokenDto;
	
	@Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialDto user =
				new AccountCredentialDto("leandro", "admin123");
		
		tokenDto = given()
				.basePath("/auth/signin")
					.port(TesteConfigs.SERVER_PORT)
					.contentType(TesteConfigs.CONTENT_TYPE_XML)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenDto.class);
		
		assertNotNull(tokenDto.getAccessToken());
		assertNotNull(tokenDto.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {
		
		var newTokenDto = given()
				.basePath("/auth/refresh")
				.port(TesteConfigs.SERVER_PORT)
				.contentType(TesteConfigs.CONTENT_TYPE_XML)
					.pathParam("username", tokenDto.getUsername())
					.header(TesteConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDto.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenDto.class);
		
		assertNotNull(newTokenDto.getAccessToken());
		assertNotNull(newTokenDto.getRefreshToken());
	}
}
