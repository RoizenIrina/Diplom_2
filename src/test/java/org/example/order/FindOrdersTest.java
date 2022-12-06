package org.example.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.Credentials;
import org.example.User;
import org.example.clients.OrderClient;
import org.example.clients.UserClient;
import org.example.generators.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class FindOrdersTest {
    private User user;

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
    @DisplayName("find order to authorized user")
    public void findOrderToAuthorizedUser() {
        Response response1 = userClient.loginUser(Credentials.from(user));
        Response response2 = orderClient.findOrder(accessToken);
        response2.then().statusCode(statusCode).and().body("success", equalTo(expected));
    }

    @Test
    @DisplayName("find order to not authorized user")
    public void findOrderToNotAuthorizedUser() {
        String accessToken = "";
        Response response = orderClient.findOrder(accessToken);
        response.then().statusCode(statusCodeError).and().body("message", equalTo(errorMessage));
    }


    @After
    public void delete() {
        userClient.deleteUser(accessToken);
    }
}
