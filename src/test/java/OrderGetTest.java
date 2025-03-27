import BaseTests.BaseOrderTest;
import Model.PojoOrder;
import Model.PojoUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static BaseTests.BaseUserTest.createUser;
import static BaseTests.BaseUserTest.getAuthPj;
import static RestApi.RequestsOrders.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderGetTest extends BaseOrderTest {
    @Test
    @DisplayName("Получение заказа пользователя")
    public void getOrderAuthorized() {

        PojoUser pj = getAuthPj();
        PojoOrder makeOrder = makeOrder();
        for (int i = 0; i < 4; i++) {
            orderCreateAuthorized(makeOrder, pj.getAccessToken());
        }
        Response r = orderGetAuthorized(pj, pj.getAccessToken())
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract().response();
        assertEquals(true, r.jsonPath().get("success"));
        assertTrue(r.jsonPath().get("success"));
        List<Object> total = r.path("orders");
        assertEquals(4, total.size());
    }

    @Test
    @DisplayName("Получение заказа пользователя без авторизации")

    public void getOrderUnauthorized() {
        PojoUser pj = createUser();
        Response r = orderGetUnauthorized(pj)
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .extract().response();
        assertEquals(false, r.jsonPath().get("success"));
        assertEquals("You should be authorised", r.jsonPath().get("message"));
    }
}
