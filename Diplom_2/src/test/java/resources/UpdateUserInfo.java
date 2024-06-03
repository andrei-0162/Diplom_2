package resources;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UpdateUserInfo {
    private String email;
    private String password;
    private String name;
    public UpdateUserInfo(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Отправка PATCH запроса для Обновления информации о пользователе")
    //запрос с авторизацией
    public Response updateUserRequest(UpdateUserInfo newUpdateInfo, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(newUpdateInfo)
                .patch(Constants.USER_INFO_ENDPOINT);
    }

    @Step("Отправка PATCH запроса для Обновления информации о пользователе")
    //запрос без авторизации (accessToken отсутствует)
    public Response updateUserRequest(UpdateUserInfo newUpdateInfo) {
        return given()
                .header("Content-type", "application/json")
                .body(newUpdateInfo)
                .patch(Constants.USER_INFO_ENDPOINT);
    }

}
