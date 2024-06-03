package resources.getingredientslist;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import resources.*;
import java.util.*;


public class IngredientsList {

    public int random4Numbers () {
        return new Random().nextInt(8999)+1000;
    }
    CheckResponse newCheckResponse = new CheckResponse();
    DeleteUser deleteUser = new DeleteUser();

    @Step("Обновление списка ингредиентов")
    public String[] updateIngredientList () {

        RestAssured.baseURI = Constants.BASE_URL;

        //создание пользователя
        String email = random4Numbers() + Constants.DEFAULT_EMAIL;
        String password = Constants.DEFAULT_PASSWORD;
        String name = Constants.DEFAULT_NAME;

        CreateUser newCreatedUser = new CreateUser(email, password, name);
        newCreatedUser.createUserRequest(newCreatedUser);

        //логин пользователя
        LoginUser newLoginUser = new LoginUser(email, password);
        Response responseLoginUser = newLoginUser.loginUserRequest(newLoginUser);
        newCheckResponse.checkStatusCode(responseLoginUser, Constants.STATUS_CODE_200);
        newCheckResponse.checkBodyMessage(responseLoginUser, Constants.KEY_NAME_SUCCESS, true);

        //получение токена
        String accessToken = newLoginUser.getLoginUserAccessToken(responseLoginUser);

        //отправка запроса GET для получения списка ингредиентов
        GetIngredientsList getList =  new GetIngredientsList();
        GetIngredientsListDeserialization ingredientListDeserialization  =  getList.getIngredientListRequest(accessToken);

        //формирование списка из 2-х ингредиентов
        String[] freshIngredientList = {
                ingredientListDeserialization.getData().get(0).get_id(),
                ingredientListDeserialization.getData().get(1).get_id()
        };

        //удаление созданного пользователя
        deleteUser.deleteUserRequest(accessToken);

        return freshIngredientList;
    }

}
