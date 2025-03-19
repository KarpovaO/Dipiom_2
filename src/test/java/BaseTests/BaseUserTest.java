package BaseTests;

import Model.PojoUser;
import io.restassured.response.Response;
import org.junit.After;

import java.time.Instant;

import static RestApi.RequestsUsers.*;

public class BaseUserTest extends BaseTest {
    public static PojoUser createUser() {
        String name = "TestUser" + Instant.now().toString();
        String email = name + "@yandex.ru";
        String password = "TestP$aSSword";
        return new PojoUser(email, password, name);
    }

    public static PojoUser createUser(String param) {
        PojoUser user = createUser();
        switch (param) {
            case "noName":
                user.setName("");
                break;
            case "noEmail":
                user.setEmail("");
                break;
            case "noPassword":
                user.setPassword("");
                break;
        }
        return user;
    }

    public static PojoUser getAuthPj() {
        PojoUser pj = createUser();
        Response r = userSignUp(pj).then().extract().response();
        userLogIn(pj);
        String accessToken = r.jsonPath().getString("accessToken");
        String refreshToken = r.jsonPath().getString("refreshToken");

        pj = createUser();
        pj.setAccessToken(accessToken);
        pj.setRefreshToken(refreshToken);
        pj.setAuthorization(accessToken);

        return pj;
    }

    public PojoUser pj;

    @After
    public void deleteUser() {
        if (pj != null) {
            userDelete(pj);
        }
    }

}
