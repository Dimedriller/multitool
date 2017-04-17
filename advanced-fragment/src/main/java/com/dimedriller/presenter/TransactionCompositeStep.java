package com.dimedriller.presenter;

class TransactionCompositeStep extends TransactionStep {
    private final TransactionStep[] mSteps;

    TransactionCompositeStep(TransactionStep[] steps) {
        mSteps = steps;
    }

    void actDirect(PresenterManager manager) {
        int countStep = mSteps.length;
        //noinspection ForLoopReplaceableByForEach
        for(int indexStep = 0; indexStep < countStep; indexStep++) {
            TransactionStep step = mSteps[indexStep];
            step.actDirect(manager);
        }
    }

    void actReverse(PresenterManager manager) {
        for(int indexStep = mSteps.length - 1; indexStep >=0; indexStep--) {
            TransactionStep step = mSteps[indexStep];
            step.actReverse(manager);
        }
    }
}
