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
    ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
    ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
    ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
    ingredients.add(jsonResponse.get(faker.number().numberBetween(0, 14)));
    return ingredients;
};

    @Step("get order valid date")
    public static Order getDefault(List<String> jsonResponse){
        return new Order(getIngredients( jsonResponse));
    }

}
