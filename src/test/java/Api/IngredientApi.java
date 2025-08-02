package Api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientApi {
    static final String INGREDIENT_API = "/api/ingredients";

    // метод для шага "Получить заказ конкретного пользователя":
    @Step("Send GET request to /api/ingredients")
    public static Response getIngredient() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get(INGREDIENT_API);
        return response;
    }
}
