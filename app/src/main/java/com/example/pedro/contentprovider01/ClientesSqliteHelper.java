package com.example.pedro.contentprovider01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pedro on 03/10/2017.
 */

public class ClientesSqliteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Clientes

    String sqlCreate = "CREATE TABLE Clientes " +
            "(_Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "telefono TEXT, " +
            "email TEXT)";


    public ClientesSqliteHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //crear DB
        sqLiteDatabase.execSQL(sqlCreate);

        //insertar 5 clientes de ejemplo
        for(int i=1; i<=5; i++) {
            //generar datos
            String nombre = "Cliente" + i;
            String telefono = "93375542" + i;
            String email = "mail" + i + "@mail.com";

            //insertamos los datos en la tabla clientes
            sqLiteDatabase.execSQL("INSERT INTO Clientes (nombre,telefono,email) " +
                    "VALUES ('" + nombre + "' , '" + telefono + "' , '" + email + "')"
            );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionVieja, int versionNueva) {
        sqLiteDatabase.execSQL("DROP Table IF EXISTS Clientes");
    }
}

