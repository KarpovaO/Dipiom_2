import BaseTests.BaseUserTest;
import Model.PojoUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Locale;

import static RestApi.RequestsUsers.userLogIn;
import static RestApi.RequestsUsers.userSignUp;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class UserLoginTest extends BaseUserTest {
    @Test
    @DisplayName("Авторизация пользователя")
    public void login() {
        PojoUser pj = createUser();
        userSignUp(pj);
        Response r = userLogIn(pj)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract().response();

        assertEquals(pj.getEmail().toLowerCase(Locale.ROOT), r.path("user.email"));
        assertEquals(pj.getName(), r.path("user.name"));
    }

    @Test
    @DisplayName("Авторизация пользователя с невалидными данными")
    public void loginFalseData() {
        PojoUser pj = createUser();
        Response r = userLogIn(pj)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract().response();
        assertEquals("email or password are incorrect", r.jsonPath().get("message"));
    }
}
