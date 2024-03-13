package userTests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import requests.UserRequests;

public class CreateUserTests {

    String id = "";

    @Test
    public void createUserTest(){
        int random = (int)(Math.random() * 500 + 1);
        String name = "Gehad";
        String gender = "female";
        String status = "active";
        String email = "gehaaaad"+random+"@gmail.com";
        Response response = UserRequests.createUser(name,email,gender,status);
        response.prettyPrint();
        response.then().statusCode(201);
        JsonPath jsonPath = response.jsonPath();
        id = jsonPath.get("id").toString();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(jsonPath.getString("name"),name, "Name is not as expeceted");
        softAssert.assertNotNull(jsonPath.get("id"), "id is null");
        softAssert.assertEquals(jsonPath.getString("gender"),gender, "Gender is not as expeceted");
        UserRequests.deleteUser(id);
        softAssert.assertAll();
    }

    @Test
    public void checkValidationOfAllFields(){
        Response response = UserRequests.createUser("","","","");
        response.then().statusCode(422);
        JsonPath jsonPath = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();
        for(int i=0; i<4 ;i++) {
            softAssert.assertTrue(jsonPath.getString("["+i+"].message").contains("can't be blank"), "message is not as expected");
        }
        softAssert.assertAll();
    }
}
