package com.example.mad_test1.view;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_test1.R;
import com.example.mad_test1.utils.Database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txtListStudent, txtSortStudent,
            txtListClass, txtSortClass;
    ListView lvStudent, lvClass, lvStudentClass;
    Button btnAddStudent, btnAddClass, btnAddStudentClass;
    EditText edtNameStudent, edtYearStudent, edtAddressStudent,
            edtNameClass, edtDescClass, edtSchoolYearStudent,
            edtIdStudentClass, edtTermStudentClass, edtCreditStudentClass;
    Database database;
    ArrayAdapter adapterClass, adapterStudent, adapterStudentClass;
    private List<String> listStudent, listClass, listStudentClass;
    String idClass;//For adding student to class by idClass

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initVariable();
        initDatabase();
        //Add student
        initListStudent();
        //Add Class
        initListClass();
        //Part 3
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNameStudent.getText().toString().isEmpty() || edtYearStudent.getText().toString().isEmpty() || edtAddressStudent.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Thông tin không được bỏ trống!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        database.QueryData("INSERT INTO SinhVien VALUES(null,'" +
                                edtNameStudent.getText().toString() + "','" +
                                Integer.parseInt(edtYearStudent.getText().toString()) + "','" +
                                edtAddressStudent.getText().toString() + "','" +
                                edtSchoolYearStudent.getText().toString() + "')");
                        initListStudent();
                    } catch (Exception e) {
                        Log.e("Exception", "onClick: " + e.toString());
                    }
                }
            }
        });
        //Part 4
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNameClass.getText().toString().isEmpty() || edtDescClass.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Thông tin không được bỏ trống!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        database.QueryData("INSERT INTO Lop VALUES(null,'" +
                                edtNameClass.getText().toString() + "','" +
                                edtDescClass.getText().toString() + "')");
                        initListClass();
                    } catch (Exception e) {
                        Log.e("Exception", "onClick: " + e.toString());
                    }
                }
            }
        });
        //Part 5
        lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String[] infoClass = listClass.get(i).split("\n");
                idClass = infoClass[0];
                Toast.makeText(MainActivity.this, "Lớp " + idClass, Toast.LENGTH_SHORT).show();
                initListStudentClass(idClass);
            }
        });
        btnAddStudentClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtIdStudentClass.getText().toString().isEmpty()) {
                    database.QueryData("INSERT INTO SinhvienLop VALUES('" + edtIdStudentClass.getText().toString() + "','" + idClass + "','" + edtTermStudentClass.getText().toString() + "','" + edtCreditStudentClass.getText().toString() + "')");
                    initListStudentClass(idClass);
                }
            }
        });
        //Part 6
        txtSortStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortListStudent();
            }
        });
        txtListStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initListStudent();
            }
        });
        //Part 7
        txtSortClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortListClass();
            }
        });
        txtListClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initListClass();
            }
        });
    }
    //////////////////////////////////////////////////////////////////
    private void initListStudent() {
        listStudent = getListStudent();
        adapterStudent = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                listStudent
        );
        lvStudent.setAdapter(adapterStudent);
    }

    private void initListClass() {
        listClass = getListClass();
        adapterClass = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                listClass
        );
        lvClass.setAdapter(adapterClass);
    }

    private void initListStudentClass(String idClass) {
        listStudentClass = getListStudentByClassID(idClass);
        adapterStudentClass = new ArrayAdapter(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                listStudentClass
        );
        lvStudentClass.setAdapter(adapterStudentClass);
    }

    //////////////////////////////////////////////////////////////////
    private List<String> getListStudent() {
        Cursor dataStudent = database.GetData("SELECT * FROM SinhVien");
        listStudent.clear();
        while (dataStudent.moveToNext()) {
            String masv = dataStudent.getString(0);
            String hoten = dataStudent.getString(1);
            int namsinh = dataStudent.getInt(2);
            String quequan = dataStudent.getString(3);
            String namhoc = dataStudent.getString(4);
            listStudent.add(masv + "\n" + hoten + "\n" + namsinh + "\n" + namhoc);
        }
        return listStudent;
    }

    private List<String> getListClass() {
        Cursor dataClass = database.GetData("SELECT * FROM Lop");
        listClass.clear();
        while (dataClass.moveToNext()) {
            String ten = dataClass.getString(1);
            listClass.add(dataClass.getString(0) + "\n" + ten);
        }
        return listClass;
    }

    private List<String> getListStudentByClassID(String idClass) {
        Cursor dataStudent = database.GetData("SELECT * FROM SinhvienLop WHERE malop='" + idClass + "'");
        listStudentClass.clear();
        while (dataStudent.moveToNext()) {
            String masv = dataStudent.getString(0);
            String kyhoc = dataStudent.getString(2);
            int sotinchi = dataStudent.getInt(3);
            listStudentClass.add(masv + "-" + kyhoc + "-" + sotinchi);
        }
        return listStudentClass;
    }

    //////////////////////////////////////////////////////////////////
    private void sortListClass() {
        Cursor dataClass = database.GetData("SELECT * FROM Lop");
        listClass.clear();
        List<Integer> listCount = new ArrayList<>();
        while (dataClass.moveToNext()) {
            String ten = dataClass.getString(1);
            //Lấy số sinh viên trong một lớp
            Cursor dataStudentClass = database.GetData("SELECT COUNT(masv) from SinhvienLop WHERE malop = '" + dataClass.getString(0) + "' ");
            dataStudentClass.moveToFirst();
            int count = Integer.parseInt(dataStudentClass.getString(dataStudentClass.getColumnIndex(dataStudentClass.getColumnName(0))));
            listClass.add(dataClass.getString(0) + "-" + ten + "-số SV: " + count);
            listCount.add(count);
        }
        //Sort danh sách lớp theo số lượng sinh viên
        int n = listCount.size();
        boolean haveSwap = false;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (listCount.get(j) < listCount.get(j + 1)) {
                    Collections.swap(listCount, j, j + 1);
                    Collections.swap(listClass, j, j + 1);
                    haveSwap = true;
                }
            }
            if (haveSwap == false) break;
        }
        adapterClass.notifyDataSetChanged();
    }

    private void sortListStudent() {
        Cursor dataStudent = database.GetData("SELECT * FROM SinhVien WHERE hoten='Nam' AND namhoc='Năm hai'");
        listStudent.clear();
        while (dataStudent.moveToNext()) {
            String masv = dataStudent.getString(0);
            String hoten = dataStudent.getString(1);
            int namsinh = dataStudent.getInt(2);
            String quequan = dataStudent.getString(3);
            String namhoc = dataStudent.getString(4);
            listStudent.add(masv + "\n" + hoten + "\n" + namsinh + "\n" + namhoc);
        }
        adapterStudent.notifyDataSetChanged();
    }

    //////////////////////////////////////////////////////////////////
    private void initDatabase() {
        database = new Database(this, "test1.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS SinhVien (masv INTEGER PRIMARY KEY AUTOINCREMENT,hoten VARCHAR(200),namsinh INTEGER(5),quequan VARCHAR(200),namhoc VARCHAR(30))");
        database.QueryData("CREATE TABLE IF NOT EXISTS Lop (malop INTEGER PRIMARY KEY AUTOINCREMENT,ten VARCHAR(200),mota VARCHAR(200))");
        database.QueryData("CREATE TABLE IF NOT EXISTS SinhvienLop (masv INTEGER(5),malop INTEGER(5),kyhoc VARCHAR(200),sotinchi INTEGER(5))");
    }

    private void initView() {
        txtListStudent = (TextView) findViewById(R.id.textview_main_liststudent);
        txtSortStudent = (TextView) findViewById(R.id.textview_main_listname);
        txtListClass = (TextView) findViewById(R.id.textview_main_listclass);
        txtSortClass = (TextView) findViewById(R.id.textview_main_sortnumberstudent);
        lvClass = (ListView) findViewById(R.id.listview_main_listclass);
        lvStudent = (ListView) findViewById(R.id.listview_main_liststudent);
        lvStudentClass = (ListView) findViewById(R.id.listview_main_liststudentclass);
        //Add student
        btnAddStudent = (Button) findViewById(R.id.button_main_addstudent);
        edtNameStudent = (EditText) findViewById(R.id.edittext_main_namestudent);
        edtYearStudent = (EditText) findViewById(R.id.edittext_main_yearstudent);
        edtAddressStudent = (EditText) findViewById(R.id.edittext_main_addressstudent);
        edtSchoolYearStudent = (EditText) findViewById(R.id.edittext_main_schoolyearstudent);
        //Add class
        btnAddClass = (Button) findViewById(R.id.button_main_addclass);
        edtNameClass = (EditText) findViewById(R.id.edittext_main_nameclass);
        edtDescClass = (EditText) findViewById(R.id.edittext_main_descclass);
        //Add Student Class
        btnAddStudentClass = (Button) findViewById(R.id.button_main_addstudentclass);
        edtIdStudentClass = (EditText) findViewById(R.id.edittext_main_idstudentclass);
        edtTermStudentClass = (EditText) findViewById(R.id.edittext_main_termstudentclass);
        edtCreditStudentClass = (EditText) findViewById(R.id.edittext_main_credittudentclass);

    }

    private void initVariable() {
        listStudent = new ArrayList<>();
        listClass = new ArrayList<>();
        listStudentClass = new ArrayList<>();
        idClass = "";
    }
}
