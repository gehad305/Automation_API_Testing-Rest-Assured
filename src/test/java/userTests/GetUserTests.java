package userTests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requests.UserRequests;

public class GetUserTests {

    String id = "";

    @BeforeClass
    public void preconditions(){
        int random = (int)(Math.random() * 500 + 1);
        id = UserRequests.
                createUser("Gehad","gehadhassan"+random+"@gmail.com","female","active")
                .jsonPath().get("id").toString();
    }

    @Test
    public void getSingleUserSuccess() {
        Response response = UserRequests.getSingleUser(id);
        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().get("id").toString(),id, "id is not as expected");

        //Deserialization
        /* GetSingleUserResponseModel getSingleUserResponseModel = UserRequests.getSingleUserAsClass(id);
        Assert.assertEquals(getSingleUserResponseModel.id.toString(),id, "id is not as expected");
        Assert.assertNotNull(getSingleUserResponseModel.gender);
        Assert.assertNotNull(getSingleUserResponseModel.status);
        Assert.assertNotNull(getSingleUserResponseModel.name);*/
    }

    @AfterClass
    public void deleteUser(){
        UserRequests.deleteUser(id);
    }
}
