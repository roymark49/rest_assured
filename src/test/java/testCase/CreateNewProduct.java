package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CreateNewProduct {
  
	HashMap<String, String> createPayLoad;
	public Map<String,String> createPayLoadMap(){
		createPayLoad = new HashMap<String, String>();
		createPayLoad.putIfAbsent("name", "amar notun phone");
		createPayLoad.putIfAbsent("price", "199");
		createPayLoad.putIfAbsent("description", "The best pillow for amazing programmers.");
		createPayLoad.putIfAbsent("category_id", "2");
		return createPayLoad;
		
	}
	@Test
	public void createAProduct() {
		//creating HashMap to store payLoad key and value. 
		
		
		//Response= interface and response is "reference variable"
		Response response = 
		//after . all the methods should be in restAssured class. so lets make restassured(imort) static and put * to bring
		//everything from that class.  
		given()
		
				//you can use log().all() anywhere to get all detailes in console
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type","application/json; charset=UTF-8")//key and value
			.auth().preemptive().basic("roymark49", "abc123")
			//.body(new File("src\\main\\resources\\data\\createProductPayload.json"))
			.body(createPayLoadMap())
		.when()
			.post("/create.php").
		then()
			.log().all()	//with log().all() you will get to see all product list in console also, you have change 
							//console's capacity from preference -->console
			.extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("Actual Status Code: " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 201);
		
		String responseheader = response.getHeader("Content-Type");
		System.out.println("Responseheader: " + responseheader);
		Assert.assertEquals(responseheader,"application/json; charset=UTF-8");
		
	//Assert.assertEquals(actualResponseTime, 200);
		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body: " + actualResponseBody);
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String ProductMessage = jp.get("message");
		System.out.println("Product Message : " + ProductMessage);
		Assert.assertEquals(ProductMessage, "Product was created.");
		}
	
	@Test(priority=2)
	public void readProductID() {

		Response response = given()
								.baseUri("https://techfios.com/api-prod/api/product")
								.header("Content-Type","application/json; charset=UTF-8")
								.auth().basic("demo@techfios.com","abc123") //using basic authorization as username & password
								.relaxedHTTPSValidation().
							when()
								.get("/read.php").
							then()
							.extract().response();
		
		//to validate response status
		int actualResponseStatus = response.getStatusCode();
		System.out.println("Actual Response Status : " + actualResponseStatus);
		Assert.assertEquals(actualResponseStatus, 200);
		
		
		//to validate the response body and show the first product ID is not null
		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body: " + actualResponseBody);
		
		JsonPath jp = new JsonPath(actualResponseBody);
		String firstProductID = jp.get("records[0].id");
		System.out.println("First Product ID : " + firstProductID);
		
		
	}
	
}
