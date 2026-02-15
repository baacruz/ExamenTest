package com.example.examentest.CRUD

import android.content.ContentValues
import android.content.Context
import com.example.examentest.Configuracion.SQLiteConexion
import com.example.examentest.Configuracion.Transacciones

class TecnicosCRUD(private val context: Context) {
    fun insertarTecnico(nombre: String, telefono: String, especialidad: String): Long {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.nombre_tecnico, nombre)
            put(Transacciones.telefono, telefono)
            put(Transacciones.especialidad, especialidad)
        }
        val resultado = db.insert(Transacciones.TABLE_TECNICOS, null, values)
        db.close()
        return resultado
    }
}
