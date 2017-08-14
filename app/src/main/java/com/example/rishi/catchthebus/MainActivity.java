package com.example.rishi.catchthebus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {


   /* TextView tv;*/
    double latitude;
    double longitude;
    Spinner busSelection;
    String BUS_NO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        busSelection= (Spinner) findViewById(R.id.selectBus);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.buses,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busSelection.setAdapter(adapter);

        busSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BUS_NO= parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void buttonClicked(View view){
        Intent intent=new Intent(this,MapsActivity.class);
        intent.putExtra("BUS_NO",BUS_NO);
        startActivity(intent);
    }

}
