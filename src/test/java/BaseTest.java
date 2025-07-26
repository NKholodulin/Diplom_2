import Data.IngredientData;
import Data.UserData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;

public class BaseTest {
    UserData userData = new UserData("holodTest@test.ru", "1234", "holod");
    UserData userDataWithoutPassword = UserData.userWithoutPassword("holodTest@test.ru", "holod");
    UserData userDataWithIncorrectParams = new UserData("incorrectholodTest@test.ru", "incorrect1234", "incorrectholod");
    UserData userDataForUpdate = new UserData("updateholodTest@test.ru", "update1234", "updateholod");
    String accessToken;
    String ingredient;
    boolean shouldDeleteUser = false;
    IngredientData ingredientData;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}