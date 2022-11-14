package com.example.bai4_sqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bai4_sqlite.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DBManager extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "student_manager";
    private static final String TABLE_NAME = "students";
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String ADDRESS = "address";
    private static final String PHONE_NUMBER = "phone";
    private static final String EMAIL = "email";
    private static int VERSION = 1;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    private String SQLQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " +
            EMAIL + " TEXT, " +
            PHONE_NUMBER + " TEXT, " +
            ADDRESS + " TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, student.getmName());
        values.put(ADDRESS, student.getmAddress());
        values.put(PHONE_NUMBER, student.getmPhoneNumber());
        values.put(EMAIL, student.getmEmail());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Student> getAllStudent() {
        List<Student> ListStudent = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) { // database co it nhat 1 gia tri
            do {
                Student student = new Student();
                student.setmID(cursor.getInt(0));
                student.setmName(cursor.getString(1));
                student.setmEmail(cursor.getString(2));
                student.setmPhoneNumber(cursor.getString(3));
                student.setmAddress(cursor.getString(4));

                ListStudent.add(student);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return ListStudent;
    }

    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, student.getmName());
        contentValues.put(ADDRESS, student.getmAddress());
        contentValues.put(EMAIL, student.getmEmail());
        contentValues.put(PHONE_NUMBER, student.getmPhoneNumber());
        String where = ID + " = " + student.getmID();
        return db.update(TABLE_NAME, contentValues, where, null);
    }

    public int Delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = ID + " = " + id;
        return db.delete(TABLE_NAME, where, null);
    }

}
