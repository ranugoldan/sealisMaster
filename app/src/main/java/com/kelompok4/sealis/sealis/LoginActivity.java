package com.kelompok4.sealis.sealis;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private EditText editTextPassword;
    private EditText editTextEmail;

    private Button buttonRegister, buttonLogin;

    private static final String LOGIN_URL = "http://192.168.43.36/sealis/public/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(reg);
        } else if (v == buttonLogin){
            loginUser();
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        if (!isValidEmail(email)){
            Toast.makeText(getApplicationContext(), "E-mail tidak valid", Toast.LENGTH_LONG).show();
        } else if (password.length()<6 || password.length()>20){
            Toast.makeText(getApplicationContext(),"Password harus terdiri dari 6-20",Toast.LENGTH_LONG).show();
        } else {
            login(email, password);
        }
    }

    private void login(String email, String password) {
        String urlSuffix = "?email="+email+"&password="+password;
        class LoginUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (!s.equals("1")){
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                } else {
                    Intent loggedin = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(loggedin);
                    finish();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(LOGIN_URL+s);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                }catch(Exception e){
                    return null;
                }
            }
        }

        LoginUser ru = new LoginUser();
        ru.execute(urlSuffix);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}

