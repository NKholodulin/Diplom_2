import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Api.AuthApi.deleteUser;
import static Api.AuthApi.registerUser;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class RegisterUserTest extends BaseTest {

    @Test
    @DisplayName("Check response and status code of /api/auth/register") // имя теста
    @Description("Basic test for /api/auth/register endpoint")
    public void testRegisterUserResponseAndCode() {
        shouldDeleteUser = true;
        Response user = registerUser(userData);
        user.then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(SC_OK);
        accessToken = user
                .then()
                .extract()
                .body()
                .path("accessToken");
    }

    @Test
    @DisplayName("Check register same user of /api/auth/register") // имя теста
    @Description("Negative test for /api/auth/register endpoint")
    public void negativeTestRegisterSameUser() {
        shouldDeleteUser = true;
        Response user = registerUser(userData);
        user.then().statusCode(SC_OK);
        accessToken = user
                .then()
                .extract()
                .body()
                .path("accessToken");
        registerUser(userData)
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .statusCode(SC_FORBIDDEN);
    }

    @Test
    @DisplayName("Check register user without password of /api/auth/register") // имя теста
    @Description("Negative test for /api/auth/register endpoint")
    public void negativeTestRegisterUserWithoutPassword() {
        registerUser(userDataWithoutPassword)
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(SC_FORBIDDEN);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteUser) {
            deleteUser(accessToken)
                    .then().statusCode(SC_ACCEPTED);
        }
    }
}
