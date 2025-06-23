package org.example.template_method;

public class Americano extends CoffeeTemplate{

    @Override
    protected void brew() {
        System.out.println("Americano : brew Coffee Beans");
    }

    @Override
    protected void addCondiments() {
        System.out.println("No Condiments");
    }
}
