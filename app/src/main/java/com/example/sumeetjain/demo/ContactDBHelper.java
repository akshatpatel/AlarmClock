package com.example.sumeetjain.demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sumeetjain on 19/03/15.
 */
public class ContactDBHelper extends SQLiteOpenHelper
{
        static final String dbName = "DemoDB";
        static final String Contacttable = "Contactdetails";

        private static final int DATABASE_VERSION = 1;
        public static final String P_id = "id";
        private static final String KEY_ROWID = "ContactId";

        static final String KEY_NAME = "Contact_Name";
        private static final String DATABASE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + "ContactDetails" + "(" + P_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ROWID + " INTEGER, " + KEY_NAME + " TEXT )";
        ContactDBHelper(Context context) {
        super(context, Contacttable, null, DATABASE_VERSION);

}
        @Override
        public void onCreate(SQLiteDatabase db)
        {
                db.execSQL(DATABASE_TABLE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
                db.execSQL("DROP TABLE IF EXISTS" + Contacttable);
                onCreate(db);
        }
    //function to insert values
        public Boolean insert(ContactStore con_store)
        {
                boolean createSuccessful = false;
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_ROWID, con_store.getContactId());
                values.put(KEY_NAME, con_store.getName(KEY_NAME));
                createSuccessful = db.insert(Contacttable, null, values) > 0;
                db.close();
                return createSuccessful;
        }
    //function to update
        public int UpdateContact(ContactStore contact)
        {
                SQLiteDatabase db = this.getReadableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(P_id, contact.getId());
                cv.put(KEY_ROWID, contact.getContactId());
                cv.put(KEY_NAME, contact.getName(KEY_NAME));
                return db.update(dbName, cv, KEY_ROWID + "=?", new String[]{String.valueOf(contact.getId())});
        }

        public void delete(ContactStore contact)
        {
                SQLiteDatabase db = this.getWritableDatabase();
                db.delete(dbName, KEY_ROWID + "?", new String[]{
                String.valueOf(contact.getId())});
                 db.close();
         }

        public Cursor getAllValues()
        {
                SQLiteDatabase db = this.getReadableDatabase();
                String[] projection = {KEY_ROWID, KEY_NAME};
                Cursor cur = db.rawQuery("SELECT" + KEY_ROWID + KEY_NAME + "from" + dbName, new String[0]);
                cur.moveToFirst();
                while (cur.isAfterLast())
                {
                        String name = cur.getString(1);
                        int number = cur.getInt(2);
                        cur.moveToNext();
                }
           return cur;
        }
    }

