package com.dimedriller.presenter;

class JournalPushStep extends JournalStep {
    private final String mStackName;
    private final Transaction mTransaction;

    JournalPushStep(String stackName, Transaction transaction) {
        mStackName = stackName;
        mTransaction = transaction;
    }

    @Override
    void act(PresenterManager manager) {
        manager.pushTransaction(mStackName, mTransaction);
    }
}
