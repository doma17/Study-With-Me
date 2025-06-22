package org.example.strategy;

public class KakaoPayPayment implements PaymentStrategy{
    @Override
    public void pay(int amount) {
        System.out.println(amount + " With KakaoPay");
    }
}
