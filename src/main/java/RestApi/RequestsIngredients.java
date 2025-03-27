package RestApi;

import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static RestApi.Requests.reqSpec;
import static io.restassured.RestAssured.given;

public class RequestsIngredients {


    private final static String GET_INGREDIENTS = "/api/ingredients";
    @Step("Получение списка курьеров из API")
    public static ArrayList<String> getIngredients() {
        return given()
                .spec(reqSpec())
                .get(GET_INGREDIENTS)
                .path("data._id");
    }
    @Step("Внесение списка ингредиентов в БД")
    public static List<String> getIngredientsPojo(){

        try {
            List<String> strings = getIngredients();
            System.out.println(strings);
            return strings;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
