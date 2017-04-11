package com.dimedriller.presenter;

class JournalPopStep extends JournalStep {
    private final String mStackName;

    JournalPopStep(String stackName) {
        mStackName = stackName;
    }

    @Override
    void act(PresenterManager manager) {
        manager.popTransaction(mStackName);
    }
}
