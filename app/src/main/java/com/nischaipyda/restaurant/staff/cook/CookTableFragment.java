package com.nischaipyda.restaurant.staff.cook;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nischaipyda.restaurant.R;
import com.nischaipyda.restaurant.customer.TableService;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class CookTableFragment extends Fragment implements BlockingStep{

    int selectedTable = -1;
    DatabaseReference dref;
    private CookTableDataModel tableDataModel;

    public CookTableFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cook_table, container, false);
        loadListView(v);
        return v;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        tableDataModel = (CookTableDataModel) context;
    }

    private void loadListView(View v) {
        ListView lv = (ListView)v.findViewById(R.id.lv_cook_table);
        dref = FirebaseDatabase.getInstance().getReference().child("Table");

        FirebaseListAdapter<TableService> fla = new FirebaseListAdapter<TableService>(
                getActivity(),
                TableService.class,
                R.layout.layout_table_radio,
                dref
        ) {
            @Override
            protected void populateView(View v, TableService model, final int position) {
                TextView textView = (TextView) v.findViewById(R.id.label);
                RadioButton radioButton = (RadioButton) v.findViewById(R.id.radio_button);
                String table = "Table "+ (position+1);
                if (!model.getOrdered()) {
                    radioButton.setEnabled(false);
                    textView.setText(table);
                    return;
                }

                textView.setText(table);
                radioButton.setChecked(position == selectedTable);
                radioButton.setEnabled(true);
                radioButton.setTag(position);

                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedTable = position;
                        notifyDataSetChanged();
                    }
                });
            }
        } ;

        lv.setAdapter(fla);

    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

        tableDataModel.saveTableNumber(selectedTable);
        callback.goToNextStep();
    }


    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if (selectedTable == -1) {
            VerificationError error = new VerificationError("Please select your Table");
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
}
