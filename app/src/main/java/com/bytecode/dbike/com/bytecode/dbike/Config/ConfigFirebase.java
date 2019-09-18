package com.bytecode.dbike.com.bytecode.dbike.Config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigFirebase {
    private static FirebaseAuth autentification;

    public static FirebaseAuth getFirebaseAutentification(){
        if(autentification == null){
            autentification = FirebaseAuth.getInstance();
        }
        return autentification;
    }

}
