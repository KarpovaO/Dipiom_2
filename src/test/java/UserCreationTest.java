import BaseTests.BaseUserTest;
import Model.PojoUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Locale;

import static RestApi.RequestsUsers.userSignUp;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class UserCreationTest extends BaseUserTest {
    @Test
    @DisplayName("Тест создать пользователя")
    public void createUserTest() {
        pj = createUser();
        Response r = userSignUp(pj)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract().response();
        assertEquals(pj.getEmail().toLowerCase(Locale.ROOT), r.path("user.email"));
        assertEquals(pj.getName(), r.path("user.name"));
        pj.setAccessToken(r.path("accessToken"));

    }

    @Test
    @DisplayName("Тест создать пользователя дважды")
    public void createUserDouble() {
        PojoUser pj = createUser();
        userSignUp(pj);
        Response r = userSignUp(pj)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract().response();
        assertEquals("User already exists", r.jsonPath().get("message"));
        assertEquals(false, r.jsonPath().get("success"));
    }

    @Test
    @DisplayName("Тест создать пользователя без имени")
    public void createUserWithNoName() {
        Response r = userSignUp(createUser("noName"))
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN)
                .extract().response();
        assertEquals("Email, password and name are required fields", r.jsonPath().get("message"));
        assertEquals(false, r.jsonPath().get("success"));
    }
}
