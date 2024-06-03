package resources;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class LoginUser {

    private String email;
    private String password;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUser() {
    }

    @Step("Отправка POST запроса для Авторизации пользователя")
    public Response loginUserRequest(LoginUser newLoginUser) {
        return given()
                .header("Content-type", "application/json")
                .body(newLoginUser)
                .post(Constants.LOGIN_USER_ENDPOINT);
    }

    @Step("Получение AccessToken залогиненного пользователя")
    public String getLoginUserAccessToken(Response responseLoginUser) {
        LoginUserDeserialization loggedUser = responseLoginUser.as(LoginUserDeserialization.class);
        return loggedUser.getAccessToken();
    }

}
