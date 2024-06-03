package resources;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetOrders {

    @Step("Отправка GET запроса для получения списка заказов")
    //запрос с авторизацией
    public Response getOrderRequest(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .get(Constants.GET_ORDER_ENDPOINT);
    }

    @Step("Отправка GET запроса для получения списка заказов")
    //запрос без авторизации (accessToken отсутствует)
    public Response getOrderRequest() {
        return given()
                .get(Constants.GET_ORDER_ENDPOINT);
    }
}
