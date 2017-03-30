package android.support.v7.app;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

public class ActionBarDrawerIndicator extends ActionBarDrawerToggle {
    public ActionBarDrawerIndicator(Activity activity,
            Toolbar toolbar,
            DrawerLayout drawerLayout,
            DrawerIndicatorDrawable slider,
            @StringRes int openDrawerContentDescRes,
            @StringRes int closeDrawerContentDescRes) {
        super(activity, toolbar, drawerLayout, slider, openDrawerContentDescRes, closeDrawerContentDescRes);
    }
}
