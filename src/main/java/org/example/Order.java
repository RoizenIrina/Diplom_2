package org.example;

import java.util.List;

public class Order {

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> ingredients;


    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

public Order(){}




}

//public static void main(String[] args) {
//ArrayList<String> allIngredientsId = new ArrayList<>();
//     expenses.add(order.getId);
//     int size = expenses.size();
//     Random random = new Random;
//    int randomNumberOfId = random.nextInt(size);
//    for (int i = 1; i <= randomNumberOfId; i = i + 1) {
//    int randomId = expenses.get(randomNumberOfId);
//     ArrayList<String> orderIngredientsId = new ArrayList<>();
////     expenses.add(randomId);
//        }



