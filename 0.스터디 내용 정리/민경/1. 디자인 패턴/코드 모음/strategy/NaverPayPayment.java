package org.example.strategy;

public class NaverPayPayment implements PaymentStrategy{
    @Override
    public void pay(int amount) {
        System.out.println(amount + " With NaverPay");
    }
}
