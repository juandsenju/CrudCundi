package com.example.crudcundi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName, etPassword, etEmail, etPhone, etId;
    Button btnCreate, btnFetch;

    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.0.4/android/save.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    requestQueue = Volley.newRequestQueue(this);

    // UI
    initUI();

    btnCreate.setOnClickListener(this);
    btnFetch.setOnClickListener(this);
}

    private void initUI(){

        //EditText
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etId = findViewById(R.id.etId);

        //Button
        btnCreate = findViewById(R.id.btnCreate);
        btnFetch = findViewById(R.id.btnFetch);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnCreate){

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            createUser(name, email, password, phone);

        } else if (id == R.id.btnFetch){
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("id", etId.getText().toString().trim());
            startActivity(intent);
        }
    }
    private void createUser(final String name, final String email, final String password, final String phone) {

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Usuario creado Satisfactoriamente.", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}