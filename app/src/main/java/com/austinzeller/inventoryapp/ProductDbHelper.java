package com.austinzeller.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

class ProductDbHelper extends SQLiteOpenHelper {

    //finds errors in this file
    private String DB_LOG_TAG = "DATABASE_HELPER";

    ProductDbHelper(Context context) {
        super(context, itemSQLContract.TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase itemDb) {
        //the ID will auto increment
        itemDb.execSQL("create table " + itemSQLContract.TABLE_NAME + " (_ID INTEGER PRIMARY KEY "
                + "AUTOINCREMENT, ITEM TEXT, PRICE INTEGER, QUANTITY INTEGER, " +
                "IMAGE TEXT, SOLD INTEGER)");
        Log.v(DB_LOG_TAG, "the table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase itemDb, int i, int i1) {
        //when an upgrade is needed, the table is deleted
        deleteAllData(itemDb);
        //when an upgrade is needed, the table is dropped if still there
        itemDb.execSQL("DROP TABLE IF EXISTS " + itemSQLContract.TABLE_NAME);
        onCreate(itemDb);
        Log.v(DB_LOG_TAG, "the onUpgrade method has been called and the table has been dropped " +
                "and recreated");
    }

    boolean insertData(String item, Integer price, Integer quantity, String image, Integer
            sold) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(DB_LOG_TAG, "database is set to get Writable");

        //Content values take in values to be set
        ContentValues contentValues = new ContentValues();
        contentValues.put(itemSQLContract.COL_1, item);
        contentValues.put(itemSQLContract.COL_2, price);
        contentValues.put(itemSQLContract.COL_3, quantity);
        contentValues.put(itemSQLContract.COL_4, image);
        contentValues.put(itemSQLContract.COL_5, sold);

        /*the insert function takes the table name and the content values and sets it to the
        table, in order to check that the function was successful it must not return -1*/
        long result = db.insert(itemSQLContract.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    Cursor readAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        /*a cursor object allows for random read and write, this code allows us to store all of the
         table data into the cursor res and return it to where the method was called to be shown*/
        return db.query(itemSQLContract.TABLE_NAME, new String[]{itemSQLContract._ID,
                itemSQLContract.COL_1, itemSQLContract.COL_2, itemSQLContract.COL_3,
                itemSQLContract.COL_4, itemSQLContract.COL_5}, null, null, null, null, null);
    }

    Boolean updateData(String id, String item, Integer price, Integer quantity, String image,
                       Integer sold) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(itemSQLContract._ID, id);
        contentValues.put(itemSQLContract.COL_1, item);
        contentValues.put(itemSQLContract.COL_2, price);
        contentValues.put(itemSQLContract.COL_3, quantity);
        contentValues.put(itemSQLContract.COL_4, image);
        contentValues.put(itemSQLContract.COL_5, sold);

        db.update(itemSQLContract.TABLE_NAME, contentValues, "_ID = ?", new String[]{id});
        return true;
    }

    Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(itemSQLContract.TABLE_NAME, "_ID = ?", new String[]{id});
    }

    private Integer deleteAllData(SQLiteDatabase db) {
        db = this.getWritableDatabase();

        //Deletes all data in table
        return db.delete(itemSQLContract.TABLE_NAME, "select * from " +
                itemSQLContract.TABLE_NAME, new String[]{"0"});
    }

    public Cursor readData(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(itemSQLContract.TABLE_NAME, new String[]{itemSQLContract._ID,
                        itemSQLContract.COL_1,
                        itemSQLContract.COL_2, itemSQLContract.COL_3, itemSQLContract.COL_4,
                        itemSQLContract.COL_5}, itemSQLContract._ID + " like" + "'%" + id + "%'", null,
                null, null, null);
    }

    static abstract class itemSQLContract implements BaseColumns {

        static final String TABLE_NAME = "inventory";
        static final String COL_1 = "item";
        static final String COL_2 = "price";
        static final String COL_3 = "quantity";
        static final String COL_4 = "image";
        static final String COL_5 = "sold";
    }
}