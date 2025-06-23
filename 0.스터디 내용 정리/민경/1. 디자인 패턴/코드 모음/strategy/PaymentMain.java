package org.example.strategy;

public class PaymentMain {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();

        context.setPayment(new KakaoPayPayment());
        context.payNow(10000);

        context.setPayment(new NaverPayPayment());
        context.payNow(5000);
    }
}
/*
10000 With KakaoPay
5000 With NaverPay
 */