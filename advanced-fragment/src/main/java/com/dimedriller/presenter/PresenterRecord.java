package com.dimedriller.presenter;

class PresenterRecord {
    final Presenter mPresenter;
    final ViewLocator mLocator;
    final ViewPlacer mPlacer;

    PresenterRecord(Presenter presenter, ViewLocator locator, ViewPlacer placer) {
        mPresenter = presenter;
        mLocator = locator;
        mPlacer = placer;
    }
}
