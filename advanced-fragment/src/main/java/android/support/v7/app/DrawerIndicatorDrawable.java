package android.support.v7.app;

import android.content.Context;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

public abstract class DrawerIndicatorDrawable extends DrawerArrowDrawable {
    public DrawerIndicatorDrawable(Context context) {
        super(context);
    }

    public abstract void showState(ActionBarIndicatorState state, boolean isForced);
}
