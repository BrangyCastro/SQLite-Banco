package com.facci.brangycastro.sqliteexamen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by brangycastro on 24/2/18.
 */

public class HerperBD extends SQLiteOpenHelper {

    public HerperBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table bancoSql(cedula text PRIMARY KEY, nombre text, saldo text);");

    }

    public void insertar(String cedula, String nombre, String saldo){

        ContentValues values = new ContentValues();
        values.put("cedula",cedula);
        values.put("nombre",nombre);
        values.put("saldo",saldo);
        this.getWritableDatabase().insert("bancoSql", null,values);

    }

    public String leercedula (String cedula){
        String consulta ="";
       // String []args = new String[] {cedula};
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM bancoSql "+" WHERE cedula = "+ cedula +" ", null);
        if (cursor.moveToFirst()){
            do{
                String saldo = cursor.getString(cursor.getColumnIndex("saldo"));
                consulta +=  saldo;

            }while (cursor.moveToNext());
        }
        return consulta;
    }

    public String leerTodo(){
        String consulta = "";
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM bancoSql", null);
        if (cursor.moveToFirst()){
            do{
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String saldo = cursor.getString(cursor.getColumnIndex("saldo"));
                String cedulaBanco = cursor.getString(cursor.getColumnIndex("cedula"));
                consulta += cedulaBanco +" "+ nombre + " " + saldo +" \n" +" \n";
            }while (cursor.moveToNext());
        }
        return consulta;
    }

    public void modificar(String cedula, String saldo){
        ContentValues values = new ContentValues();
        values.put("saldo",saldo);
        this.getWritableDatabase().update(
                "bancoSql", values, "cedula='" + cedula +"'", null );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
