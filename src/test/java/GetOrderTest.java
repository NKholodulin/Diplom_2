import Data.IngredientData;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static Api.AuthApi.deleteUser;
import static Api.AuthApi.registerUser;
import static Api.IngredientApi.getIngredient;
import static Api.OrderApi.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest extends BaseTest {
    @BeforeEach
    public void setUp() {
        super.setUp();
        ingredient = getIngredient()
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("data[0]._id");
        ingredientData = new IngredientData(Arrays.asList(ingredient));
    }

    @Test
    @DisplayName("Check response and status code of /api/orders with authorization") // имя теста
    @Description("Basic test for /api/orders endpoint")
    public void testGetOrderWithAuthorizationResponseAndCode() {
        shouldDeleteUser = true;
        accessToken = registerUser(userData)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("accessToken");
        createOrderWithAuthorization(ingredientData, accessToken)
                .then()
                .statusCode(SC_OK);
        getOrder(accessToken)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("orders[0].number", notNullValue())
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check of /api/orders without authorization") // имя теста
    @Description("Negative test for /api/orders endpoint")
    public void testGetOrderWithoutAuthorization() {
        getOrderWithoutAuthorization()
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
