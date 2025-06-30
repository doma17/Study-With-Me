package org.example.mvp;

public class CalculatorConsoleView implements CalculatorView{
    @Override
    public void showResult(int result) {
        System.out.println("Result : " + result);
    }
}
