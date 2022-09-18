package testCase;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;
//So here instead of write 3 lines of code for BDD methods (given,when,then) we can just *. 

public class ReadAllProducts {
	/*given: all input details (baseURI, Headers, Payload/Body,QueryParameters,Authorization)
	when:submit api requests (Http method, Endpoint/Resource)
	then:validate response(status code, Headers, responseTime, Payload/Body)
	*
	http method: GET
	Headers:
	Content-Type: application/json;charset=UTF-8
	Authorization: 
	basicAuth: username,password
	EndPoint_URL: https://techfios.com/api-prod/api/product/read.pstathp
	statusCode: 200 OK*/
	@Test
	public void readAllProducts() {
		//Response= interface and response is "reference variable"
		Response response = 
		//after . all the methods should be in restAssured class. so lets make restassured(imort) static and put * to bring
		//everything from that class.  
		given()
		
				//you can use log().all() anywhere to get all detailes in console
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type","application/json;charset=UTF-8")//key and value
			.auth().preemptive().basic("roymark49", "abc123").
		when()
			.get("/read.php").
		then()
			.log().all()	//with log().all() you will get to see all product list in console also, you have change 
							//console's capacity from preference -->console
			.extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("Actual Status Code: " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		
		String responseheader = response.getHeader("Content-Type");
		System.out.println("Responseheader: " + responseheader);
		Assert.assertEquals(responseheader,"application/json; charset=UTF-8");
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Actual Response Time: " + actualResponseTime);
		
		if(actualResponseTime<=2000) {
			System.out.println("Response time is whithn the range");
		}else {
			System.out.println("Response time is out of range");
		}
		//Assert.assertEquals(actualResponseTime, 200);
		String actualResponseBody = response.getBody().asString();
		System.out.println("Actual Response Body: " + actualResponseBody);
		
		JsonPath jp = new JsonPath(actualResponseBody);
		String firstProductId = jp.get("records[0].id");
		System.out.println("First Product Id: " + firstProductId );
		
		if(firstProductId != null) {
			System.out.println("First Product Id is not null.");
		}else {
			System.out.println("First Product Id is null!!");
		}
	}
	
	
	
}
