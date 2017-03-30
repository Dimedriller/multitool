package com.dimedriller.multitool;

import com.dimedriller.advancedfragment.RootFragment;
import com.dimedriller.advancedfragment.ViewInterface;
import com.dimedriller.multitoolmodel.MultitoolModel;

public abstract class MultitoolFragment<VI extends ViewInterface> extends RootFragment<VI, MultitoolModel> {
    public MultitoolFragment(VI viewInt) {
        super(viewInt);
    }

    public MultitoolFragment(Class<VI> viewIntClass) {
        super(viewIntClass);
    }
}
