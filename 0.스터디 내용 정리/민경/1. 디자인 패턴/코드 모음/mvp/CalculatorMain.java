package org.example.mvp;

public class CalculatorMain {
    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel();
        CalculatorView view = new CalculatorConsoleView();
        CalculatorPresenter presenter = new CalculatorPresenter(model, view);

        presenter.onAddButtonClicked(10,20);
    }
}
