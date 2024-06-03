package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import resources.*;

import java.util.Random;

public class GetOrderTest {
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
    @DisplayName("Получение заказов конкретного пользователя")
    @Description("Получение заказов авторизованного пользователя")
    public void getOrder () {

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
        newOrder.createOrderRequest(newOrder,accessToken);

        //получение списка заказов пользователя
        GetOrders newOrderList = new GetOrders();
        Response responseOrderList = newOrderList.getOrderRequest(accessToken);

        //проверка статус кода и тела запроса
        newCheckResponse.checkStatusCode(responseOrderList,Constants.STATUS_CODE_200);
        newCheckResponse.checkBodyMessage(responseOrderList,Constants.KEY_NAME_SUCCESS,true);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(accessToken);

    }
    @Test
    @DisplayName("Получение заказов конкретного пользователя")
    @Description("Получение заказов для неавторизованного пользователя")
    public void getOrderWithoutAuth () {

        //создание заказа
        CreateOrder newOrder = new CreateOrder(Constants.INGREDIENT_LIST);
        newOrder.createOrderRequest(newOrder);

        //получение списка заказов пользователя
        GetOrders newOrderList = new GetOrders();
        Response responseOrderList = newOrderList.getOrderRequest();

        //проверка статус кода и тела запроса
        newCheckResponse.checkStatusCode(responseOrderList,Constants.STATUS_CODE_401);
        newCheckResponse.checkBodyMessage(responseOrderList,Constants.KEY_NAME_SUCCESS,false);

    }


}
