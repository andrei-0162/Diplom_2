package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import resources.*;


import java.util.Random;


public class CreateUserTest {
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
    @DisplayName("Создание пользователя")
    @Description("Создание уникального пользователя")
    public void createUser () {
        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newCreatedUser = new CreateUser(email, password, name);
        Response responseCreateUser = newCreatedUser.createUserRequest(newCreatedUser);
        newCheckResponse.checkStatusCode(responseCreateUser, Constants.STATUS_CODE_200);
        newCheckResponse.checkBodyMessage(responseCreateUser, Constants.KEY_NAME_SUCCESS, true);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(email, password);
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Попытка создания пользователя, который уже зарегистрирован")
    public void createUserTwin () {
        String email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newCreatedUser = new CreateUser(email, password, name);
        newCreatedUser.createUserRequest(newCreatedUser);
        Response responseCreateUser = newCreatedUser.createUserRequest(newCreatedUser);
        newCheckResponse.checkStatusCode(responseCreateUser, Constants.STATUS_CODE_403);
        newCheckResponse.checkBodyMessage(responseCreateUser, Constants.KEY_NAME_SUCCESS, false);

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(email, password);

    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Попытка создания пользователя без заполнения одного из обязательных полей")
    public void createInvalidUser() {

        // без email
        String email = null;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newCreatedUser = new CreateUser(email, password, name);
        Response responseCreateUser = newCreatedUser.createUserRequest(newCreatedUser);
        newCheckResponse.checkStatusCode(responseCreateUser, Constants.STATUS_CODE_403);
        newCheckResponse.checkBodyMessage(responseCreateUser, Constants.KEY_NAME_SUCCESS, false);

        // без password
        email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        password = null;

        newCreatedUser = new CreateUser(email, password, name);
        responseCreateUser = newCreatedUser.createUserRequest(newCreatedUser);
        newCheckResponse.checkStatusCode(responseCreateUser, Constants.STATUS_CODE_403);
        newCheckResponse.checkBodyMessage(responseCreateUser, Constants.KEY_NAME_SUCCESS, false);

        //без name
        email = random4Numbers()+ Constants.DEFAULT_EMAIL;
        password = Constants.DEFAULT_PASSWORD;
        name = null;

        newCreatedUser = new CreateUser(email, password, name);
        responseCreateUser = newCreatedUser.createUserRequest(newCreatedUser);
        newCheckResponse.checkStatusCode(responseCreateUser, Constants.STATUS_CODE_403);
        newCheckResponse.checkBodyMessage(responseCreateUser, Constants.KEY_NAME_SUCCESS, false);

    }

}
