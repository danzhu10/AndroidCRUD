package com.android.crud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.crud.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by EduSPOT on 21/07/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "soding.db";
    private static final String TABLE_USER = "user";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private Context context;
    private String query;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String DATE_CREATED = "dateCreated";
    private static final String DATE_UPDATED = "dateUpdate";

    // SQL Commands
    public static final String CREATE = "CREATE TABLE "
            + TABLE_USER + " ("
            + ID + " INTEGER, "
            + NAME + " VARCHAR(100), "
            + DESCRIPTION + " VARCHAR(255), "
            + DATE_CREATED + " VARCHAR(20), "
            + DATE_UPDATED + " VARCHAR(20), "
            + "PRIMARY KEY(" + ID + " ) "
            + ")";
    public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_USER;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase dbx) {
        this.db = dbx;
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP);
        db.execSQL(CREATE);
    }

    public void insertData(User user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(DESCRIPTION, user.getDescription());
        values.put(DATE_CREATED, user.getDateCreated());
        values.put(DATE_UPDATED, user.getDateUpdated());
        db.insert(TABLE_USER, null, values);
    }

    public void deleteDataById(String id) {
        db = this.getWritableDatabase();
        query = "DELETE FROM '" + TABLE_USER + "' WHERE id = '" + id + "' ";
        db.execSQL(query);
    }

    public void updateDataById(User user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(DESCRIPTION, user.getDescription());
        values.put(DATE_CREATED, user.getDateCreated());
        values.put(DATE_UPDATED, user.getDateUpdated());
        db.update(TABLE_USER, values, "id = '" + user.getId()+"'", null);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        db = this.getReadableDatabase();
        query = "SELECT * FROM " + TABLE_USER + " ORDER BY ID desc";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getString(0));
                user.setName(cursor.getString(1));
                user.setDescription(cursor.getString(2));
                user.setDateCreated(cursor.getString(3));
                user.setDateUpdated(cursor.getString(4));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }
}
