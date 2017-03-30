package com.dimedriller.multitool;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.dimedriller.advancedfragment.BaseActionBarFragmentActivity;
import com.dimedriller.advancedfragment.BaseFragmentActivity;
import com.dimedriller.multitool.purchases.PurchasesFragment;

public class MainActivity extends BaseActionBarFragmentActivity {
    @Override
    protected int getContentLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected int getToolbarViewID() {
        return R.id.Toolbar;
    }

    @Override
    protected int getAnchorViewID() {
        return R.id.Content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {getNavigationManager().showFragmentOnly(new PurchasesFragment());}, 500);
    }
}
