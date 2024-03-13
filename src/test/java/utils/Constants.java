package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Constants {
    public static String baseURL = "https://gorest.co.in/public/v2/";
    public static String userEndPoint = "users/";
    public static String postEndPoint = "posts/";

    public static Map<String, String> generalHeaders(){
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer 43afb5b858c0e581db0fef6b911e6cc225cc07e2a1276d8c728e96e40e8db35b");
        return headers;
    }
    public static Map<String, String> bodyMapOfCreatePost(String user_id, String title, String body){
        Map<String,String> bodyMap = new HashMap<>();
        bodyMap.put("user_id",user_id);
        bodyMap.put("title",title);
        bodyMap.put("body",body);
        return bodyMap;
    }
    public static String generateRandomIdNotExist(List<String> existingIds) {
        Random random = new Random();

        String randomId;
        do {
            // Generate a random string ID
            randomId = generateRandomString();

            // Check if the generated ID already exists in the existingIds list
        } while (existingIds.contains(randomId));

        return randomId;
    }
    public static String generateRandomString() {
        // Define the characters that can be used in the random string
        String numbers = "0123456789";

        // Specify the length of the random string
        int length = 5;

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        // Generate random characters to form the string
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(numbers.length());
            randomString.append(numbers.charAt(index));
        }

        return randomString.toString();
    }
}