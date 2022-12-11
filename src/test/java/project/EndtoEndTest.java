package project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndtoEndTest {

	Response response;
	
public Response GetEmployee() {
	
	RestAssured.baseURI = "http://localhost:3000";
	
	RequestSpecification request = RestAssured.given();
	
	Response response = request.get("employees");
		
		return response;
	}
 
public Response CreateEmployee(String Name, int a) {
	 Map<String, Object> mapobj = new HashMap<String, Object>();
		
		mapobj.put("name",Name);
		mapobj.put("salary",a);
		
			
			RestAssured.baseURI = "http://localhost:3000";
			
			RequestSpecification request = RestAssured.given();
			
			Response response = request.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(mapobj)
					.post("employees/create");
			
			return response;
}

public Response GetSingleEmployee(int a) {
	 
	RestAssured.baseURI = "http://localhost:3000";
	
	RequestSpecification request = RestAssured.given();
	
	response = request.get("employees/" + a);
	
	return response;

}

public Response UpdateEmployee(String Name,int id) {
	
	Map<String, Object> mapobj = new HashMap<String, Object>();
	
	mapobj.put("name",Name);
	mapobj.put("salary","8000");
		
		RestAssured.baseURI = "http://localhost:3000";
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(mapobj)
				.put("employees/" + id);
		
		return response;
}

public Response DeleteEmployee(int id) {
	
	RestAssured.baseURI = "http://localhost:3000";
	
	RequestSpecification request = RestAssured.given();
	
	Response response = request.delete("employees/" + id);
	
	return response;
}
 
 @Test
 public void endtoendtest() {
	 
//TEST CASE A
	    System.out.println("GET ALL EMPLOYEE");
	    response = GetEmployee();
	    System.out.println(response.getBody().asString());
	 
	    int responsecode = response.getStatusCode();
		Assert.assertEquals(200, responsecode);

//TEST CASE B		
		System.out.println("CREATE NEW EMPLOYEE");
		response = CreateEmployee("John",8000);
		int responsecodea = response.getStatusCode();
		Assert.assertEquals(201, responsecodea);
		JsonPath Jpath = response.jsonPath();
		int EEID = Jpath.get("id");

//TEST CASE C		
		System.out.println("GET CREATED SINGLE NEW EMPLOYEE");
		response = GetSingleEmployee(EEID);
		 System.out.println(response.getBody().asString());
		 Assert.assertEquals(200, response.statusCode());
		 Assert.assertEquals(200, response.statusCode());
		 String ResponseBody1 = response.getBody().asString();
			Assert.assertTrue(ResponseBody1.contains("John"));

//TEST CASE D			
		System.out.println("UPDATE SINGLE NEW EMPLOYEE");
		response = UpdateEmployee("Smith",EEID);
		Assert.assertEquals(200, response.statusCode());

//TEST CASE E		
		System.out.println("GET UPDATED SINGLE NEW EMPLOYEE");
		response = GetSingleEmployee(EEID);
		System.out.println(response.getBody().asString());
		Assert.assertEquals(200, response.statusCode());
		String ResponseBody = response.getBody().asString();
		Assert.assertTrue(ResponseBody.contains("Smith"));

//TEST CASE F
		System.out.println("DELETE NEW EMPLOYEE");
		response = DeleteEmployee(EEID);
		Assert.assertEquals(200, response.statusCode());

//TEST CASE G
		System.out.println("GET DELETED SINGLE NEW EMPLOYEE");
		response = GetSingleEmployee(EEID);
		Assert.assertEquals(404, response.statusCode());

//TEST CASE H
		System.out.println("GET ALL EMPLOYEE");
		response = GetEmployee();
		System.out.println(response.getBody().asString());
		JsonPath Jpath3 = response.jsonPath();
		List<String> names3 = Jpath3.get("name");
		Assert.assertFalse(names3.contains("Smith"));
		 
		 
}


} 


