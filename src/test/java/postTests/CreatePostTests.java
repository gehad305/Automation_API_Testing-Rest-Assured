package postTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import requests.PostRequests;
import requests.UserRequests;
import utils.Constants;
import java.util.ArrayList;

public class CreatePostTests {
    String id = "";
    String userId="";
    ArrayList<String> idOfAllUsers = new ArrayList<>();
    int userCount;

    @BeforeClass
    public void createUser(){
        int random = (int)(Math.random() * 500 + 1);
        String name = "Gehad";
        String gender = "female";
        String status = "active";
        String email = "gehaadd"+random+"@gmail.com";
        Response userResponse = UserRequests.createUser(name,email,gender,status);
        JsonPath jsonPathOfUser = userResponse.jsonPath();
        userId = jsonPathOfUser.get("id").toString();
    }

    @Test
    public void createPostTest() throws JsonProcessingException {
        String title = "API Testing";
        String body = "Hello World!";
        Response response = PostRequests.createPost(userId,title,body);
        response.prettyPrint();
        response.then().statusCode(201);
        JsonPath jsonPath = response.jsonPath();
        id = jsonPath.get("id").toString();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(jsonPath.getString("user_id"),userId, "User ID is not as expeceted");
        softAssert.assertEquals(jsonPath.getString("title"),title, "Name is not as expeceted");
        softAssert.assertNotNull(jsonPath.get("id"), "id is null");
        softAssert.assertEquals(jsonPath.getString("body"),body, "Gender is not as expeceted");
        PostRequests.deletePost(id);
        softAssert.assertAll();
    }

    @Test
    public void checkValidationOfAllFields() throws JsonProcessingException { //will fail because user id display "must exist" if it is blank
        Response response = PostRequests.createPost("","","");
        response.then().statusCode(422);
        JsonPath jsonPath = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();
        for(int i=0; i<3 ;i++) {
            softAssert.assertTrue(jsonPath.getString("["+i+"].message").contains("can't be blank"), "message is not as expected");
        }
        softAssert.assertAll();
    }
    @Test
    public void checkValidationOfUserIdField() throws JsonProcessingException {
        String title = "Gehad Hassan";
        String body = "Hello API Test Automation using Rest Assured in JAVA!";
        Response usersResponse = UserRequests.getAllUser();
        JsonPath usersJsonPath = usersResponse.jsonPath();
        userCount =usersJsonPath.getList("").size();
        for(int i=0;i<userCount;i++){
            idOfAllUsers.add(usersJsonPath.getString("["+i+"].id"));
        }
        String randomIdNotExist = Constants.generateRandomIdNotExist(idOfAllUsers);
        Response response = PostRequests.createPost(randomIdNotExist,title,body);
        response.then().statusCode(422);
        JsonPath jsonPath = response.jsonPath();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(jsonPath.getString("message").contains("must exist"), "message is not as expected");
    }

    @AfterClass
    public void deleteUser(){
        UserRequests.deleteUser(userId);
    }
}
