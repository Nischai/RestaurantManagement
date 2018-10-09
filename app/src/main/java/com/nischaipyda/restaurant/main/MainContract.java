package com.nischaipyda.restaurant.main;

import android.view.View;

import com.nischaipyda.restaurant.BaseView;


public interface MainContract {

    interface View extends BaseView<Presenter>{
        void startCustomerActivity();

        void startStaffActivity();

        void makeToast(String message);
    }

    interface Presenter{

        void customerOptionChecked();

        void staffOptionChecked();

        void noOptionsChecked();

    }
}
