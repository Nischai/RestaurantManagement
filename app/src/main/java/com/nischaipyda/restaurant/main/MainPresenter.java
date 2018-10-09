package com.nischaipyda.restaurant.main;

import android.view.View;

/**
 * Created by nisch on 11-09-2017.
 */

public class MainPresenter implements MainContract.Presenter{

    MainContract.View view;
    public MainPresenter(MainContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void customerOptionChecked() {
        view.startCustomerActivity();
    }

    @Override
    public void staffOptionChecked() {
        view.startStaffActivity();
    }

    @Override
    public void noOptionsChecked() {
        view.makeToast("No option selected");
    }
}
