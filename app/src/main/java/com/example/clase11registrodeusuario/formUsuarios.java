package com.example.clase11registrodeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class formUsuarios extends AppCompatActivity {
    TextInputEditText txtNombre, txtUsuario, txtClave, txtConfirmacion;
    Button btnAgregarUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_usuarios);

        txtNombre = findViewById(R.id.txtNombre);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave = findViewById(R.id.txtClave);
        txtConfirmacion = findViewById(R.id.txtConfirmacion);
        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);

        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamada a el método para insertar
                insertarUsuario();
            }
        });
    }

    private void insertarUsuario() {
        String nombre = txtNombre.getText().toString().trim();
        String usuario = txtUsuario.getText().toString().trim();
        String clave = txtClave.getText().toString().trim();
        String confirmacion = txtConfirmacion.getText().toString().trim();
        //Implementando un ProgressDialog para ver errores
        ProgressDialog progressDialog = new ProgressDialog(this);
        //Validar cada elemento que no se encuentre vacio
        if(nombre.isEmpty())
            txtNombre.setError("Es obligatorio");
        else if(usuario.isEmpty())
            txtUsuario.setError("Es obligatorio");
        else if(clave.isEmpty())
            txtClave.setError("Es obligatorio");
        else if(!clave.equals(confirmacion))
            txtConfirmacion.setError("Las claves no coinciden");
        else{
            progressDialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    "http://192.168.0.6/webservices3/agregarUsuarios.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Registro almacenado correctamente")) {
                                Toast toast = Toast.makeText(
                                        getApplicationContext(),
                                        "Registro almacenado correctamente",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                                progressDialog.dismiss();
                            } else {
                                Toast toast = Toast.makeText(
                                        formUsuarios.this,
                                        response,
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast toast = Toast.makeText(
                            formUsuarios.this,
                            error.getMessage(),
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Cargar datos referenciados desde la base de datos
                    Map<String,String>params = new HashMap<String,String>();
                    params.put("txtNombre",nombre);
                    params.put("txtUsuario",usuario);
                    params.put("txtClave",clave);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(formUsuarios.this);
            requestQueue.add(request);
        }
    }
}