package resources;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class CreateUser {
    private String email;
    private String password;
    private String name;
    public CreateUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public CreateUser() {
    }

    @Step("Отправка POST запроса для Создания пользователя")
    public Response createUserRequest(CreateUser newCreatedUser) {
        return given()
                .header("Content-type", "application/json")
                .body(newCreatedUser)
                .post(Constants.CREATE_USER_ENDPOINT);
    }

}
