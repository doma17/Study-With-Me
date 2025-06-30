package org.example.observer;

public class NewsReader implements Observer{
    private String name;

    public NewsReader(String name){
        this.name = name;
    }
    @Override
    public void update(String news) {
        System.out.println(name + " received news : " + news);
    }
}
