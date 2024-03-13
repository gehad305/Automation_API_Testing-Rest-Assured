package requests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
//import models.GetSingleUserResponseModel;
import utils.Constants;

public class UserRequests {
    public static Response createUser(String name, String email, String gender, String status){
        return RestAssured.given().log().all().
                contentType(ContentType.JSON).
                headers(Constants.generalHeaders())
                .body(" {\n" +
                        "    \"name\": \""+name+"\",\n" +
                        "    \"email\": \""+email+"\",\n" +
                        "    \"gender\": \""+gender+"\",\n" +
                        "    \"status\": \""+status+"\"\n" +
                        " }").post(Constants.baseURL+ Constants.userEndPoint);
    }

    public static Response getSingleUser(String id){
        return RestAssured.given().log().all().headers(Constants.generalHeaders())
                .get(Constants.baseURL + Constants.userEndPoint + id);
    }

    //Deserialization
    /*public static GetSingleUserResponseModel getSingleUserAsClass(String id){
        return getSingleUser(id).as(GetSingleUserResponseModel.class);
    }*/

    public static Response getAllUser(){
        return RestAssured.given().log().all().headers(Constants.generalHeaders())
                .get(Constants.baseURL + Constants.userEndPoint);
    }

    public static Response deleteUser(String id){
        return RestAssured.given().log().all().headers(Constants.generalHeaders())
                .delete(Constants.baseURL + Constants.userEndPoint + id);
    }
}
