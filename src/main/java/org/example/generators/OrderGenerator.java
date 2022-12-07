package org.example.generators;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.example.Order;

import java.util.ArrayList;
import java.util.List;


public class OrderGenerator {
private static Faker faker = new Faker();

public static List<String> ingredients = new ArrayList<>();

public  static List<String> getIngredients(List<String> jsonResponse) {
    int randomInt = faker.number().numberBetween(0, jsonResponse.size());

    for (int i = 0; i<randomInt; i++){
        ingredients.add(jsonResponse.get(faker.number().numberBetween(0, jsonResponse.size())));
    }
    return ingredients;
};

    @Step("get order valid date")
    public static Order getDefault(List<String> jsonResponse){
        return new Order(getIngredients( jsonResponse));
    }
    @Step("get order without ingredients")
    public static Order getWithZeroIngredients(List<String> jsonResponse){
        jsonResponse.clear();
        return new Order(getIngredients(jsonResponse));
    }
    @Step("get order invalid date")
    public static Order getWithInvalidIngredients(List<String> jsonResponse){
        jsonResponse.clear();
        ingredients.add("1");
        return new Order(getIngredients(jsonResponse));
    }
}

