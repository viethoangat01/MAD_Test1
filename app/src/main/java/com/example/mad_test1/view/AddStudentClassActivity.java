package com.example.mad_test1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mad_test1.R;
import com.example.mad_test1.utils.Database;

import java.util.ArrayList;
import java.util.List;

public class AddStudentClassActivity extends AppCompatActivity {
    TextView txtClass,txtSave,txtBack;
    EditText edtMaSV,edtTerm,edtCredit;
    ListView lvStudent;
    Database database;
    private List<String> listStudentClass;
    ArrayAdapter adapter;
    String malop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_class);
        initView();
        initVariable();
        initDatabase();
        getDataBundle();
        createListStudentClass();
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtMaSV.getText().toString().isEmpty()){
                    database.QueryData("INSERT INTO SinhvienLop VALUES('"+edtMaSV.getText().toString()+"','"+malop+"','"+edtTerm.getText().toString()+"','"+edtCredit.getText().toString()+"')");
                    setListStudentClass();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddStudentClassActivity.this,MainActivity.class));
            }
        });
    }

    private void initView() {
        edtMaSV=(EditText) findViewById(R.id.edittext_addstudentclass_id);
        edtCredit=(EditText) findViewById(R.id.edittext_addstudentclass_credit);
        edtTerm=(EditText) findViewById(R.id.edittext_addstudentclass_term);
        txtClass=(TextView) findViewById(R.id.textview_addstudentclass_class);
        txtBack=(TextView) findViewById(R.id.textview_addstudentclass_back);
        txtSave=(TextView) findViewById(R.id.textview_addstudentclass_save);
        lvStudent=(ListView) findViewById(R.id.listview_addstudentclass);
    }

    private void initVariable() {
        listStudentClass=new ArrayList<>();
    }

    private void initDatabase() {
        database = new Database(this, "test1.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS SinhvienLop (masv INTEGER(5),malop INTEGER(5),kyhoc VARCHAR(200),sotinchi INTEGER(5))");
    }

    private void createListStudentClass() {
        setListStudentClass();
        adapter=new ArrayAdapter(
                AddStudentClassActivity.this,
                android.R.layout.simple_list_item_1,
                listStudentClass
        );
        lvStudent.setAdapter(adapter);
    }

    private void setListStudentClass(){
        Cursor dataStudent=database.GetData("SELECT * FROM SinhvienLop WHERE malop='"+malop+"'");
        listStudentClass.clear();
        while (dataStudent.moveToNext()){
            String masv=dataStudent.getString(0);
            String kyhoc=dataStudent.getString(2);
            int sotinchi=dataStudent.getInt(3);
            listStudentClass.add(masv+"-"+kyhoc+"-"+sotinchi);
        }
    }

    private void getDataBundle() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("com.example.mad_test1.view.MainActivity");
        if(bundle!=null){
            malop=bundle.getString("malop");
            txtClass.setText(malop+"");
        }
    }
}
