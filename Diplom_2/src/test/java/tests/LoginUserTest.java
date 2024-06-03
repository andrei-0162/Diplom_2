package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import resources.*;

import java.util.Random;

public class LoginUserTest {
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
    @DisplayName("Логин пользователя")
    @Description("Логин под существующим пользователем")
    public void loginUser () {

        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        //создание пользователя
        CreateUser newCreatedUser = new CreateUser(email, password, name);
        newCreatedUser.createUserRequest(newCreatedUser);

        //логин пользователя
        LoginUser newLoginUser = new LoginUser(email, password);
        Response responseLoginUser = newLoginUser.loginUserRequest(newLoginUser);
        newCheckResponse.checkStatusCode(responseLoginUser, Constants.STATUS_CODE_200);
        newCheckResponse.checkBodyMessage(responseLoginUser, Constants.KEY_NAME_SUCCESS, true);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(responseLoginUser);
}

    @Test
    @DisplayName("Логин пользователя")
    @Description("Попытка логина с неверным логином и паролем")
    public void loginInvalidUser () {

        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;

        //логин пользователя
        LoginUser newLoginUser = new LoginUser(email, password);
        Response responseLoginUser = newLoginUser.loginUserRequest(newLoginUser);
        newCheckResponse.checkStatusCode(responseLoginUser, Constants.STATUS_CODE_401);
        newCheckResponse.checkBodyMessage(responseLoginUser, Constants.KEY_NAME_SUCCESS, false);

    }


}
