package com.example.sumeetjain.demo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class AddingContactActivity extends Activity
{
    int uniqueInteger = 1;
    AlarmSave objAlarmHolder = AlarmSave.getInstance();
    ArrayList<AlarmContract.Alarm> tempAlarmHolder = new ArrayList<AlarmContract.Alarm>();
    private AlarmListAdapater mAdapter;
    ContactDBHelper dbHelp;
    AlarmDBHelper dbHelper;
    AlarmSave model;
    private Context mContext;
    private Button but_Add;
    private Button btn_show;
    SQLiteDatabase db;
    static final int PICKCONTACTREQUEST = 1;
    public Button btnsetalarm;
    public static final int pick_contact = 1;
    public Button btn_add;
    private TextView txtContacts1;
    private TextView txtNumber1;

    private void pickContact()
    {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICKCONTACTREQUEST);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
         super.onCreate(savedInstanceState);

         requestWindowFeature(Window.FEATURE_ACTION_BAR);
         setContentView(R.layout.homepage);

          btn_show = (Button) findViewById(R.id.button_show);
          btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
              {
                Intent intent=new Intent(AddingContactActivity.this,displayActivity.class);
                startActivity(intent);
              }
           });

          mContext = this;
          txtContacts1 = (TextView) findViewById(R.id.display_name);
          txtNumber1 = (TextView) findViewById(R.id.display_number);
          btnsetalarm = (Button) findViewById(R.id.set_alarm);
          btnsetalarm.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v)
              {
                  Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                  startActivityForResult(intent, pick_contact);
              }
          });
    }

    public void startAlert(int rnd)
    {
        int i =  1;
        Bundle bundle = new Bundle();
        bundle.putInt("key", uniqueInteger);
        tempAlarmHolder.clear();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (i * 1000), getPendingIntent(bundle, uniqueInteger));
        Toast.makeText(this, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
    }

    private PendingIntent getPendingIntent(Bundle bundle, int rc)
    {
        Intent intent = new Intent(AddingContactActivity.this, AlarmManagerHelper.class);
        // send alarm id to broatcast
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(this, rc, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ArrayList<ContactStore> getAllContacts()
    {
        //  contactSave.getAllValues();
        int contactId;
        String contactName = "";
        ArrayList<ContactStore> contactList = new ArrayList<ContactStore>();
        String selectQuery = "SELECT  * FROM Contactdetails";
        dbHelp = new ContactDBHelper(this);
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                    contactId = Integer.parseInt(cursor.getString(0));
                    contactName = cursor.getString(1);
                    ContactStore contact=new ContactStore(contactId,contactName);
                    contactList.add(contact);
               }
            while (cursor.moveToNext());
        }
        return contactList;
    }

    public ContactStore saveContact(Intent data)
    {
        Uri contactData = data.getData();
        String contactName, contactId = null;
        Cursor cursor = getContentResolver().query(contactData, null, null, null, null);

        if (cursor.moveToFirst())
        {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            txtContacts1.setText(contactName);
            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
            {
                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext())
                {
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    txtNumber1.setText(phoneNumber);
                }
            }
            ContactStore contactStore = ContactStore.store(Integer.parseInt(contactId), contactName);
            ContactDBHelper contactSave = new ContactDBHelper(this);
            contactSave.insert(contactStore);
            return  contactStore;
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
                case (pick_contact):
                        if (resultCode == Activity.RESULT_OK)
                        {
                        ContactStore store = saveContact(data);
                        Intent intent1 = new Intent(this, AlarmDisplayActivity.class);
                        intent1.putExtra("ContactId", store.getId());
                        startActivity(intent1);
                         }
                break;
        }

        if (requestCode == PICKCONTACTREQUEST)
        {
                if (requestCode == RESULT_OK)
                {
                        Uri contactUri = data.getData();
                        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                        cursor.moveToFirst();
                        int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String number = cursor.getString(column);
                        mAdapter.notifyDataSetChanged();
                }
         }
     }

    public void setAlarmEnabled(long id, boolean isEnabled)
    {
            AlarmManagerHelper.cancelAlarms(this);
            AlarmSave model = dbHelper.getAlarm(id);
            model.isEnabled = isEnabled;
     //   dbHelper.updateAlarm(model);
            AlarmManagerHelper.setAlarms(this);
    }

    public void startAlarmDetailsActivity(long id)
    {
            Intent intent = new Intent(this, AlarmDetailsActivity.class);
            intent.putExtra("id", id);
            startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
             return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
        //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings)
            {
            return true;
            }
        return super.onOptionsItemSelected(item);
    }
}




