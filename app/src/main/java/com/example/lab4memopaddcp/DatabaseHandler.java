package com.example.lab4memopaddcp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final int CSV_RECORD_LENGTH = 1;
    private static final String DATABASE_NAME = "memoDatabase.db";

    private static final String TABLE_MEMOS = "Memos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";
    private final List<String[]> init;
    public static final String QUERY_CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MEMOS + " (" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_MEMO + " text)";
    public static final String QUERY_DELETE_CONTACTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_MEMOS;
    public static final String QUERY_GET_MEMO = "SELECT * FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = ?";
    public static final String QUERY_GET_ALL_MEMOS = "SELECT * FROM " + TABLE_MEMOS;
    public static final String QUERY_DELETE_MEMO = COLUMN_ID + " = ?";




    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

        try {

            int id = R.raw.init;
            BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(id)));
            StringBuilder s = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                Log.i("MainActivity", line + "  init Line");
                s.append(line).append('\n');
            }

            CSVReader reader = new CSVReaderBuilder(new StringReader(s.toString())).withCSVParser(new CSVParserBuilder().withSeparator('\t').build()).build();
            init = reader.readAll();

            br.close();
            reader.close();

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_CONTACTS_TABLE);
        for (String[] record : init) {
            if (record.length >= CSV_RECORD_LENGTH) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_MEMO, record[0]);
                db.insert(TABLE_MEMOS, null, values);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DELETE_CONTACTS_TABLE);
        onCreate(db);
    }

    public void addMemo(Memo m) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, m.getMemo());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }

    public String deleteAllMemos() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMOS, null, null);
        return("Memos Deleted");
    }

    public String deleteMemo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL(QUERY_DELETE_MEMO,new String[]{String.valueOf(id)});
        db.delete(TABLE_MEMOS,QUERY_DELETE_MEMO,new String[]{String.valueOf(id)});
        db.close();
        return("Memos Deleted");
    }

    public Memo getMemo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_MEMO, new String[]{ String.valueOf(id) });
        Memo m = null;
        if (cursor.moveToFirst()) {
            int newId = cursor.getInt(0);
            String memo = cursor.getString(1);
            cursor.close();
            m = new Memo(memo);
            m.setId(newId);

        }
        db.close();
        return m;
    }

    public String getAllMemos() {
        StringBuilder s = new StringBuilder();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_ALL_MEMOS, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                s.append(getMemo(id)).append("\n");
            }
            while (cursor.moveToNext());
        }
        db.close();
        return s.toString();
    }


    public List<Memo> getAllMemosAsList() {
        String query = "SELECT * FROM " + TABLE_MEMOS;
        List<Memo> allMemos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                int newId = cursor.getInt(0);
                String memo = cursor.getString(1);
                Log.i("MainActivity", newId + " " + memo + "  :  GET MEMO");
                allMemos.add( new Memo(memo,newId) );
            }
            while ( cursor.moveToNext() );
        }
        db.close();
        return allMemos;
    }
}
