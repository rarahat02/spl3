package com.afollestad.materialdialogssample;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by iit on 10/5/17.
 */

public class FirebaseInitialOperation
{


    private static DatabaseReference mFirebaseDatabase;
    private static FirebaseDatabase mFirebaseInstance;

    public static void createCustomer(String id, String name, String phoneNumber, int purchasedSongCount, String IMEI)
    {

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Customer");

        Customers customer = new Customers(id, name, phoneNumber, purchasedSongCount, IMEI);

        mFirebaseDatabase.child(id).setValue(customer);

        addcustomerChangeListener(id);
    }


    private static void addcustomerChangeListener(String id) {
        // customer data change listener
        mFirebaseDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Customers customer = dataSnapshot.getValue(Customers.class);

                if (customer == null) {
                    //Log.e(TAG, "customer data is null!");
                    return;
                }
                customer.setId("asas");
                customer.setName("sds");

            }
            @Override
            public void onCancelled(DatabaseError error) {

                //Log.e(TAG, "Failed to read customer", error.toException());
            }
        });
    }

    private static void updatecustomer(String customerId, String name, String phoneNumber, int purchasedSongCount)
    {

        //if (!TextUtils.isEmpty(name))
        mFirebaseDatabase.child(customerId).child("name").setValue(name);

        //if (!TextUtils.isEmpty(id))
        mFirebaseDatabase.child(customerId).child("id").setValue(customerId);
        //if (!TextUtils.isEmpty(phoneNumber))
        mFirebaseDatabase.child(customerId).child("phoneNumber").setValue(phoneNumber);

        mFirebaseDatabase.child(customerId).child("purchasedSongCount").setValue(purchasedSongCount);
    }
}
