package com.dimedriller.presenter;

public interface PendingAction {
    boolean isPersistable();
    void act(PresenterManager manager);
}
