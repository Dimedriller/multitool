package com.dimedriller.presenter;

abstract class TransactionStep {
    abstract void actDirect(PresenterManager manager);
    abstract void actReverse(PresenterManager manager);
}
