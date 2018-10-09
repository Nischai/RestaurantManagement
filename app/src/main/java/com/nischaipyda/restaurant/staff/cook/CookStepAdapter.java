package com.nischaipyda.restaurant.staff.cook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;



public class CookStepAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "POSITION";

    public CookStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(@IntRange(from = 0L) int position) {

        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);

        switch (position) {
            case 0:
                final CookTableFragment tableFragment = new CookTableFragment();
                tableFragment.setArguments(b);
                return tableFragment;

            case 1:
                final OrderCookedFragment orderCookedFragment = new OrderCookedFragment();
                orderCookedFragment.setArguments(b);
                return orderCookedFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
