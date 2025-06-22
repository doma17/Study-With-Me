package org.example.template_method;

//Abstract Class : Template Method 제공
abstract class CoffeeTemplate {

    //Template Method : 고정된 알고리즘 구조
    public final void makeCoffee(){
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    private void pourInCup() {
        System.out.println("Pour In Cup");
    }

    private void boilWater() {
        System.out.println("Boil Water");
    }

    // Abstract Method : 하위 클래스가 구현
    protected abstract void addCondiments();

    protected abstract void brew();
}
