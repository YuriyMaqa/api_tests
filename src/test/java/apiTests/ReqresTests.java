package apiTests;


import apiTests.models.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTests {

    String email = "eve.holt@reqres.in",
           password = "cityslicka";


    @BeforeAll
    static void  setUp() {
        RestAssured.baseURI = "https://reqres.in";

    }
    @Test
    void loginTest() {
        var request = new LoginRequest(email, password);

        given()
                .log().uri()
                .log().body()
                .body(request)
                .contentType(JSON)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void missingPasswordLoginTest() {
        var loginWithoutPassword = new LoginWithoutPassword(email);
        given()
                .log().uri()
                .log().body()
                .body(loginWithoutPassword)
                .contentType(JSON)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void userLogin() {

        var userRequest = new UserRequest("morpheus", "leader");
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(userRequest)
                .when()
                .post("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void checkTotalWithSomeLogs() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .body("data.id", is(2))
                .body("support.text",
                        is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void putUsersTest() {
        PutUsersRequest putRequest = new PutUsersRequest("morpheus", "zion resident");


        LocalDate intDate = LocalDate.now();
        String date = intDate.toString();

        PutUserResponse response =
                given()
                        .log().uri()
                        .log().body()
                        .contentType(JSON)
                        .body(putRequest)
                        .when()
                        .put("/api/users/2")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(PutUserResponse.class);
        Assertions.assertEquals(putRequest.getJob(), "zion resident");
        Assertions.assertEquals(putRequest.getName(), "morpheus");
        Assertions.assertTrue(response.updatedAt.contains(date));
    }
}
