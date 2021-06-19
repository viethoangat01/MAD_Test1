package com.example.mad_test1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad_test1.R;
import com.example.mad_test1.utils.Database;

public class AddClassActivity extends AppCompatActivity {
    Button btnSave;
    EditText edtName,edtDesc;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        initView();
        initDatabase();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtName.getText().toString().isEmpty() || edtDesc.getText().toString().isEmpty()){
                    Toast.makeText(AddClassActivity.this, "Thông tin không được bỏ trống!", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        database.QueryData("INSERT INTO Lop VALUES(null,'"+edtName.getText().toString()+"','"+edtDesc.getText().toString()+"')");
                        startActivity(new Intent(AddClassActivity.this, MainActivity.class));
                    }catch (Exception e){
                        Log.e("Exception", "onClick: "+e.toString() );
                    }
                }
            }
        });

    }

    private void initDatabase() {
        database = new Database(this, "test1.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Lop (malop INTEGER PRIMARY KEY AUTOINCREMENT,ten VARCHAR(200),mota VARCHAR(200))");
    }

    private void initView() {
        btnSave=(Button) findViewById(R.id.button_addclass_save);
        edtName=(EditText) findViewById(R.id.edittext_addclass_name);
        edtDesc=(EditText) findViewById(R.id.edittext_addclass_desc);
    }
}
