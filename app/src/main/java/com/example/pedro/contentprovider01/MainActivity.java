package com.example.pedro.contentprovider01;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.pedro.contentprovider01.ClientesProvider.Clientes;

public class MainActivity extends AppCompatActivity {

    private Button btnInsertar;
    private Button btnConsultar;
    private Button btnEliminar;
    private Button btnLlamadas;
    private TextView txtResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencias a los controles
        txtResultados = (TextView)findViewById(R.id.TxtResultados);
        btnConsultar = (Button)findViewById(R.id.BtnConsultar);
        btnInsertar = (Button)findViewById(R.id.BtnInsertar);
        btnEliminar = (Button)findViewById(R.id.BtnEliminar);
        btnLlamadas = (Button)findViewById(R.id.BtnLlamadas);

        //······· CONSULTAR ·······
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] projection = new String[]{
                        Clientes._ID,
                        Clientes.COL_NOMBRE,
                        Clientes.COL_TELEFONO,
                        Clientes.COL_EMAIL
                };
                Uri clientesUri = ClientesProvider.CONTENT_URI;
                ContentResolver cr = getContentResolver();

                Cursor cursor = cr.query(clientesUri,
                        projection, //Columnas a devolver
                        null,       //Condición de la query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados

                //tendremos que recorrer el cursor para procesar los resultados
                if (cursor.moveToFirst()) {
                    String nombre;
                    String telefono;
                    String email;

                    int colNombre = cursor.getColumnIndex(Clientes.COL_NOMBRE);
                    int colTelefono = cursor.getColumnIndex(Clientes.COL_TELEFONO);
                    int colEmail = cursor.getColumnIndex(Clientes.COL_EMAIL);

                    txtResultados.setText("");
                    do {
                        nombre = cursor.getString(colNombre);
                        telefono = cursor.getString(colTelefono);
                        email = cursor.getString(colEmail);

                        txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");
                    } while (cursor.moveToNext());

                }


            }
        });

        //······· INSERTAR ·······
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(Clientes.COL_NOMBRE,"pedro");
                contentValues.put(Clientes.COL_TELEFONO,"38273823");
                contentValues.put(Clientes.COL_EMAIL,"demo@demo.com");

                ContentResolver cr = getContentResolver();
                cr.insert(ClientesProvider.CONTENT_URI,contentValues);


            }
        });

        //······  ELIMINAR ·······
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver cr = getContentResolver();

                cr.delete(ClientesProvider.CONTENT_URI,
                        Clientes.COL_NOMBRE + " = 'Cliente5'", null);
            }
        });


        //······  Mirar Llamadas del móvil ·······
        btnLlamadas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String[] projection = new String[] {
                        CallLog.Calls.TYPE,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.DURATION};

                Uri llamadasUri =  CallLog.Calls.CONTENT_URI;

                ContentResolver cr = getContentResolver();

                Cursor cur = cr.query(llamadasUri,
                        projection, //Columnas a devolver
                        null,       //Condición de la query
                        null,       //Argumentos variables de la query
                        null);      //Orden de los resultados

                if (cur.moveToFirst())
                {
                    int tipo;
                    String tipoLlamada = "";
                    String telefono;
                    String duracion;

                    int colTipo = cur.getColumnIndex(CallLog.Calls.TYPE);
                    int colTelefono = cur.getColumnIndex(CallLog.Calls.NUMBER);
                    int colDuracion = cur.getColumnIndex(CallLog.Calls.DURATION);

                    txtResultados.setText("");

                    do
                    {

                        tipo = cur.getInt(colTipo);
                        telefono = cur.getString(colTelefono);
                        duracion = cur.getString(colDuracion);

                        if(tipo == CallLog.Calls.INCOMING_TYPE)
                            tipoLlamada = "ENTRADA";
                        else if(tipo == CallLog.Calls.OUTGOING_TYPE)
                            tipoLlamada = "SALIDA";
                        else if(tipo == CallLog.Calls.MISSED_TYPE)
                            tipoLlamada = "PERDIDA";

                        txtResultados.append(tipoLlamada + " - " + telefono + " - " + duracion + "\n");

                    } while (cur.moveToNext());
                }
            }
        });
    }
}



