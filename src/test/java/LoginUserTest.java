import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Api.AuthApi.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        accessToken = registerUser(userData)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("accessToken");
    }

    @Test
    @DisplayName("Check response and status code of /api/auth/login") // имя теста
    @Description("Basic test for /api/auth/login endpoint")
    public void testLoginUserResponseAndCode() {
        shouldDeleteUser = true;
        loginUser(userData)
        .then()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check login with incorrect params of /api/auth/login") // имя теста
    @Description("Negative test for /api/auth/login endpoint")
    public void testLoginUserWithIncorrectParams() {
        shouldDeleteUser = true;
        loginUser(userDataWithIncorrectParams)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
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
