import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderPUTPATCHTest {

    private static Faker faker;
    private String fakeEmail;
    private String fakeName;
    private String fakeUserName;
    private String fakePhone;
    private String fakeWWW;

    // Uruchomi się tylko raz przed wszystkimi testami
    @BeforeAll
    public static void beforeAll() {
        faker = new Faker();
    }

    // Uruchomi się przed każdymi testami
    @BeforeEach
    public void beforeEach() {
        fakeEmail = faker.internet().emailAddress();
        fakeName = faker.name().name();
        fakeUserName = faker.name().username();
        fakePhone = faker.phoneNumber().phoneNumber();
        fakeWWW = faker.internet().url();

    }

    @Test
    public void jsonPlaceholderUpdateUserPUTTest() {

        JSONObject user = new JSONObject();

        user.put("name", fakeName);
        user.put("username", fakeUserName);
        user.put("email", fakeEmail);
        user.put("phone", fakePhone);
        user.put("website", fakeWWW);

        JSONObject geo = new JSONObject();

        geo.put("lat", "-37.3159");
        geo.put("lng", "81.1496");

        JSONObject address = new JSONObject();

        address.put("street", "Kulas Light");
        address.put("suite", "Apt. 556");
        address.put("city", "Gwenborough");
        address.put("zipcode", "92998-3874");
        address.put("geo", geo);

        user.put("address", address);

        JSONObject company = new JSONObject();

        company.put("name", "Romaguera-Crona");
        company.put("catchPhrase", "Multi-layered client-server neural-net");
        company.put("bs", "harness real-time e-markets");

        user.put("company", company);

//
//        String jsonBody = "{\n" +
//                "    \"name\": \"Pawel Testowy PUT\",\n" +
//                "    \"username\": \"PawelPUT\",\n" +
//                "    \"email\": \"pawelput@test.pl\",\n" +
//                "    \"address\": {\n" +
//                "        \"street\": \"Kulas Light\",\n" +
//                "        \"suite\": \"Apt. 556\",\n" +
//                "        \"city\": \"Gwenborough\",\n" +
//                "        \"zipcode\": \"92998-3874\",\n" +
//                "        \"geo\": {\n" +
//                "            \"lat\": \"-37.3159\",\n" +
//                "            \"lng\": \"81.1496\"\n" +
//                "        }\n" +
//                "    },\n" +
//                "    \"phone\": \"1-770-736-8031 x56442\",\n" +
//                "    \"website\": \"hildegard.org\",\n" +
//                "    \"company\": {\n" +
//                "        \"name\": \"Romaguera-Crona\",\n" +
//                "        \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
//                "        \"bs\": \"harness real-time e-markets\"\n" +
//                "    }\n" +
//                "}";


        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

//        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals(fakeName, jsonPath.get("name"));
        Assertions.assertEquals(fakeUserName, jsonPath.get("username"));
        Assertions.assertEquals(fakeEmail, jsonPath.get("email"));
    }


    @Test
    public void jsonPlaceholderUpdateUserPATCHTest() {


        JSONObject email = new JSONObject();
        email.put("email", fakeEmail);


//        String jsonBody = "{\n" +
//                "    \"email\": \"pawelput1@test.pl\"\n" +
//                "}";


        Response response = given()
                .contentType("application/json")
                .body(email.toString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();

        Assertions.assertEquals(fakeEmail, jsonPath.get("email"));
    }
}
