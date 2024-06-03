package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import resources.*;

import java.util.Random;

public class UpdateUserInfoTest {
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
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных пользователя с авторизацией")
    public void updateUserInfo () {

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

        //обновление информации о пользователе
        email = random4Numbers() + random4Numbers() +  Constants.DEFAULT_EMAIL;
        password = random4Numbers() + Constants.DEFAULT_PASSWORD;
        name = random4Numbers() + Constants.DEFAULT_NAME ;

        UpdateUserInfo newUpdateUserInfo = new UpdateUserInfo(email, password, name);
        Response responseUpdatedInfo = newUpdateUserInfo.updateUserRequest(newUpdateUserInfo, accessToken);

        //проверка статус кода и тела запроса
        newCheckResponse.checkStatusCode(responseUpdatedInfo,Constants.STATUS_CODE_200);
        newCheckResponse.checkBodyMessage(responseUpdatedInfo,Constants.KEY_NAME_SUCCESS,true);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(accessToken);

    }

    @Test
    @DisplayName("Изменение данных пользователя")
    @Description("Изменение данных пользователя без авторизации")
    public void updateInvalidUserInfo () {

        //обновление информации о пользователе
        String email = Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME ;

        UpdateUserInfo newUpdateUserInfo = new UpdateUserInfo(email, password, name);
        Response responseUpdatedInfo = newUpdateUserInfo.updateUserRequest(newUpdateUserInfo);

        //проверка статус кода и тела запроса
        newCheckResponse.checkStatusCode(responseUpdatedInfo,Constants.STATUS_CODE_401);
        newCheckResponse.checkBodyMessage(responseUpdatedInfo,Constants.KEY_NAME_SUCCESS,false);

    }


}
