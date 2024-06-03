package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import resources.*;

import java.util.Random;

public class CreateOrderTest {

    public int random4Numbers () {
        return new Random().nextInt(8999)+1000;
    }
    CheckResponse newCheckResponse = new CheckResponse();
    DeleteUser deleteUser = new DeleteUser();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа c авторизацией и наличием ингредиентов")
    public void createOrder () {

        //создание пользователя
        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newCreatedUser = new CreateUser(email, password, name);
        newCreatedUser.createUserRequest(newCreatedUser);

        //логин пользователя
        LoginUser newLoginUser = new LoginUser(email, password);
        Response responseLoginUser = newLoginUser.loginUserRequest(newLoginUser);

        //получение токена
        String accessToken = newLoginUser.getLoginUserAccessToken(responseLoginUser);

        //создание заказа
        CreateOrder newOrder = new CreateOrder(Constants.INGREDIENT_LIST);
        Response responseCreateOrder = newOrder.createOrderRequest(newOrder,accessToken);

        //проверка статус кода и тела запроса
        newCheckResponse.checkStatusCode(responseCreateOrder,Constants.STATUS_CODE_200);
        newCheckResponse.checkBodyMessage(responseCreateOrder,Constants.KEY_NAME_SUCCESS,true);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(accessToken);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Попытка создания заказа c авторизацией и без ингредиентов")
    public void createOrderWithoutIngredients () {

        //создание пользователя
        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newCreatedUser = new CreateUser(email, password, name);
        newCreatedUser.createUserRequest(newCreatedUser);

        //логин пользователя
        LoginUser newLoginUser = new LoginUser(email, password);
        Response responseLoginUser = newLoginUser.loginUserRequest(newLoginUser);

        //получение токена
        String accessToken = newLoginUser.getLoginUserAccessToken(responseLoginUser);

        //создание заказа
        CreateOrder newOrder = new CreateOrder(null);
        Response responseCreateOrder = newOrder.createOrderRequest(newOrder,accessToken);

        //проверка статус кода и тела запроса
        newCheckResponse.checkStatusCode(responseCreateOrder,Constants.STATUS_CODE_400);
        newCheckResponse.checkBodyMessage(responseCreateOrder,Constants.KEY_NAME_SUCCESS,false);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(accessToken);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Попытка создания заказа c авторизацией и с неверным хешем ингредиентов")
    public void createOrderWithInvalidIngredientList () {

        //создание пользователя
        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newCreatedUser = new CreateUser(email, password, name);
        newCreatedUser.createUserRequest(newCreatedUser);

        //логин пользователя
        LoginUser newLoginUser = new LoginUser(email, password);
        Response responseLoginUser = newLoginUser.loginUserRequest(newLoginUser);

        //получение токена
        String accessToken = newLoginUser.getLoginUserAccessToken(responseLoginUser);

        //создание заказа
        CreateOrder newOrder = new CreateOrder(Constants.INVALID_INGREDIENT_LIST);
        Response responseCreateOrder = newOrder.createOrderRequest(newOrder,accessToken);

        //проверка статус кода
        newCheckResponse.checkStatusCode(responseCreateOrder,Constants.STATUS_CODE_500);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(accessToken);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа без авторизации")
    public void createOrderWithoutAuth () {

        //создание заказа
        CreateOrder newOrder = new CreateOrder(Constants.INGREDIENT_LIST);
        Response responseCreateOrder = newOrder.createOrderRequest(newOrder);

        //проверка статус кода
        newCheckResponse.checkStatusCode(responseCreateOrder,Constants.STATUS_CODE_200);
        newCheckResponse.checkBodyMessage(responseCreateOrder,Constants.KEY_NAME_SUCCESS,true);
    }

}
