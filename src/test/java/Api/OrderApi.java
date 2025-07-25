package Api;

import Data.IngredientData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    static final String ORDER_API = "/api/orders";

    // метод для шага "Создание заказа":
    @Step("Send POST request to /api/orders")
    public static Response createOrder(IngredientData ingredientData) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(ingredientData)
                .when()
                .post(ORDER_API);
        return response;
    }

    // метод для шага "Получить заказ конкретного пользователя":
    @Step("Send GET request to /api/orders")
    public static Response getOrder() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_API);
        return response;
    }
}
