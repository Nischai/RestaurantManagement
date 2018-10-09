package com.nischaipyda.restaurant.customer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.List;

public class TableFragment extends Fragment implements BlockingStep {

    private int selectedTable = -1;
    FirebaseListAdapter<TableService> firebaseListAdapter;
    DatabaseReference dref;
    TableDataModel tableDataModel;

    public TableFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_table, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadListView(view);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        tableDataModel = (TableDataModel) context;
    }

    private void loadListView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.lv_table_reserve);
        dref = FirebaseDatabase.getInstance().getReference().child("Table");
        firebaseListAdapter = new FirebaseListAdapter<TableService>(
                getActivity(),
                TableService.class,
                R.layout.layout_table_radio,
                dref
        ) {
            @Override
            protected void populateView(View v, TableService model, int position) {
                TextView textView = (TextView) v.findViewById(R.id.label);
                RadioButton radioButton = (RadioButton) v.findViewById(R.id.radio_button);
                String table = "Table "+ (position+1);

                if (model.getReserved()) {
                    table += "  -  (Reserved)";
                    radioButton.setEnabled(false);
                    textView.setText(table);
                    return;
                }else if (!model.getCleaned()){
                    table += "  -  (Cleaning)";
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
                        selectedTable = (Integer) v.getTag();
                        notifyDataSetChanged();
                    }
                });
            }

        };
        listView.setAdapter(firebaseListAdapter);
    }

    public void updateTableReserve(Boolean b){
        dref = FirebaseDatabase.getInstance().getReference();
        dref.child("Table").child(Integer.toString(selectedTable+1)).child("Reserved").setValue(b);
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

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        updateTableReserve(true);
        tableDataModel.saveTableNumber(selectedTable);
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }
}
