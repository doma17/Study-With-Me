package org.example.template_method;

public class Latte extends CoffeeTemplate{

    @Override
    protected void brew() {
        System.out.println("Latte : Get Espresso");
    }

    @Override
    protected void addCondiments() {
        System.out.println("Add Milk");
    }
}
