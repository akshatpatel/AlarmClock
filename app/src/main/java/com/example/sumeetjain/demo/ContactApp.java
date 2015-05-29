package com.example.sumeetjain.demo;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.widget.TextView;

/**
 * Created by sumeetjain on 19/03/15.
 */
public class ContactApp extends ListActivity
{
        private static final int CONTACTCREATE = 0;
        private static final int CONTACTEDIT = 1;
        private static final int INSERTID = Menu.FIRST;
        private static final int DELETEID = Menu.FIRST + 1;
        public ContactDBHelper dBhelp;
        private Cursor c;
        private SQLiteDatabase db;
        private TextView txtname;
        private TextView txtnumber;
        public void onCreate(Bundle icilie) {
        super.onCreate(icilie);
        setContentView(R.layout.listofalarms);
        dBhelp = new ContactDBHelper(this);

        txtname = (TextView) findViewById(R.id.text_name);
        txtnumber = (TextView) findViewById(R.id.text_number);
}

 public Cursor fetchAllRows(long rowID)
 {
            Uri uri;
            String[] projection;
            uri = ContactsContract.Data.CONTENT_URI;
            projection = new String[]
                    {
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                            ContactsContract.Contacts.HAS_PHONE_NUMBER,
                            ContactsContract.CommonDataKinds.Phone.NUMBER

                    };
            return db.query(dBhelp.dbName, projection, null, null, null, null, null);
  }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data)
        {
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == RESULT_OK)
                {
                        Uri contactData = data.getData();
                        Cursor cursor = getContentResolver().query(contactData, null, null, null, null);

                if (cursor.moveToFirst())
                {
                        String name = cursor.getString(cursor.getColumnIndex(ContactDBHelper.KEY_NAME));
                        txtname.setText(name);
                }

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext())
                        {
                                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                txtnumber.setText(phoneNumber);
                        }
                }
        }
  }
}
