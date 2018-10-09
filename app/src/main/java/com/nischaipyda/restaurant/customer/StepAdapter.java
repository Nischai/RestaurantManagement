package com.nischaipyda.restaurant.customer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;


public class StepAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "POSITION";

    public StepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(@IntRange(from = 0L) int position) {

        Bundle b = new Bundle();
        b.putInt(CURRENT_STEP_POSITION_KEY, position);

        switch (position) {
            case 0:
                final TableFragment tableFragment = new TableFragment();
                tableFragment.setArguments(b);
                return tableFragment;

            case 1:
                final FoodMenuFragment foodMenuFragment = new FoodMenuFragment();
                foodMenuFragment.setArguments(b);
                return foodMenuFragment;

            case 2:
                final BillFragment billFragment = new BillFragment();
                billFragment.setArguments(b);
                return billFragment;

            case 3:
                final FeedbackFragment feedbackFragment = new FeedbackFragment();
                feedbackFragment.setArguments(b);
                return feedbackFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
