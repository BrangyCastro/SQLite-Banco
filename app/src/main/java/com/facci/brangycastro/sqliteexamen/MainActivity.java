package com.facci.brangycastro.sqliteexamen;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    HerperBD bd;
    TextView lista;
    Button botonCrear,botonModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = new HerperBD(getApplicationContext(), "bd", null, 1);

        lista = (TextView) findViewById(R.id.lblDatos);
        llenarLista();

        botonCrear = (Button)findViewById(R.id.btnCrear);
        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dlgCrearBanco = new Dialog(MainActivity.this);
                dlgCrearBanco.setContentView(R.layout.dlg_crear);
                Button botonGuardar = (Button)dlgCrearBanco.findViewById(R.id.btnGuardarPersona);
                final EditText cajaCedula = (EditText)dlgCrearBanco.findViewById(R.id.txtCedulaCrear);
                final EditText cajaNombre = (EditText)dlgCrearBanco.findViewById(R.id.txtNombreCrear);
                final EditText cajaSaldo = (EditText)dlgCrearBanco.findViewById(R.id.txtSaldoCrear);
                botonGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bd.insertar(cajaCedula.getText().toString(),
                                cajaNombre.getText().toString(),
                                cajaSaldo.getText().toString());
                        llenarLista();
                        dlgCrearBanco.hide();

                    }
                });
                dlgCrearBanco.show();

            }
        });

        botonModificar = (Button) findViewById(R.id.btnAcion);
        botonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dlgModificar = new Dialog(MainActivity.this);
                dlgModificar.setContentView(R.layout.dlg_modificar);
                final EditText cajaCedula = (EditText)dlgModificar.findViewById(R.id.txtCedulaModificar);
                final EditText cajaSaldoNuevo = (EditText)dlgModificar.findViewById(R.id.txtSaldoNuevo);

                Button retiro = (Button)dlgModificar.findViewById(R.id.btnRetiro);
                Button deposito = (Button)dlgModificar.findViewById(R.id.btnDeposito);

                retiro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      double saldoActual = Double.valueOf(bd.leercedula(cajaCedula.getText().toString()));
                      double saldoNuevo = Double.valueOf(cajaSaldoNuevo.getText().toString());

                      double total = saldoActual - saldoNuevo;
                      String resultado = String.valueOf(total);

                      bd.modificar(cajaCedula.getText().toString(),resultado);
                      llenarLista();
                      dlgModificar.hide();
                      Toast.makeText(MainActivity.this, "Retiro Exitosa", Toast.LENGTH_SHORT).show();
                    }
                });

                deposito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double saldoActual = Double.valueOf(bd.leercedula(cajaCedula.getText().toString()));
                        double saldoNuevo = Double.valueOf(cajaSaldoNuevo.getText().toString());

                        double total = saldoActual + saldoNuevo;
                        String resultado = String.valueOf(total);

                        bd.modificar(cajaCedula.getText().toString(),resultado);
                        llenarLista();
                        dlgModificar.hide();
                        Toast.makeText(MainActivity.this, "Deposito Exitosa", Toast.LENGTH_SHORT).show();
                    }
                });

                dlgModificar.show();
            }
        });

    }

    public void llenarLista(){
        lista.setText(bd.leerTodo());
    }
}
