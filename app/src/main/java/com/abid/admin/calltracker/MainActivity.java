package com.abid.admin.calltracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ListView lv;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> numberlist = new ArrayList<>();
        Set<String> setNumber= null;
        SharedPreferences prefs = getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE);
        editor = prefs.edit();

        //Set<String> setNumber = new HashSet<String>();
        setNumber =  prefs.getStringSet("NumberList",null);

        if (setNumber == null){
             setNumber = new HashSet<String>();
        }else{
            numberlist.addAll(setNumber);
        }


        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        lv = findViewById(R.id.listview);


        //numberlist.add("+917982141661");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,numberlist);

        lv.setAdapter(arrayAdapter);
        final Set<String> finalSetNumber = setNumber;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = ""+editText.getText();

                if (!number.equalsIgnoreCase("")&& (number!=null)){
                    numberlist.add(number);
                    Set<String> numberSet = new HashSet<>(numberlist);
                    editor.putStringSet("NumberList", numberSet);
                    editor.commit();
                    arrayAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(MainActivity.this, "Please Enter number !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
