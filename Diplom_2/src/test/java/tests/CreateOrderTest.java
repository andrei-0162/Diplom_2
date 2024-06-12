package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import resources.*;

import java.util.Random;

@DisplayName("Создание заказа")
public class CreateOrderTest {

    public int random4Numbers () {
        return new Random().nextInt(8999)+1000;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа c авторизацией и наличием ингредиентов")
    public void createOrder () {

        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        //создание пользователя
        new CreateUser().createUser(email, password, name);

        //логин пользователя
        LoginUser newLogin = new LoginUser().loginUserRequest(email, password);

        //получение токена
        String accessToken = newLogin.getLoginUserAccessToken();

        //создание заказа, проверка статус кода и тела запроса
        new CreateOrder(Constants.INGREDIENT_LIST)
                .createOrderRequest(accessToken)
                .checkStatusCode(Constants.STATUS_CODE_200)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS,true);

        //удаление созданного пользователя
        new DeleteUser().deleteUserRequest(accessToken);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Попытка создания заказа c авторизацией и без ингредиентов")
    public void createOrderWithoutIngredients () {

        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        //создание пользователя
        new CreateUser().createUser(email, password, name);

        //логин пользователя
        LoginUser newLogin = new LoginUser().loginUserRequest(email, password);

        //получение токена
        String accessToken = newLogin.getLoginUserAccessToken();

        //создание заказа, проверка статус кода и тела запроса
        new CreateOrder()
                .createOrderRequest(accessToken)
                .checkStatusCode(Constants.STATUS_CODE_400)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS,false);

        //удаление созданного пользователя
        new DeleteUser().deleteUserRequest(accessToken);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Попытка создания заказа c авторизацией и с неверным хешем ингредиентов")
    public void createOrderWithInvalidIngredientList () {

        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        //создание пользователя
        new CreateUser().createUser(email, password, name);

        //логин пользователя
        LoginUser newLogin = new LoginUser().loginUserRequest(email, password);

        //получение токена
        String accessToken = newLogin.getLoginUserAccessToken();

        //создание заказа, проверка статус кода
        new CreateOrder(Constants.INVALID_INGREDIENT_LIST)
                .createOrderRequest(accessToken)
                .checkStatusCode(Constants.STATUS_CODE_500);

        //удаление созданного пользователя
        new DeleteUser().deleteUserRequest(accessToken);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без авторизации")
    public void createOrderWithoutAuth () {

        //создание заказа, проверка статус кода и тела запроса
        new CreateOrder(Constants.INGREDIENT_LIST)
                .createOrderRequest()
                .checkStatusCode(Constants.STATUS_CODE_200)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS,true);
    }

}
