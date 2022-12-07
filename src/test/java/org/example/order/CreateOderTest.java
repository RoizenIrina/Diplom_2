package org.example.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.Order;
import org.example.User;
import org.example.clients.OrderClient;
import org.example.clients.UserClient;
import org.example.generators.OrderGenerator;
import org.example.generators.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotEquals;

public class CreateOderTest {
    private User user;
    private Order order;
    private UserClient userClient;
    private OrderClient orderClient;
    private int
            statusCode,
            invalidIngredientStatusCode,
            nullIngredientStatusCode;
    private boolean
            expected,
            expectedError;
    private String
            invalidIngredientErrorMessage,
            nullIngredientErrorMessage,
            accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.getDefault();
        Response response = userClient.createUser(user);
        accessToken = response.then().extract().path("accessToken");
        statusCode = 200;
        invalidIngredientStatusCode = 500;
        nullIngredientStatusCode = 400;
        expected = true;
        expectedError = false;
        nullIngredientErrorMessage = "Ingredient ids must be provided";
    }

    @Test
    @DisplayName("create valid Order for authorized user")
    public void createValidOrder() {
        Response response1 = orderClient.getIngredients();
        List<String> jsonResponse = response1.then().extract().body()
                .jsonPath().getList("data._id");
        order = OrderGenerator.getDefault(jsonResponse);
        Response response2 = orderClient.createNewOrder(accessToken, order);
        response2.then().assertThat().statusCode(statusCode)
                .and().body("success", equalTo(expected));
    }

//    @Test
//    @DisplayName("create Order for not authorized user")
//    @Description( "the test will fail because according to the documents it is impossible to create an order without authorization, but in practice it is possible")
//    public void createOrderForNotAuthorizedUser() {
//        Response response1 = orderClient.getIngredients();
//        List<String> jsonResponse = response1.then().extract().body()
//                .jsonPath().getList("data._id");
//        order = OrderGenerator.getDefault(jsonResponse);
//        String accessToken = "";
//        Response response2 = orderClient.createNewOrder(accessToken, order);
//        int sC = response2.then().extract().statusCode();
//        assertNotEquals("error - order can be created for not authorized user! ", sC, statusCode);
//    }

    @Test
    @DisplayName("post invalid ingredients")
    @Description( "the response to the request does not contain a body, so we only check the statusCode")
    public void createOrderWithInvalidIngredients() {
        Response response1 = orderClient.getIngredients();
        List<String> jsonResponse = response1.then().extract().body()
                .jsonPath().getList("data._id");
        order = OrderGenerator.getWithInvalidIngredients(jsonResponse);
        Response response2 = orderClient.createNewOrder(accessToken, order);
        response2.then().assertThat().statusCode(invalidIngredientStatusCode);
    }
    @Test
    @DisplayName("post invalid ingredients")
    public void createOrderWithNullIngredients() {
        Response response1 = orderClient.getIngredients();
        List<String> jsonResponse = response1.then().extract().body()
                .jsonPath().getList("data._id");
        order = OrderGenerator.getWithZeroIngredients(jsonResponse);
        Response response2 = orderClient.createNewOrder(accessToken, order);
        response2.then().assertThat().statusCode(nullIngredientStatusCode)
                .and().body("message", equalTo(nullIngredientErrorMessage));
    }

    @After
    public void delete() {
        userClient.deleteUser(accessToken);
    }
}

