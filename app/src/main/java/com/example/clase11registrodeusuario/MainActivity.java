package com.example.clase11registrodeusuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnInsertar, btnMantenimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsertar = findViewById(R.id.btnInsertar);
        btnMantenimiento = findViewById(R.id.btnMantenimiento);

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Referenciando a la activity para formulario por medio de un intent
                Intent frm = new Intent(MainActivity.this,formUsuarios.class);
                //Cargar la activity del formulario
                startActivity(frm);
            }
        });

        btnMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Referenciando a la activity para formulario por medio de un intent
                Intent mttofrm = new Intent(MainActivity.this,activity_mantenimiento_usuarios.class);
                //Cargar la activity del formulario
                startActivity(mttofrm);
            }
        });
    }
}