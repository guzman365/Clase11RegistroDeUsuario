package com.example.clase11registrodeusuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class activity_mantenimiento_usuarios extends AppCompatActivity {
    Button
            btnBuscar, btnModificar, btnEliminar;
    TextInputEditText
            txtUsuarioId, txtNombre, txtUsuario, txtClave, txtConfirmacion;
    RequestQueue
            requestQueue;

    public static final String url ="http://192.168.0.6/webservices3/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_usuarios);

        txtUsuarioId = findViewById(R.id.txtUsuarioId);
        txtNombre = findViewById(R.id.txtNombre);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtClave = findViewById(R.id.txtClave);;
        txtConfirmacion = findViewById(R.id.txtConfirmacion);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);

        //Peticiones
        requestQueue = Volley.newRequestQueue(this);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuario();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarUsuarios();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUsuario();
            }
        });
    }

    private void eliminarUsuario() {
        //Crear variables locales
        String id = txtUsuarioId.getText().toString().trim();
        String URL = url + "eliminarUsuarios.php";
        ProgressDialog progressDialog = new ProgressDialog(this);
        //Proceder con la peticion al servidor
        if (validarId()) {
            //Implementando un AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmacion de eliminacion");
            builder.setMessage("Desea eliminar el registro en pantalla?");
            builder.setPositiveButton("ACEPTAR", (dialog, which) -> {
                //Iniciando el medido de progreso
                progressDialog.show();
                //Iniciando la peticion al servidor
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Evaluar la respuesta del servidor
                                if (response.equalsIgnoreCase("Registro eliminado correctamente")) {
                                    //Mostrando mensaje de confirmacion
                                    Toast toast = Toast.makeText(
                                            getApplicationContext(),
                                            response,
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                    //Limpiar progreso
                                    progressDialog.dismiss();
                                    limpiarObjetos();
                                } else {
                                    //Mostrando mensaje de error
                                    Toast toast = Toast.makeText(
                                            getApplicationContext(),
                                            response,
                                            Toast.LENGTH_LONG);
                                    toast.show();
                                    //Limpiar progreso
                                    progressDialog.dismiss();
                                }//Fin de IF
                            }//Fin de onResponse
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Mostrando mensaje de confirmacion
                        Toast toast = Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG);
                        toast.show();
                        //Limpiar progreso
                        progressDialog.dismiss();
                    }
                }) {
                    //Seccion de objetos a enviar por POST
                    protected Map<String, String> getParams() throws AuthFailureError {//Cargar los datos de referencia
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("txtUsuarioId", id);
                        return params;
                    }//Fin de Map
                };
                RequestQueue requestQueue = Volley.newRequestQueue(activity_mantenimiento_usuarios.this);
                requestQueue.add(request);
            });//Fin de SetPositiveButton
            builder.setNegativeButton("CANCELAR", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void modificarUsuarios() {
        String id = txtUsuarioId.getText().toString().trim();
        String nombre = txtNombre.getText().toString().trim();
        String usuario = txtUsuario.getText().toString().trim();
        String clave = txtClave.getText().toString().trim();
        String confirmacion = txtConfirmacion.getText().toString().trim();
        String URL = url+"modificarUsuarios.php";

        ProgressDialog progressDialog = new ProgressDialog(this);
        //Proceder con la peticion al servidor
        if(validarCampos()){
            //Iniciando el medido de progreso
            progressDialog.show();
            //Iniciando la peticion al servidor
            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Evaluar la respuesta del servidor
                            if (response.equalsIgnoreCase("Registro modificado correctamente")) {
                                //Mostrando mensaje de confirmacion
                                Toast toast = Toast.makeText(
                                        getApplicationContext(),
                                        response,
                                        Toast.LENGTH_LONG);
                                toast.show();
                                //Limpiar progreso
                                progressDialog.dismiss();
                            } else {
                                //Mostrando mensaje de error
                                Toast toast = Toast.makeText(
                                        getApplicationContext(),
                                        response,
                                        Toast.LENGTH_LONG);
                                toast.show();
                                //Limpiar progreso
                                progressDialog.dismiss();
                            }//Fin de IF
                        }//Fin de onResponse
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Mostrando mensaje de confirmacion
                    Toast toast = Toast.makeText(
                            getApplicationContext(),
                            error.getMessage(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    //Limpiar progreso
                    progressDialog.dismiss();
                }
            }){
                //Seccion de objetos a enviar por POST
                protected Map<String, String> getParams() throws AuthFailureError {
                    //Cargar los datos de referencia
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("txtUsuarioId",id);
                    params.put("txtNombre",nombre);
                    params.put("txtUsuario",usuario);
                    params.put("txtClave",clave);
                    return params;
                }//Fin de Map
            };
            RequestQueue requestQueue = Volley.newRequestQueue(activity_mantenimiento_usuarios.this);
            requestQueue.add(request);
        }
    }

    private void buscarUsuario() {
        String idUser = txtUsuarioId.getText().toString().trim();
        //Definir la URL del servicio
        String URL = url+"mostrarUsuarios.php?txtUsuarioId="+idUser;
        //Iniciar peticiones
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response);
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            for(int i = 0; i<jsonArray.length();i++){
                                //Asignando valores formateados en JSON
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                txtNombre.setText(jObject.getString("nombre"));
                                txtUsuario.setText(jObject.getString("usuario"));
                                txtClave.setText(jObject.getString("clave"));
                                txtConfirmacion.setText(jObject.getString("clave"));
                            }//Fin de for
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("Error en cath", e.toString());
                        }//Fin de cath
                    }//Fin de onResponse
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error en response", error.toString());
            }
        });
        requestQueue = Volley.newRequestQueue(activity_mantenimiento_usuarios.this);
        requestQueue.add(stringRequest);
    }

    private boolean validarCampos() {
        //Declarar variable local
        boolean correcto = true;//Por defecto todo esta bien
        if(txtUsuarioId.getText().toString().trim().isEmpty()){
            txtUsuarioId.setError("Es obligatorio");
            correcto = false;//Especificamos que hay un error
        }else if(txtNombre.getText().toString().trim().isEmpty()){
            txtNombre.setError("Es obligatorio");
            correcto = false;//Especificamos que hay un error
        }else if(txtUsuario.getText().toString().trim().isEmpty()){
            txtUsuario.setError("Es obligatorio");
            correcto = false;//Especificamos que hay un error
        }else if(txtClave.getText().toString().trim().isEmpty()){
            txtClave.setError("Es obligatorio");
            correcto = false;//Especificamos que hay un error
        }else if(!txtClave.getText().toString().trim().equals(txtConfirmacion.getText().toString().trim())){
            txtConfirmacion.setError("Es obligatorio");
            correcto = false;//Especificamos que hay un error
        }
        return correcto;
    }

    private boolean validarId(){
        boolean correcto = true;//Por defecto todo esta bien
        if(txtUsuarioId.getText().toString().trim().isEmpty()){
            txtUsuarioId.setError("Es obligatorio");
            correcto = false;//Especificamos que hay un error
        }
        return correcto;
   }
    //Usado para limpiar objetos de Activity
    private void limpiarObjetos(){
        txtUsuarioId.setText("");
        txtNombre.setText("");
        txtUsuario.setText("");
        txtClave.setText("");
        txtConfirmacion.setText("");
    }
}