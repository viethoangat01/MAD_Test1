package com.example.mad_test1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mad_test1.R;
import com.example.mad_test1.utils.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txtAddStudent,txtAddClass,txtListStudent,txtListName,txtListClass,txtSortNumber;
    ListView lvStudent,lvClass;
    Database database;
    private List<String> listStudent,listClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initVariable();
        initDatabase();
        //Tạo danh sách sinh viên
        createListStudent();
        //Tạo danh sách lớp
        createListClass();
        txtAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddStudentActivity.class));
            }
        });
        txtAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddClassActivity.class));
            }
        });
        txtListName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo danh sách sinh viên tên Nam học năm 2
                createListStudent2();
            }
        });
        txtListStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createListStudent();
            }
        });
        txtListClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo danh sách lớp
                createListClass();
            }
        });
        txtSortNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo danh sách lớp sắp xếp theo số lượng sinh viên
                createListClass2();
            }
        });
    }

    private void initVariable() {
        listStudent=new ArrayList<>();
        listClass=new ArrayList<>();
    }

    private void initView() {
        txtAddStudent=(TextView) findViewById(R.id.textview_main_addstudent);
        txtAddClass=(TextView) findViewById(R.id.textview_main_addclass);
        txtListStudent=(TextView) findViewById(R.id.textview_main_liststudent);
        txtListName=(TextView) findViewById(R.id.textview_main_listname);
        txtListClass=(TextView) findViewById(R.id.textview_main_listclass);
        txtSortNumber=(TextView) findViewById(R.id.textview_main_sortnumberstudent) ;
        lvClass=(ListView) findViewById(R.id.listview_main_listclass);
        lvStudent=(ListView) findViewById(R.id.listview_main_liststudent);
    }

    private void initDatabase() {
        database = new Database(this, "test1.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS SinhVien (masv INTEGER PRIMARY KEY AUTOINCREMENT,hoten VARCHAR(200),namsinh INTEGER(5),quequan VARCHAR(200),namhoc VARCHAR(30))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Lop (malop INTEGER PRIMARY KEY AUTOINCREMENT,ten VARCHAR(200),mota VARCHAR(200))");
        database.QueryData("CREATE TABLE IF NOT EXISTS SinhvienLop (masv INTEGER(5),malop INTEGER(5),kyhoc VARCHAR(200),sotinchi INTEGER(5))");
    }

    private void createListClass() {
        ArrayAdapter adapter=new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getListClass()
        );
        lvClass.setAdapter(adapter);
        //Event listview
        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Bundle bundle=new Bundle();
                    String[] result = listClass.get(i).split("-");
                    bundle.putString("malop",result[0]);
                    Intent intent=new Intent(MainActivity.this,AddStudentClassActivity.class);
                    intent.putExtra("com.example.mad_test1.view.MainActivity",bundle);
                    startActivity(intent);
                }catch (Exception e){
                    Log.e("Exception", "onItemClick: "+e.toString());
                }
            }
        });
    }

    //Tạo danh sách lớp sắp xếp theo số lượng sinh viên
    private void createListClass2() {
        ArrayAdapter adapter=new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getListClass2()
        );
        lvClass.setAdapter(adapter);
    }

    private void createListStudent() {
        ArrayAdapter adapter=new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getListStudent()
        );
        lvStudent.setAdapter(adapter);
    }

    //Tạo danh sách sinh viên tên Nam học năm 2
    private void createListStudent2() {
        ArrayAdapter adapter=new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                getListStudent2()
        );
        lvStudent.setAdapter(adapter);
    }

    private List<String> getListStudent(){
        Cursor dataStudent=database.GetData("SELECT * FROM SinhVien");
        listStudent.clear();
        while (dataStudent.moveToNext()){
            String masv=dataStudent.getString(0);
            String hoten=dataStudent.getString(1);
            int namsinh=dataStudent.getInt(2);
            String quequan=dataStudent.getString(3);
            String namhoc=dataStudent.getString(4);
            listStudent.add(masv+"-"+hoten+"-"+namsinh+"-"+namhoc);
        }
        return listStudent;
    }

    //Lấy danh sách sinh viên tên Nam học năm 2
    private List<String> getListStudent2() {
        Cursor dataStudent=database.GetData("SELECT * FROM SinhVien WHERE hoten='Nam' AND namhoc='Năm hai'");
        listStudent.clear();
        while (dataStudent.moveToNext()){
            String masv=dataStudent.getString(0);
            String hoten=dataStudent.getString(1);
            int namsinh=dataStudent.getInt(2);
            String quequan=dataStudent.getString(3);
            String namhoc=dataStudent.getString(4);
            listStudent.add(masv+"-"+hoten+"-"+namsinh+"-"+namhoc);
        }
        return listStudent;
    }

    private List<String> getListClass(){
        Cursor dataClass=database.GetData("SELECT * FROM Lop");
        listClass.clear();
        while (dataClass.moveToNext()){
            String ten=dataClass.getString(1);
            listClass.add(dataClass.getString(0)+"-"+ten);
        }
        return listClass;
    }

    //Lấy danh sách lớp học sắp xếp theo số SV giảm dần
    private List<String> getListClass2() {
        Cursor dataClass=database.GetData("SELECT * FROM Lop");
        listClass.clear();
        List<Integer> listCount=new ArrayList<>();
        while (dataClass.moveToNext()){
            String ten=dataClass.getString(1);
            //Lấy số sinh viên trong một lớp
            Cursor dataStudentClass=database.GetData("SELECT COUNT(masv) from SinhvienLop WHERE malop = '"+dataClass.getString(0)+"' ");
            dataStudentClass.moveToFirst();
            int count =Integer.parseInt(dataStudentClass.getString(dataStudentClass.getColumnIndex(dataStudentClass.getColumnName(0)))) ;
            listClass.add(dataClass.getString(0)+"-"+ten+"-số SV: "+count);
            listCount.add(count);
        }
        //Sort danh sách lớp theo số lượng sinh viên
        int n=listCount.size();
        boolean haveSwap=false;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-i-1;j++){
                if(listCount.get(j)<listCount.get(j+1)){
                    Collections.swap(listCount, j, j+1);
                    Collections.swap(listClass, j, j+1);
                    haveSwap=true;
                }
            }
            if(haveSwap==false) break;
        }
        return listClass;
    }

}
