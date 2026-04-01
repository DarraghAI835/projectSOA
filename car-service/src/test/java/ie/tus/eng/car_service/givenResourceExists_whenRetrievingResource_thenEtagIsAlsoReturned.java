package ie.tus.eng.car_service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class givenResourceExists_whenRetrievingResource_thenEtagIsAlsoReturned {

	@Test
	public void givenResourceExists_whenRetrievingResource_thenEtagIsAlsoReturned() {
	    // Given
	    String uriOfResource = "http://localhost:8080/cars";

	    // When
	    Response findOneResponse = RestAssured.given().
	      header("Accept", "application/json").get(uriOfResource);

	    // Then
	    assertNotNull(findOneResponse.getHeader("ETag"));
	}

}
