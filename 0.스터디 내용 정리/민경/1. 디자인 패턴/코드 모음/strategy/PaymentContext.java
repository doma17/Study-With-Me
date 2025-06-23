package org.example.strategy;

public class PaymentContext {
    private PaymentStrategy strategy;

    public void setPayment(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void payNow(int amount){
        if (strategy==null){
            System.out.println("No PaymentStrategy");
            return;
        }
        strategy.pay(amount);
    }
}
