package com.nischaipyda.restaurant.staff.cook;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nischaipyda.restaurant.R;
import com.nischaipyda.restaurant.customer.FoodItem;
import com.nischaipyda.restaurant.main.MainActivity;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderCookedFragment extends Fragment implements BlockingStep {

    View v;
    DatabaseReference dref;
    CookTableDataModel tableDataModel;
    int position;
    public OrderCookedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        tableDataModel = (CookTableDataModel) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_order_cooked, container, false);
        return v;
    }

    private void loadListView(View v) {
        ListView lv = (ListView)v.findViewById(R.id.listview_orderToBeCooked);
        dref = FirebaseDatabase.getInstance().getReference().child("Orders").child(Integer.toString(position));
        FirebaseListAdapter<FoodItem> firebaseListAdapter = new FirebaseListAdapter<FoodItem>(
                getActivity(),
                FoodItem.class,
                android.R.layout.simple_list_item_1,
                dref
        ) {
            @Override
            protected void populateView(View v, FoodItem model, int position) {
                TextView tv = (TextView)v.findViewById(android.R.id.text1);
                tv.setText(model.getName());
            }
        };

        lv.setAdapter(firebaseListAdapter);
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        dref =FirebaseDatabase.getInstance().getReference().child("Table").child(Integer.toString(position));
        dref.child("Cooked").setValue(true);
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        position = tableDataModel.getTableNumber()+1;
        loadListView(v);
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
