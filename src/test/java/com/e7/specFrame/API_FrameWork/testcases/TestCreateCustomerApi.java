package com.e7.specFrame.API_FrameWork.testcases;

import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.e7.specFrame.API_FrameWork.TestUtils.ConfigProperty;
import com.e7.specFrame.API_FrameWork.TestUtils.DataProviderClass;
import com.e7.specFrame.API_FrameWork.setUp.APISetUp;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Hashtable;
/*
 * URI--https://api.stripe.com/v1/customers 
 * methodType-Post
 * argument - email,description,balance
 * Auth- Basic Auth = sk_test_51I3kh5CDx93T2wimjhfQBx4cYMzyqXA35icHOOd8pWH2nv8gwZaKAxRxbRnWMKSPHuGJj9biIvVQTfL9G4wAu8pW00ijzsEJ7V
 */

public class TestCreateCustomerApi extends APISetUp {
	@Test(dataProviderClass = DataProviderClass.class, dataProvider = "dp",enabled=false)
	public void validateCreateCustomerAPI(Hashtable<String, String> data) {

		testLevelLog.get().assignAuthor("Emran");
		testLevelLog.get().assignCategory("Regression");

		RequestSpecification spec = setRequestSpecification().formParam("email", data.get("email"))
				.formParam("description", data.get("description")).formParam("balance", 100000).log().all();

		System.out.println("========================================================");

		Response response = spec.post("customers");
		testLevelLog.get().info(response.asString());
		
		//fetch the email from the response 
		String emailInResponse = response.path("email");
		System.out.println("email in Response :-->" + emailInResponse);
		//fetch the description from the response
		String descriptionInResponse = response.path("description");
		System.out.println("description in Response :-->" + descriptionInResponse);
		
		System.out.println("Footer values is :-->" + response.path("invoice_settings.footer"));
		
		JsonPath responseJson = new JsonPath(response.asString());
		
		System.out.println("Email using jsonPath:-->" +responseJson.get("email"));
		
		response.prettyPrint();

		Assert.assertEquals(response.getStatusCode(), 200);

	}
	
	@Test(dataProviderClass = DataProviderClass.class, dataProvider = "dp")
	public void fetchCustomers(Hashtable<String, String> data) {
		

		testLevelLog.get().assignAuthor("Emran");
		testLevelLog.get().assignCategory("Regression");

		RequestSpecification spec = setRequestSpecification().log().all();

		System.out.println("========================================================");

		Response response = spec.request(data.get("methodType"),data.get("endPoint"));
		//response.prettyPrint();
		
		int lengthOfData = response.path("data.size()");
		String expectedDefaultSource = "card_1IBR8ACDx93T2wimON6krjpK";
		
		for(int i=0;i<lengthOfData;i++) 
		{
			
			String actualDefaultSource = response.path("data["+i+"].default_source");
			if(expectedDefaultSource.equals(actualDefaultSource)) {
				System.out.println("The id number for card:--> "+response.path("data["+i+"].id"));
				break;
			}
		}
		
		/*
		 * System.out.println("Size of the data :-->" + response.path("data.size()"));
		 * 
		 * System.out.println("Size of the data 2 :-->" +
		 * response.path("data[0].size()"));
		 * 
		 * ArrayList<String> listOfIds = response.path("data.id");
		 * 
		 * System.out.println(listOfIds);
		 */
	}


}
