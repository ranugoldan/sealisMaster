package com.kelompok4.sealis.sealis;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextEmail;

    private Button buttonRegister;

    private static final String REGISTER_URL = "http://192.168.43.36/sealis/public/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String nama = editTextName.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        if (!isValidEmail(email)){
            Toast.makeText(getApplicationContext(),"E-mail tidak valid",Toast.LENGTH_LONG).show();
        } else if (nama.length()<=4){
            Toast.makeText(getApplicationContext(),"Nama harus lebih dari 4 karakter",Toast.LENGTH_LONG).show();
        } else if (password.length()<6 || password.length()>20){
            Toast.makeText(getApplicationContext(),"Password harus terdiri dari 6-20",Toast.LENGTH_LONG).show();
        } else {
            register(email,nama,password);
        }
    }

    private void register(String email, String nama, String password) {
        String urlSuffix = "?nama="+nama+"&email="+email+"&password="+password;
        class RegisterUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL+s);
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

        RegisterUser ru = new RegisterUser();
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
