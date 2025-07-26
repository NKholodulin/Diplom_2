import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Api.AuthApi.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest extends BaseTest {

    @Test
    @DisplayName("Check response and status code of /api/auth/user") // имя теста
    @Description("Basic test for /api/auth/user endpoint")
    public void testUpdateUserResponseAndCode() {
        shouldDeleteUser = true;
        accessToken = registerUser(userData)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("accessToken");
        patchUser(userDataForUpdate, accessToken)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check update user without authorization of /api/auth/user") // имя теста
    @Description("Negative test for /api/auth/user endpoint")
    public void testUpdateUserWithoutAuthorization() {
        patchUserWithoutAuthorization(userDataForUpdate)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(SC_UNAUTHORIZED);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteUser) {
            deleteUser(accessToken)
                    .then().statusCode(SC_ACCEPTED);
        }
    }
}
