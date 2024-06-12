package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import resources.*;

import java.util.Random;
@DisplayName("Логин пользователя")
public class LoginUserTest {
    public int random4Numbers () {
        return new Random().nextInt(8999)+1000;
    }

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
        CreateUser newUser = new CreateUser().createUser(email, password, name);

        //логин пользователя, проверка кода и тела ответа
        LoginUser newLogin = new LoginUser().loginUserRequest(email, password)
                .checkStatusCode(Constants.STATUS_CODE_200)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS, true);

        //удаление созданного пользователя
        new DeleteUser().deleteUserRequest(newLogin.getLoginUserAccessToken());
}

    @Test
    @DisplayName("Логин пользователя")
    @Description("Попытка логина с неверным логином и паролем")
    public void loginInvalidUser () {

        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;

        //логин пользователя, проверка кода и тела ответа
        LoginUser newLogin = new LoginUser().loginUserRequest(email, password)
                .checkStatusCode(Constants.STATUS_CODE_401)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS, false);

    }


}
