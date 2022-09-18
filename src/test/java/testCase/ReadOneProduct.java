package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadOneProduct {
//	given: all input details (baseURI, Headers, Payload/Body,QueryParameters,Authorization)
//	when:submit api requests (Http method, Endpoint/Resource)
//	then:validate response(status code, Headers, responseTime, Payload/Body)
//	*
//	02.ReadOneProduct
//	HTTP Method: GET
//	EndpointUrl: https://techfios.com/api-prod/api/product/read_one.php
//	Authorization:
//	Basic Auth/ Bearer Token
//	Header:
//	"Content-Type" : "application/json"
//	QueryParam: 
//	"id":"value"
//	Status Code: 200
	@Test
	public void readOneProduct() {
		//Response= interface and response is "reference variable"
		Response response = 
		//after . all the methods should be in restAssured class. so lets make restassured(imort) static and put * to bring
		//everything from that class.  
		given()
		
				//you can use log().all() anywhere to get all detailes in console
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type","application/json")//key and value
			.header("Authorization", "Bearer fnadbsfksadbj")
			.queryParam("id", "4732")
		.when()
			.get("read_one.php").
		then()
			.log().all()	//with log().all() you will get to see all product list in console also, you have change 
							//console's capacity from preference -->console
			.extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("Actual Status Code: " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		
		String responseheader = response.getHeader("Content-Type");
		System.out.println("Responseheader: " + responseheader);
		Assert.assertEquals(responseheader,"application/json");
		
	//Assert.assertEquals(actualResponseTime, 200);
		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body: " + actualResponseBody);
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String ProductID = jp.get("id");
		System.out.println("Product ID : " + ProductID);
		Assert.assertEquals(ProductID, "4732");
		
		String ProductName = jp.get("name");
		System.out.println("Product Name : " + ProductName);
		Assert.assertEquals(ProductName, "Amazing Pillow 2.0 By Aref");
		
		String productDescription = jp.get("description");
		System.out.println("Product Description : " + productDescription);
		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
		
		String productPrice = jp.get("price");
		System.out.println("Product Price : " + productPrice);
		Assert.assertEquals(productPrice, "199");
		
		
	
	}
	
	
	
}
