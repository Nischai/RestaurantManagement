package com.nischaipyda.restaurant.customer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nischaipyda.restaurant.R;
import com.nischaipyda.restaurant.main.MainActivity;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;


public class FoodMenuFragment extends Fragment implements BlockingStep{

    FirebaseListAdapter<FoodItem> firebaseListAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dref;
    int p[];
    String n[];
    TableDataModel tableDataModel;

    public FoodMenuFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        return inflater.inflate(R.layout.fragment_food_menu, container, false);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        tableDataModel = (TableDataModel) context;
        Log.i("Nischai",Integer.toString(tableDataModel.getTableNumber()));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        n = new String[5];
        p = new int[5];

        for(int i = 0; i<n.length;i++){
            p[i] = -1;
        }

        Log.i("Nischai",Integer.toString(tableDataModel.getTableNumber()));

        loadListView(view);
    }

    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.lv_food_menu);
        dref = firebaseDatabase.getReference().child("Food");

        firebaseListAdapter = new FirebaseListAdapter<FoodItem>(
                getActivity(),
                FoodItem.class,
                R.layout.layout_food_item,
                dref
        ) {
            @Override
            protected void populateView(View v, final FoodItem model, final int position) {
                final TextView foodName = (TextView) v.findViewById(R.id.label1);
                final TextView price = (TextView) v.findViewById(R.id.label2);
                final CheckBox checkBox = (CheckBox) v.findViewById(R.id.cb_food_item);
                String strPrice = "Price :" + model.getPrice();

                foodName.setText(model.getName());
                price.setText(strPrice);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()) {
                            n[position] = model.getName();
                            p[position] = model.getPrice();
                        }
                        else {
                            n[position] = null;
                            p[position] = -1;
                        }
                        notifyDataSetChanged();
                    }
                });
            }
        };
        listView.setAdapter(firebaseListAdapter);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        boolean flag = false;
        for(int i=0;i<n.length;i++)
            if(n[i] != null){
                flag = true;
                break;
            }

        if (!flag) {
            VerificationError error = new VerificationError("No Food Item Selected");
            onError(error);
            return error;
        }
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        Toast.makeText(getContext(),error.getErrorMessage(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        String num = Integer.toString(tableDataModel.getTableNumber()+1);
        dref = firebaseDatabase.getReference();
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Ordered").setValue(true);
        dref.child("Orders").child(num).removeValue();
        dref = firebaseDatabase.getReference().child("Orders").child(num);
        int k=1,total=0,gst=0;
        for (int i =0;i<n.length;i++) {
            if(n[i] != null && p[i] != -1) {
                dref.child(Integer.toString(k)).child("Name").setValue(n[i]);
                dref.child(Integer.toString(k)).child("Price").setValue(p[i]);
                total += p[i];
                k++;
            }
        }
        gst = (18*total)/100;
        total += gst;
        dref = FirebaseDatabase.getInstance().getReference().child("Total");
        dref.child("gst").setValue(gst);
        dref.child("total").setValue(total);
        callback.goToNextStep();

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Reserved").setValue(false);
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Cleaned").setValue(true);
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Served").setValue(false);
        dref.child("Table").child("" +(tableDataModel.getTableNumber()+1)).child("Ordered").setValue(false);

        startActivity(new Intent(getContext(), MainActivity.class));
    }
}
