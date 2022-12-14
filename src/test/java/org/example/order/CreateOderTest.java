package org.example.order;

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

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOderTest {
    private User user;
    private Order order;
    private UserClient userClient;
    private OrderClient orderClient;
    private int
            statusCode,
            statusCodeError;
    private boolean
            expected,
            expectedError;
    private String
            errorMessage,
            accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = UserGenerator.getDefault();
        Response response = userClient.createUser(user);
        accessToken = response.then().extract().path("accessToken");
        statusCode = 200;
        statusCodeError = 401;
        expected = true;
        expectedError = false;
        errorMessage = "You should be authorised";
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

    @Test
    @DisplayName("create Order for not authorized user")
    public void createOrderForNotAuthorizedUser() {

    }

    @After
    public void delete() {
        userClient.deleteUser(accessToken);
    }
}

