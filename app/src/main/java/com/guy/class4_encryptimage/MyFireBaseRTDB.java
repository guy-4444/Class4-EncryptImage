package com.guy.class4_encryptimage;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyFireBaseRTDB {

    public interface CallBack_StringReturn {
        void stringReady(String val);
    }

    public static void postPicToServer(String value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_pic");

        myRef.setValue(value);
    }

    public static void getPicFromServer(final CallBack_StringReturn callBack_stringReturn) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("my_pic");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                callBack_stringReturn.stringReady(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        myRef.addListenerForSingleValueEvent(valueEventListener);
        //myRef.removeEventListener(valueEventListener);
    }
}