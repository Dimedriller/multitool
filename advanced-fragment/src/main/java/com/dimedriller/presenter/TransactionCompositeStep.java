package com.dimedriller.presenter;

class TransactionCompositeStep extends TransactionStep {
    private final TransactionStep[] mSteps;

    TransactionCompositeStep(TransactionStep[] steps) {
        mSteps = steps;
    }

    void actDirect(PresenterManager manager) {
        for(TransactionStep step : mSteps)
            step.actDirect(manager);
    }

    void actReverse(PresenterManager manager) {
        for(TransactionStep step : mSteps)
            step.actReverse(manager);
    }
}
