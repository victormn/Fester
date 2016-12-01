package com.example.victor.fester.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.victor.fester.R;
import com.example.victor.fester.Toolbox.BitmapManager;

import java.util.ArrayList;

/**
 * Created by Victor on 21/11/2016.
 */
public class UserDBAdapter {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_QRCODE = "qrcode";

    private String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PHONE, COLUMN_PHOTO, COLUMN_QRCODE};

    public static final String CREATE_TABLE_USER = "create table " + USER_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_PHONE + " text not null, "
            + COLUMN_PHOTO + " blob, "
            + COLUMN_QRCODE + " blob);";

    private SQLiteDatabase sqlDB;
    private Context context;

    private UserDBHelper userDBHelper;

    public UserDBAdapter(Context ctx){
        context = ctx;
    }

    public UserDBAdapter open() throws android.database.SQLException{
        userDBHelper = new UserDBHelper(context);
        sqlDB = userDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        userDBHelper.close();
    }

    public User updateUser(String name, String email, String phone, byte[] photo, byte[] qrcode){

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PHOTO, photo);
        values.put(COLUMN_QRCODE, qrcode);

        long insertId = sqlDB.insert(USER_TABLE,null, values);

        Cursor cursor = sqlDB.query(USER_TABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        User user = cursorToUser(cursor);

        cursor.close();

        return user;
    }

    public User getUser(){
        ArrayList<User> users = new ArrayList<>();
        User returnUser;

        Cursor cursor = sqlDB.query(USER_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            User user = cursorToUser(cursor);
            users.add(user);
        }

        cursor.close();

        // Se nao existir, crie um usuario
        if(users.size() == 0){

            String name = "Usuário";
            String email = "usuario@email.com";
            String phone = "(00)000000000";

            Bitmap photoB = BitmapFactory.decodeResource(context.getResources(), R.drawable.foto);
            byte[] photo = BitmapManager.bitmapToByteArray(photoB);

            Bitmap qrB = BitmapFactory.decodeResource(context.getResources(), R.drawable.qr);
            byte[] qr = BitmapManager.bitmapToByteArray(qrB);

            returnUser = updateUser(name, email, phone, photo, qr);

        }else returnUser = users.get(0);

        return returnUser;
    }

    private User cursorToUser(Cursor cursor){
        User user = new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4), cursor.getBlob(5));
        return user;
    }

    public void deleteUsers(){
        sqlDB.execSQL("delete from "+ USER_TABLE);
    }

    private static class UserDBHelper extends SQLiteOpenHelper {

        UserDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE_USER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(UserDBHelper.class.getName(), "Upgrading database from version "
                    + oldVersion + " to " + newVersion + ", which will destroy all old date");

            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            onCreate(db);
        }
    }
}
