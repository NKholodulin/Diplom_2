import Data.IngredientData;
import Data.UserData;
import io.restassured.RestAssured;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    Faker faker = new Faker();
    String name = faker.name().firstName();
    String email = name + "@test.ru";
    String password = faker.regexify("[A-Za-z0-9]{10}");

    UserData userData = new UserData(email, password, name);
    UserData userDataWithoutPassword = UserData.userWithoutPassword(email, name);
    UserData userDataWithIncorrectParams = new UserData("incorrect" + email, "incorrect" + password, "incorrect" + name);
    UserData userDataForUpdate = new UserData("update" + email, "update" + password, "update" + name);
    String accessToken;
    String ingredient;
    boolean shouldDeleteUser = false;
    IngredientData ingredientData;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}