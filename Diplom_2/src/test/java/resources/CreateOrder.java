package resources;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateOrder {
    private String[] ingredients;

    public CreateOrder() {
    }
    public CreateOrder(String[] ingredients) {
        this.ingredients = ingredients;
    }

    @Step("Отправка POST запроса для Создания заказ")
    public Response createOrderRequest(CreateOrder newCreateOrder, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(newCreateOrder)
                .post(Constants.CREATE_ORDER_ENDPOINT);
    }

    @Step("Отправка POST запроса для Создания заказ")
    //запрос без авторизации (accessToken отсутствует)
    public Response createOrderRequest(CreateOrder newCreateOrder) {
        return given()
                .header("Content-type", "application/json")
                .body(newCreateOrder)
                .post(Constants.CREATE_ORDER_ENDPOINT);
    }


}
