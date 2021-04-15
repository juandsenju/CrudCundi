package com.example.crudcundi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    EditText etName, etPassword, etEmail, etPhone, etId;
    Button btnDelete, btnEdit;
    String id;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    requestQueue = Volley.newRequestQueue(this);

     Bundle extras = getIntent().getExtras();
     if(extras!=null){
         id = extras.getString("id");
     }


    // UI
     initUI();

     readUser();

    btnDelete.setOnClickListener(this);
    btnEdit.setOnClickListener(this);
}

    private void initUI(){

        //EditText
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etId = findViewById(R.id.etId);

        //Button
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void readUser(){
        String URL = "http://192.168.0.4/android/fetch.php?id=" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        String name, email, password, phone;
                        try {
                            name = response.getString("nombre");
                            email = response.getString("email");
                            password = response.getString("password");
                            phone = response.getString("telefono");

                            etName.setText(name);
                            etEmail.setText(email);
                            etPassword.setText(password);
                            etPhone.setText(phone);

                        } catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnEdit){

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            updateUser(name, email, password, phone);


        } else if (id == R.id.btnDelete){
            String idUser = etId.getText().toString().trim();

            removeUser(idUser);
        }
    }

    private void updateUser(final String name, final String email, final String password, final String phone) {
        String URL = "http://192.168.0.4/android/edit.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity2.this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);

                return params;
            }

        };

        requestQueue.add(stringRequest);
    }

    public void removeUser(final String idUser){
        String URL = "http://192.168.0.4/android/delete.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("id", idUser);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}