package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import resources.*;


import java.util.Random;

@DisplayName("Создание пользователя")

public class CreateUserTest {
    public int random4Numbers () {
        return new Random().nextInt(8999)+1000;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Создание уникального пользователя")
    public void createUser () {
        String email = random4Numbers() + Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newUser = new CreateUser()
                .createUser(email, password, name)
                .checkStatusCode(Constants.STATUS_CODE_200)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS, true);;

        //удаление созданного пользователя
        new DeleteUser().deleteUserRequest(email, password);
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Попытка создания пользователя, который уже зарегистрирован")
    public void createUserTwin () {
        String email = random4Numbers() + Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newUser = new CreateUser()
                .createUser(email, password, name)
                .createUser(email, password, name)
                .checkStatusCode(Constants.STATUS_CODE_403)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS, false);

        //удаление созданного пользователя
        new DeleteUser().deleteUserRequest(email, password);

    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Попытка создания пользователя без заполнения одного из обязательных полей")
    public void createInvalidUser() {

        // без email
        String email = null;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;
        new CreateUser()
                .createUser(email, password, name)
                .checkStatusCode(Constants.STATUS_CODE_403)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS, false);


        // без password
        email = random4Numbers() + Constants.DEFAULT_EMAIL;
        password = null;
        new CreateUser()
                .createUser(email, password, name)
                .checkStatusCode(Constants.STATUS_CODE_403)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS, false);


        //без name
        email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        password = Constants.DEFAULT_PASSWORD;
        name = null;
        new CreateUser()
                .createUser(email, password, name)
                .checkStatusCode(Constants.STATUS_CODE_403)
                .checkBodyMessage(Constants.KEY_NAME_SUCCESS, false);

    }

}
