package BaseTests;

import Model.PojoOrder;
import jdk.jfr.Description;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static Model.PojoIngredients.*;


public class BaseOrderTest extends BaseTest {
    @Before
    @Description("Начало базового теста заказа")
    public void Start() {
    }

    protected PojoOrder makeOrder() {
        setData();
        List<String> orderIngs = new ArrayList<>();
        for (String i : ingredients) {
            if (ingredients.indexOf(i) % 2 == 0)
                orderIngs.add(i);
        }
        return new PojoOrder(orderIngs);
    }
}
