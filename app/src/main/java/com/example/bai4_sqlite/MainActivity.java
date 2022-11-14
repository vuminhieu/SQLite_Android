package com.example.bai4_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bai4_sqlite.adapter.CustomAdapter;
import com.example.bai4_sqlite.data.DBManager;
import com.example.bai4_sqlite.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText_Name, editText_Email, editText_Phone_Number, editText_Address, editTextID;
    Button button_save, btnUpdate, buttonDelete;
    ListView listView_Student;
    private CustomAdapter customAdapter;
    private List<Student> students;
    DBManager dbManager = new DBManager(this);

    private void mapping() {
        button_save = (Button) findViewById(R.id.btn_save);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        buttonDelete = (Button) findViewById(R.id.btn_delete);
        editText_Name = (EditText) findViewById(R.id.edt_ten);
        editText_Address = (EditText) findViewById(R.id.edt_address);
        editText_Email = (EditText) findViewById(R.id.edt_email);
        editTextID = (EditText) findViewById(R.id.edt_id);
        editText_Phone_Number = (EditText) findViewById(R.id.edt_number);
        listView_Student = (ListView) findViewById(R.id.lv_student);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        students = dbManager.getAllStudent();
        setAdapter();

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = createStudent();
                if (student != null) {
                    dbManager.addStudent(student);
                }
                updateListStudent();
                setAdapter();
            }
        });

        listView_Student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = students.get(position);
                editTextID.setText(String.valueOf(student.getmID()));
                editText_Name.setText(student.getmName());
                editText_Address.setText(student.getmAddress());
                editText_Email.setText(student.getmEmail());
                editText_Phone_Number.setText(student.getmPhoneNumber());
                button_save.setEnabled(false);
                btnUpdate.setEnabled(true);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = new Student();
                student.setmID(Integer.parseInt(String.valueOf(editTextID.getText())));
                student.setmName(editText_Name.getText() + "");
                student.setmAddress(editText_Address.getText() + "");
                student.setmEmail(editText_Email.getText() + "");
                student.setmPhoneNumber(editText_Phone_Number.getText() + "");
                int result = dbManager.updateStudent(student);
                if (result > 0) {
                    dbManager.updateStudent(student);
                    updateListStudent();

                }
                button_save.setEnabled(true);
                btnUpdate.setEnabled(false);
            }
        });

        //btnXoa
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setmID(Integer.parseInt(String.valueOf(editTextID.getText())));
                int result = dbManager.Delete(student.getmID());
                if (result > 0) {
                    updateListStudent();
                    Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Delete false", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //xoa
        listView_Student.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Student student = students.get(position);
                int result = dbManager.Delete(student.getmID());
                if (result > 0) {
                    updateListStudent();
                    Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Delete false", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }


    private Student createStudent() {
        String name = editText_Name.getText().toString().trim();
        String address = editText_Address.getText().toString().trim();
        String phone = editText_Phone_Number.getText().toString().trim();
        String email = editText_Email.getText().toString().trim();

        Student student = new Student(name, address, phone, email);
        return student;
    }

    private void setAdapter() {
        if (customAdapter == null) {
            customAdapter = new CustomAdapter(this, R.layout.item_list_student, students);
            listView_Student.setAdapter(customAdapter);
        } else {
            customAdapter.notifyDataSetChanged();
            listView_Student.setSelection(customAdapter.getCount() - 1);
        }
    }

    public void updateListStudent() {
        students.clear();
        students.addAll(dbManager.getAllStudent());
        if (customAdapter != null) {
            customAdapter.notifyDataSetChanged();
        }
    }
}