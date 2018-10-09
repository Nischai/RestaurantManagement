package com.nischaipyda.restaurant.staff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nischaipyda.restaurant.R;
import com.nischaipyda.restaurant.customer.TableService;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        ListView tablesToClean = (ListView) findViewById(R.id.lv_serve_tables);
        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("Table");

        final FirebaseListAdapter<TableService> firebaseListAdapter = new FirebaseListAdapter<TableService>(
                ServerActivity.this,
                TableService.class,
                R.layout.layout_serve_table,
                dref
        ) {
            @Override
            protected void populateView(View v, TableService model, final int position) {

                TextView textView = (TextView)v.findViewById(R.id.tv_serve_table_name);
                Button button = (Button)v.findViewById(R.id.btn_serve_table);
                final String table = "Table  "+(position+1);

                textView.setText(table);

                if(model.getCooked() && !model.getServed()){
                    button.setEnabled(true);
                    button.setText("Done");
                }

                if (model.getServed() || !model.getCooked()){
                    button.setText("Served");
                    button.setEnabled(false);
                    return ;
                }



                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dref.child(Integer.toString(position+1)).child("Served").setValue(true);
                        notifyDataSetChanged();
                    }
                });

            }
        };

        tablesToClean.setAdapter(firebaseListAdapter);
    }
}
