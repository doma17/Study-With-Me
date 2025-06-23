package org.example.mvp;

public class CalculatorPresenter {
    private final CalculatorModel model;
    private final CalculatorView view;

    public CalculatorPresenter(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
    }

    public void onAddButtonClicked(int a, int b){
        int result = model.add(a,b);
        view.showResult(result);
    }
}
