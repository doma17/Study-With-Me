package org.example.template_method;

public class CoffeeMain {
    public static void main(String[] args) {
        CoffeeTemplate americano = new Americano();
        americano.makeCoffee();
        System.out.println();

        CoffeeTemplate latte = new Latte();
        latte.makeCoffee();
    }
}

/*
Boil Water
Americano : brew Coffee Beans
Pour In Cup
No Condiments

Boil Water
Latte : Get Espresso
Pour In Cup
Add Milk
 */