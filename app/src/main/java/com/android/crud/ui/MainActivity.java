package com.android.crud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.crud.R;
import com.android.crud.adapter.DataListAdapter;
import com.android.crud.database.DatabaseHelper;
import com.android.crud.model.User;
import com.android.crud.ui.dialog.OptionDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<User> userList = new ArrayList<>();
    DataListAdapter adapter;
    TextView noData;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DatabaseHelper(this);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        noData = (TextView) findViewById(R.id.noData);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateDataActivity.class));
            }
        });
    }

    private void initData() {
        userList = helper.getAllUsers();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (!userList.isEmpty()) {
            noData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            adapter = new DataListAdapter(userList);
            recyclerView.setAdapter(adapter);
            adapter.onItemClick(new DataListAdapter.OnItemClickListener() {
                @Override
                public void onClickListener(final User user, final int pos) {
                    OptionDialog dialog = OptionDialog.newInstance(new OptionDialog.OptionDialogListener() {
                        @Override
                        public void onOptionClickListener(int i) {
                            if (i == 0) {
                                startActivity(new Intent(getApplicationContext(), CreateDataActivity.class)
                                        .putExtra("user", user)
                                        .putExtra("isEdit", true));
                            } else {
                                helper.deleteDataById(user.getId());
                                adapter.remove(pos);
                                if (adapter.getItemCount() == 0) {
                                    initData();
                                }
                                Toast.makeText(MainActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show(getSupportFragmentManager(), OptionDialog.class.getSimpleName());
                }
            });
        } else {
            noData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

}
