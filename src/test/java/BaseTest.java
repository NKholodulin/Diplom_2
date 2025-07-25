import Data.UserData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    UserData userData = new UserData("holodTest@test.ru", "1234", "holod");
    UserData userDataWithoutPassword = UserData.userWithoutPassword("holodTest@test.ru", "holod");
    UserData userDataWithIncorrectParams = new UserData("incorrectholodTest@test.ru", "incorrect1234", "incorrectholod");
    String accessToken;
    boolean shouldDeleteUser = false;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}