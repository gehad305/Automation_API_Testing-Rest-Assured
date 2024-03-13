package requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.Constants;

public class PostRequests {
    public static Response createPost(String user_id, String title, String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); //serialization
        String jsonString = mapper.writeValueAsString(Constants.bodyMapOfCreatePost(user_id, title, body));
        return RestAssured.given().log().all().
                contentType(ContentType.JSON).
                headers(Constants.generalHeaders())
                .body(jsonString).post(Constants.baseURL+ Constants.postEndPoint);
    }

    public static Response getSinglePost(String id){
        return RestAssured.given().log().all().headers(Constants.generalHeaders())
                .get(Constants.baseURL + Constants.postEndPoint + id);
    }

    public static Response deletePost(String id){
        return RestAssured.given().log().all().headers(Constants.generalHeaders())
                .delete(Constants.baseURL + Constants.postEndPoint + id);
    }
}
