package BaseTests;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.Before;

public class BaseTest {
    @Before
    @Description("Старт")

    public void setup() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}
