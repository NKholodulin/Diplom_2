import Data.IngredientData;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static Api.AuthApi.*;
import static Api.IngredientApi.getIngredient;
import static Api.OrderApi.createOrderWithAuthorization;
import static Api.OrderApi.createOrderWithoutAuthorization;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseTest {

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
    @DisplayName("Check response and status code of /api/orders without authorization") // имя теста
    @Description("Basic test for /api/orders endpoint")
    public void testCreateOrderWithoutAuthorizationResponseAndCode() {
        createOrderWithoutAuthorization(ingredientData)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("order.number", notNullValue())
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check response and status code of /api/orders with authorization") // имя теста
    @Description("Basic test for /api/orders endpoint")
    public void testCreateOrderWithAuthorizationResponseAndCode() {
        shouldDeleteUser = true;
        accessToken = registerUser(userData)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .path("accessToken");
        createOrderWithAuthorization(ingredientData, accessToken)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("order.number", notNullValue())
                .statusCode(SC_OK);
    }

    @AfterEach
    void tearDown() {
        if (shouldDeleteUser) {
            deleteUser(accessToken)
                    .then().statusCode(SC_ACCEPTED);
        }
    }
}
