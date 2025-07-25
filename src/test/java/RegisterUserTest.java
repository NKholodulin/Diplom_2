import Data.UserData;
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
    String accessToken;
    private boolean shouldDeleteUser = false;
    UserData userData = new UserData("holodTest@test.ru", "1234", "holod");

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

    @AfterEach
    void tearDown() {
        if (shouldDeleteUser) {
            deleteUser(accessToken)
                    .then().statusCode(SC_ACCEPTED);
        }
    }
}
