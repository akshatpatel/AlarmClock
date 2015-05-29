package com.example.sumeetjain.demo;

/**
 * Created by sumeetjain on 19/03/15.
 */
public class ContactStore
{
        int id;
        int contactId;
        String contactName;
        public ContactStore(int contactid, String contactname)
        {
                this.contactName = contactname;
                this.contactId = contactid;
        }
        public static ContactStore store(int contactId, String name)
        {
                return new ContactStore(contactId, name);
        }
        public void setId(int contactId)
        {
                this.contactId = contactId;
        }
        public int getId()
        {
                return id;
        }
        public int getContactId()
        {
                return contactId;
        }
        public void setContactId(int contactId)
        {
                this.contactId = contactId;
        }
        public String getName(String name)
        {
                return contactName;
        }
        public void setName(String name)
        {
                this.contactName = name;
        }
}
