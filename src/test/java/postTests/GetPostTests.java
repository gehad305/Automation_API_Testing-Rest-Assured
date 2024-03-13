package postTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requests.PostRequests;
import requests.UserRequests;

public class GetPostTests {
    String id = "";
    String userId="";

    @BeforeClass
    public void preconditions() throws JsonProcessingException {
        int random = (int)(Math.random() * 500 + 1);
        String name = "Gehad";
        String gender = "female";
        String status = "active";
        String email = "gehadd"+random+"@gmail.com";
        Response userResponse = UserRequests.createUser(name,email,gender,status);
        JsonPath jsonPathOfUser = userResponse.jsonPath();
        userId = jsonPathOfUser.get("id").toString();
        id = PostRequests.
                createPost(userId,"Gehad Hassan","Yarab ang7 fel ISTQB")
                .jsonPath().get("id").toString();
    }

    @Test
    public void getSingleUserSuccess() {
        Response response = PostRequests.getSinglePost(id);
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().get("id").toString(),id, "id is not as expected");
    }

    @AfterClass
    public void deleteUser(){
        PostRequests.deletePost(id);
        UserRequests.deleteUser(userId);
    }
}
