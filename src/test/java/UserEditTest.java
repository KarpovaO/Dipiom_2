import BaseTests.BaseUserTest;
import Model.PojoUser;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static RestApi.RequestsUsers.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class UserEditTest extends BaseUserTest {
    @Test
    @DisplayName("Редактирование данных пользователя после авторизации")
    public void editUserAuthorized() {
        PojoUser pj = getAuthPj();
        userChangeData(pj, pj.getAccessToken())
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Редактирование данных пользователя без авторизации")

    public void editUserUnauthorized() {
        PojoUser pj = createUser();
        userChangeDataUnauthorized(pj)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);
    }
}
