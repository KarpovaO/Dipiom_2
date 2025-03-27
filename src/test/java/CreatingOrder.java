import BaseTests.BaseOrderTest;
import Model.PojoOrder;
import Model.PojoUser;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static BaseTests.BaseUserTest.getAuthPj;
import static RestApi.RequestsOrders.orderCreateAuthorized;
import static RestApi.RequestsOrders.orderCreateUnauthorized;
import static junit.framework.TestCase.assertFalse;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreatingOrder extends BaseOrderTest {
    PojoOrder order;

    @Before
    @Step("Cоздание заказа")
    public void Start() {
        order = makeOrder();
    }

    @Test
    @DisplayName("Тест Создание заказа пользователя")
    public void createOrderAuthorized() {
        PojoUser pj = getAuthPj();
        Response r = orderCreateAuthorized(order, pj.getAccessToken())
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract().response();
        assertFalse(r.jsonPath().getString("name").isEmpty());
        assertTrue(r.jsonPath().getBoolean("success"));
        assertTrue(r.path("order.number") instanceof Integer && (Integer) r.path("order.number") > 0);
    }

    @Test
    @DisplayName("Тест Создание заказа без пользователя")
    public void createOrderUnauthorizedWithIngredients() {
        orderCreateUnauthorized(order)
                .then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Тест Создание заказа с Wrong Hash")
    public void createOrderWithBadHash() {
        order.getIngredients().add("NotAHash");
        orderCreateUnauthorized(order)
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Тест Создание заказа без Ingredients")
    public void createOrderWithoutIngredients() {
        order.getIngredients().clear();
        Response r = orderCreateUnauthorized(order)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract().response();
        assertEquals(false, r.jsonPath().get("success"));
        assertEquals("Ingredient ids must be provided", r.jsonPath().get("message"));
    }
}
