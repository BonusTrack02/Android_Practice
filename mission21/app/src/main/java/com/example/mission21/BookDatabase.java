package com.example.mission21;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDatabase {
    private static final String TAG = "BookDatabase";

    private static BookDatabase database;

    public static String DATABASE_NAME = "book.db";

    public static String TABLE_BOOK_INFO = "BOOK_INFO";

    public static int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;

    private Context context;

    private BookDatabase(Context context) {
        this.context = context;
    }

    public static BookDatabase getInstance(Context context) {
        if (database == null) {
            database = new BookDatabase(context);
        }

        return database;
    }

    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }
        return c1;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executeQuery" ,ex);
            return false;
        }

        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion < 2) {
            }
        }

        private void insertRecord(SQLiteDatabase _db, String name, String author, String contents) {
            try {
                _db.execSQL("insert into " + TABLE_BOOK_INFO + "(NAME, AUTHOR, CONTENTS) values ('" + name + "', '" + author + "', '" + contents + "');");
            } catch (Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            println("creating table [" + TABLE_BOOK_INFO + "].");

            String DROP_SQL = "drop table if exists " + TABLE_BOOK_INFO;
            try {
            } catch (Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            String CREATE_SQL = "create table " + TABLE_BOOK_INFO + "(" + " _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + " NAME TEXT, " + " AUTHOR TEXT, " + " CONTENTS TEXT, " + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP " + ")";

            try {
                _db.execSQL(CREATE_SQL);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

            insertRecord(_db, "Do it ??????????????? ??? ???????????????", "?????????", "??????????????? ???????????? ????????????????????? ??????????????? ??????");
            insertRecord(_db, "Programming Android", "Mednieks, Zigurd", "Oreilly Associates Inc?????? ??????");
            insertRecord(_db, "????????? ???????????????", "?????????, ????????? ??????", "???????????????????????? ??????");
            insertRecord(_db, "???????????????! ??????????????? ?????? ???????????????", "????????? ????????? ???", "?????????????????? ??????");
            insertRecord(_db, "??????! ??????????????? ????????? ??????????????? ????????????", "?????????, ????????? ??????", "DW Wave?????? ??????");
       }
    }

    public void insertRecord(String name, String author, String contents) {
        try {
            db.execSQL("insert into " + TABLE_BOOK_INFO + "(NAME, AUTHOR, CONTENTS) values ('" + name + "', '" + author + "', '" + contents + "');");
        } catch (Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }
}
