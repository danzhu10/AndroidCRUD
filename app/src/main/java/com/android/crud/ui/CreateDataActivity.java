package com.android.crud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.android.crud.R;
import com.android.crud.database.DatabaseHelper;
import com.android.crud.model.User;

import java.util.Calendar;

/**
 * Created by EduSPOT on 22/07/2017.
 */

public class CreateDataActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout nameTV;
    TextInputLayout descTV;
    TextView dateCreated;
    TextView dateUpdated;
    Button save;
    DatabaseHelper helper;
    User userEdit;
    boolean isEdit = false;
    String currentDate;
    String dateCreatedStr;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
        helper = new DatabaseHelper(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userEdit = (User) getIntent().getExtras().getSerializable("user");
            isEdit = getIntent().getExtras().getBoolean("isEdit");
        }
        initCalendar();
        initView();
    }

    private void initCalendar() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        currentDate = mDay + "/" + mMonth + "/" + mYear;
    }

    private void initView() {
        nameTV = (TextInputLayout) findViewById(R.id.nameTV);
        descTV = (TextInputLayout) findViewById(R.id.descriptionTV);
        dateCreated = (TextView) findViewById(R.id.dateCreated);
        dateUpdated = (TextView) findViewById(R.id.dateUpdated);
        save = (Button) findViewById(R.id.saveData);
        save.setOnClickListener(this);

        if (isEdit) {
            save.setText("Update");
            nameTV.getEditText().setText(userEdit.getName());
            descTV.getEditText().setText(userEdit.getDescription());
            dateCreatedStr = userEdit.getDateCreated();
            dateCreated.setText("Date Created : " + dateCreatedStr);
            dateUpdated.setText("Last Updated : " + userEdit.getDateUpdated());
            setTitle("Edit Data");
        } else {
            setTitle("Input Data");
            dateUpdated.setVisibility(View.GONE);
            dateCreatedStr = currentDate;
            dateCreated.setText("Date Created : " + dateCreatedStr);
        }
    }

    @Override
    public void onClick(View view) {
        if (validation(nameTV, descTV)) {
            User user = new User();
            user.setName(nameTV.getEditText().getText().toString());
            user.setDescription(descTV.getEditText().getText().toString());
            user.setDateCreated(dateCreatedStr);
            user.setDateUpdated(currentDate);
            if (isEdit) {
                user.setId(userEdit.getId());
                helper.updateDataById(user);
            } else {
                helper.insertData(user);
            }
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

    }

    public boolean validation(TextInputLayout nameWrapper, TextInputLayout description) {
        boolean isDecsc = false;
        boolean isName = false;
        EditText desc = description.getEditText();
        EditText name = nameWrapper.getEditText();
        if (name.getText().toString().equalsIgnoreCase("")) {
            nameWrapper.setErrorEnabled(true);
            nameWrapper.setError("Fill Your Name");
        } else {
            nameWrapper.setErrorEnabled(false);
            isName = true;
        }

        if (desc.getText().toString().equalsIgnoreCase("")) {
            description.setErrorEnabled(true);
            description.setError("Fill Description");
        } else {
            description.setErrorEnabled(false);
            isDecsc = true;
        }

        return isName && isDecsc;
    }
}
