package com.example.examentest.Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteConexion extends SQLiteOpenHelper
{
    public SQLiteConexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Transacciones.CreateTableHerramientas);
        db.execSQL(Transacciones.CreateTableTecnicos);
        db.execSQL(Transacciones.CreateTableAsignaciones);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(Transacciones.DropTableHerramientas);
        onCreate(db);

        db.execSQL(Transacciones.DropTableTecnicos);
        onCreate(db);

        db.execSQL(Transacciones.DropTableAsignaciones);
        onCreate(db);

    }
}
