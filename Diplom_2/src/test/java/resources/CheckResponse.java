package resources;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class CheckResponse {

    @Step("Проверка статус кода ответа")
    public void checkStatusCode(Response response, int statusCode) {
        response.then()
                .assertThat()
                .statusCode(statusCode);
    }


    @Step("Проверка тела сообщения по ключу и значению")
    public void checkBodyMessage(Response response, String keyName, boolean expectedMessage) {
        response.then()
                .assertThat()
                .body(keyName, equalTo(expectedMessage));
    }
}
