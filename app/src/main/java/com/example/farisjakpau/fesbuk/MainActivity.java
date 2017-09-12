package com.example.farisjakpau.fesbuk;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farisjakpau.fesbuk.Adapter.TextAdapter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private TextAdapter adapter;
    private FirebaseAuth mAuth;
    LoginButton loginButton;
    Button but;
    private ListView lv;
    private FirebaseAuth.AuthStateListener mAuthListener;
    CallbackManager mCallbackManager;
    TextView tv;
    AccessToken acc = AccessToken.getCurrentAccessToken();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
       // but = (Button)findViewById(R.id.button_facebook_signout);
        tv =(TextView)findViewById(R.id.tv);



        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.farisjakpau.fesbuk",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        loginButton.setReadPermissions("email","public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
              //  fesbukStat(loginResult.getAccessToken());
                loginButton.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

      /*  but.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        }); */




        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                 updateUI(user);
                } else {
                    // User is signed out
                   updateUI(null);
                }
                // ...
            }
        };
    }




    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential dredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(dredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(MainActivity.this,DisplayPage.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);


                           // updateUI(user);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Failed Auth",Toast.LENGTH_LONG).show();
                           // updateUI(null);
                        }
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
           // loginButton.setVisibility(View.INVISIBLE);
           // updateUI(currentUser);
          //  lv.setVisibility(View.VISIBLE);
           // fesbukStat(acc);
            Intent i = new Intent(MainActivity.this,DisplayPage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        else {
            loginButton.setVisibility(View.VISIBLE);
            updateUI(null);
        }

    }

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        loginButton.setVisibility(View.VISIBLE);
       // lv.setVisibility(View.INVISIBLE);
        lv.setEmptyView(findViewById(R.id.emptyElement));
        updateUI(null);
    }


    private void updateUI(FirebaseUser currentUser) {

        if(currentUser != null){
            //lv.setVisibility(View.VISIBLE);
         //   fesbukStat(acc);

        }

        else {
           // lv.setAdapter(null);
           // tv.setText("Sign Out");
          //  lv.setEmptyView(findViewById(R.id.emptyElement));
          //  lv.setAdapter(null);

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
