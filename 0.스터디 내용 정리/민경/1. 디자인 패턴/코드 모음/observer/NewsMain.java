package org.example.observer;

public class NewsMain {
    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency();
        Observer reader1 = new NewsReader("guest1");
        Observer reader2 = new NewsReader("guest2");

        agency.registerObserver(reader1);
        agency.registerObserver(reader2);

        agency.setNews("Big News !");
        agency.removeObserver(reader2);
        System.out.println("==================");
        System.out.println("Unsubscribe reader2");
        System.out.println("==================");
        agency.setNews("Second Big News!");
    }
}

/*
guest1 received news : Big News !
guest2 received news : Big News !
==================
Unsubscribe reader2
==================
guest1 received news : Second Big News!
 */