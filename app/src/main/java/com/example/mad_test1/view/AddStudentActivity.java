package com.example.mad_test1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mad_test1.R;
import com.example.mad_test1.utils.Database;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {
    Button btnSave;
    EditText edtName,edtYear,edtAddress;
    Spinner spSchoolYear;
    Database database;
    ArrayList<String> yearList;
    private static String schoolyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        initView();
        initSpinner();
        initDatabase();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().isEmpty()||edtYear.getText().toString().isEmpty()||edtAddress.getText().toString().isEmpty()){
                    Toast.makeText(AddStudentActivity.this, "Thông tin không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        database.QueryData("INSERT INTO SinhVien VALUES(null,'"+edtName.getText().toString()+"','"+Integer.parseInt(edtYear.getText().toString())+"','"+edtAddress.getText().toString()+"','"+schoolyear+"')");
                        startActivity(new Intent(AddStudentActivity.this, MainActivity.class));
                    }catch (Exception e){
                        Log.e("Exception", "onClick: "+e.toString() );
                    }
                }
            }
        });



    }

    private void initSpinner() {
        yearList=new ArrayList<>();
        yearList.add("Năm nhất");
        yearList.add("Năm hai");
        yearList.add("Năm ba");
        yearList.add("Năm bốn");
        //Tao adapter
        ArrayAdapter<String> spinAdapter=new ArrayAdapter<String>(
                AddStudentActivity.this,
                android.R.layout.simple_spinner_item,
                yearList
        );
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spSchoolYear.setAdapter(spinAdapter);

        schoolyear="";
        spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    schoolyear=yearList.get(i).toString();
                    Log.d("AAAAAAAAAAAAA", "onClick: "+i+" "+schoolyear);
                }catch (Exception e){
                    Log.e("Exception", "onItemSelected: "+e.toString() );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                schoolyear=yearList.get(0).toString();
            }
        });
    }

    public void initDatabase(){
        database = new Database(this, "test1.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS SinhVien (masv INTEGER PRIMARY KEY AUTOINCREMENT,hoten VARCHAR(200),namsinh INTEGER(5),quequan VARCHAR(200),namhoc VARCHAR(30))");
    }

    private void initView() {
        btnSave=(Button) findViewById(R.id.button_addstudent_save);
        edtName=(EditText) findViewById(R.id.edittext_addstudent_name);
        edtYear=(EditText) findViewById(R.id.edittext_addstudent_year);
        edtAddress=(EditText) findViewById(R.id.edittext_addstudent_address);
        spSchoolYear=(Spinner) findViewById(R.id.spinner_addstudent_schoolyear);
    }
}
