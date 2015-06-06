package com.example.orient_me.contacts;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.RawContacts;
import android.widget.Toast;

public class ContactsProviderHelper {

	ArrayList<ContentProviderOperation> ops;
	String name, number, company, title, email;
	int rawId;

	ContactsProviderHelper() {
		initializeContactsContentProvider();
	}
	
	ContactsProviderHelper(Contact contact){
		this.name = contact.getName();
		this.number = contact.getNumber();
		this.company = contact.getCompany();
		this.title = contact.getTitle();
		this.email = contact.getEmail();
		initializeContactsContentProvider();
	}

	public ArrayList<ContentProviderOperation> getOps() {
		return ops;
	}

	public void setOps(ArrayList<ContentProviderOperation> ops) {
		this.ops = ops;
	}

	public int getRawId() {
		return rawId;
	}

	public void setRawId() {
		this.rawId = ops.size();
	}

	// Mathew, G. (2012) Adding Contacts Programmatically using Contacts Provider in Android - Example. [Online]. Available from: http://wptrafficanalyzer.in/blog/adding-contacts-programatically-using-contacts-provider-in-android-example/ [Accessed: 5 May 2015].
	public void insertDataToContactsContractTable(Context context) {

		// Insert display name in the table ContactsContract.Data
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,
						rawId)
				.withValue(ContactsContract.Data.MIMETYPE,
						StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.DISPLAY_NAME, name).build());

		// Insert number in table ContactsContract.Data
	     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	             .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	             .withValue(ContactsContract.Data.MIMETYPE,
	         ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
	             .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
	             .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
	         ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
	             .build());
		
		// Insert company & title in table ContactsContract.Data
	     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
	             .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
	             .withValue(ContactsContract.Data.MIMETYPE,
	         ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
	             .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
	             .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
	             .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, title)
	             .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
	             .build());
	     
		// Insert email in table ContactsContract.Data
	     if (!"0".equals(email)){
		     ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
		         .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
		         .withValue(ContactsContract.Data.MIMETYPE,
		     ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
		         .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
		         .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
		         .build());
	     }
	}
	
	public int applyBatchInsertOperations(Context context){
		int error = 0;
		try {
			// Executing all the insert operations as a single database
			// transaction
			context.getContentResolver().applyBatch(ContactsContract.AUTHORITY,
					ops);
			Toast.makeText(context,
					"Contact is successfully added", Toast.LENGTH_SHORT)
					.show();
		} catch (RemoteException e) {
			e.printStackTrace();
			error = 1;
			return error;
		} catch (OperationApplicationException e) {
			e.printStackTrace();
			error = 1;
			return error;
		}
		return error;
	}
	
	private void initializeContactsContentProvider(){
		ops = new ArrayList<ContentProviderOperation>();
		setRawId();
		// Adding insert operation to operations list
		// to insert a new raw contact in the table
		// ContactsContract.RawContacts
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null).build());
	}
}
