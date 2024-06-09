package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import resources.*;

import java.util.Random;
@DisplayName("Изменение данных пользователя")
public class UpdateUserInfoTest {
    public int random4Numbers () {
        return new Random().nextInt(8999)+1000;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }


    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных пользователя с авторизацией")
    public void updateUserInfo () {

        //создание пользователя
        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        //создание пользователя
        new CreateUser().createUser(email, password, name);

        //логин пользователя
        LoginUser newLogin = new LoginUser().loginUserRequest(email, password);
        //получение токена
        String accessToken = newLogin.getLoginUserAccessToken();

        email = random4Numbers() + random4Numbers() +  Constants.DEFAULT_EMAIL;
        password = random4Numbers() + Constants.DEFAULT_PASSWORD;
        name = random4Numbers() + Constants.DEFAULT_NAME;

        //обновление информации о пользователе, проверка статус кода и тела запроса
        UpdateUserInfo newUpdate = new UpdateUserInfo()
                .updateUserRequest(email, password, name, accessToken)
                .checkStatusCode(Constants.STATUS_CODE_200)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS,true);

        //удаление созданного пользователя
        new DeleteUser().deleteUserRequest(accessToken);

    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных пользователя без авторизации")
    public void updateInvalidUserInfo () {

        //обновление информации о пользователе, проверка статус кода и тела запроса
        String email = Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME ;

        UpdateUserInfo newUpdate = new UpdateUserInfo().updateUserRequest(email, password, name)
                .checkStatusCode(Constants.STATUS_CODE_401)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS,false);
    }


}
