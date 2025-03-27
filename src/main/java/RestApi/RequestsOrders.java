package RestApi;

import Model.PojoOrder;
import Model.PojoUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestsOrders extends Requests {
    private final static String CREATE_ORDER = "/api/orders";
    private final static String GET_ORDERS_ALL = "/api/orders/all";
    private final static String GET_ORDER_USER = "/api/orders";

    @Step("Создание заказа от авторизированного пользователя")
    public static Response orderCreateAuthorized(PojoOrder pj, String accessToken) {
        ObjectMapper mapper = new ObjectMapper();
        return given()
                .spec(reqSpec(pj))
                .header("Authorization", accessToken)
                .post(CREATE_ORDER);
    }
    @Step("Создание заказа от неавторизированного пользователя")
    public static Response orderCreateUnauthorized(PojoOrder pj)
    {
        return given()
                .spec(reqSpec(pj))
                .post(CREATE_ORDER);
    }
    @Step("Получение заказа от авторизированного пользователя")
    public static Response orderGetAuthorized(PojoUser user, String accessToken)
    {
        return given()
                .spec(reqSpec(user))
                .header("Authorization", accessToken)
                .get(GET_ORDER_USER);
    }
    @Step("Попытка получения заказа от неавторизированного пользователя")
    public static Response orderGetUnauthorized(PojoUser user)
    {
        return given()
                .spec(reqSpec(user))
                .get(GET_ORDER_USER);
    }
}
