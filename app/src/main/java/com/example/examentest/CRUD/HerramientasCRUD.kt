package com.example.examentest.CRUD

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.examentest.Configuracion.Transacciones
import com.example.examentest.Configuracion.SQLiteConexion


class HerramientasCRUD(private val context: Context) {

        // CREATE
        fun insertarHerramienta(nombre: String, descripcion: String, especificaciones: String, foto: String?): Long {
            val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
            val db = conexion.writableDatabase
            val values = ContentValues().apply {
                put(Transacciones.nombre, nombre)
                put(Transacciones.descripcion, descripcion)
                put(Transacciones.especificaciones, especificaciones)
                foto?.let { put(Transacciones.foto, it) }
            }
            val resultado = db.insert(Transacciones.TABLE_HERRAMIENTAS, null, values)
            db.close()
            return resultado
        }

        // READ (por id)
        fun obtenerHerramientaPorId(id: Int): Cursor? {
            val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
            val db = conexion.readableDatabase
            return db.rawQuery(
                "SELECT * FROM ${Transacciones.TABLE_HERRAMIENTAS} WHERE ${Transacciones.herramienta_id} = ?",
                arrayOf(id.toString())
            )
        }

        // READ ALL
        fun obtenerTodasHerramientas(): Cursor? {
            val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
            val db = conexion.readableDatabase
            return db.rawQuery(Transacciones.SelectTableHerramientas, null)
        }

        // UPDATE
        fun actualizarHerramienta(id: Int, nombre: String, descripcion: String, especificaciones: String, foto: String?): Int {
            val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
            val db = conexion.writableDatabase
            val values = ContentValues().apply {
                put(Transacciones.nombre, nombre)
                put(Transacciones.descripcion, descripcion)
                put(Transacciones.especificaciones, especificaciones)
                foto?.let { put(Transacciones.foto, it) }
            }
            val resultado = db.update(
                Transacciones.TABLE_HERRAMIENTAS,
                values,
                "${Transacciones.herramienta_id} = ?",
                arrayOf(id.toString())
            )
            db.close()
            return resultado
        }

        // DELETE
        fun eliminarHerramienta(id: Int): Int {
            val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
            val db = conexion.writableDatabase
            val resultado = db.delete(
                Transacciones.TABLE_HERRAMIENTAS,
                "${Transacciones.herramienta_id} = ?",
                arrayOf(id.toString())
            )
            db.close()
            return resultado
        }
}

