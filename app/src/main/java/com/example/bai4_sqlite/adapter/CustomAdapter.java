package com.example.bai4_sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bai4_sqlite.R;
import com.example.bai4_sqlite.model.Student;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Student> {
    private Context context;
    private int resource;
    private List<Student> list;


    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_student, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView_id = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.textView_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.textView_address = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.textView_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.textView_email = (TextView) convertView.findViewById(R.id.tv_email);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Student student = list.get(position);
        viewHolder.textView_id.setText(String.valueOf(student.getmID()));
        viewHolder.textView_name.setText(student.getmName());
        viewHolder.textView_address.setText(student.getmAddress());
        viewHolder.textView_email.setText(student.getmEmail());
        viewHolder.textView_phone.setText(student.getmPhoneNumber());

        return convertView;
    }

    public class ViewHolder {
        private TextView textView_id;
        private TextView textView_name;
        private TextView textView_email;
        private TextView textView_phone;
        private TextView textView_address;
    }
}
