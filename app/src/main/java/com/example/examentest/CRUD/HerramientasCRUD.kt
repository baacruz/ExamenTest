package com.example.examentest.CRUD

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.examentest.Configuracion.SQLiteConexion
import com.example.examentest.Configuracion.Transacciones

class HerramientasCRUD(private val context: Context) {

    fun insertarHerramienta(nombre: String, descripcion: String, especificaciones: String, foto: String?): Long {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.nombre_herramienta, nombre)
            put(Transacciones.descripcion, descripcion)
            put(Transacciones.especificaciones, especificaciones)
            foto?.let { put(Transacciones.foto, it) }
        }
        val resultado = db.insert(Transacciones.TABLE_HERRAMIENTAS, null, values)
        db.close()
        return resultado
    }

    @SuppressLint("Recycle")
    fun obtenerTodasLasHerramientas(): Cursor? {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.readableDatabase
        return db.rawQuery(Transacciones.SelectTableHerramientas, null)
    }

    @SuppressLint("Recycle")
    fun obtenerHerramientasDisponibles(): Cursor? {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.readableDatabase
        return db.rawQuery("SELECT * FROM ${Transacciones.TABLE_HERRAMIENTAS} WHERE ${Transacciones.estado} = 'DISPONIBLE'", null)
    }

    @SuppressLint("Recycle")
    fun obtenerVistaCompletaHerramientas(): Cursor? {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.readableDatabase
        val query = """
        SELECT
            h.id as _id,
            h.nombre_herramienta,
            h.especificaciones,
            h.estado,
            latest_a.asignacion_id,
            latest_a.fecha_fin,
            latest_a.fecha_devolucion,
            t.nombre_tecnico
        FROM
            ${Transacciones.TABLE_HERRAMIENTAS} AS h
        LEFT JOIN
            ${Transacciones.TABLE_ASIGNACIONES} AS latest_a ON latest_a.asignacion_id = (
                SELECT MAX(asignacion_id)
                FROM ${Transacciones.TABLE_ASIGNACIONES}
                WHERE herramienta_fk_id = h.id
            )
        LEFT JOIN
            ${Transacciones.TABLE_TECNICOS} AS t ON latest_a.tecnico_fk_id = t.tecnico_id
        ORDER BY
            CASE WHEN h.estado = 'DISPONIBLE' THEN 1 ELSE 0 END,
            latest_a.fecha_fin ASC
    """
        return db.rawQuery(query, null)
    }

    fun actualizarEstadoHerramienta(id: Int, nuevoEstado: String): Int {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.estado, nuevoEstado)
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

    @SuppressLint("Recycle")
    fun obtenerHerramientaPorId(id: Int): Cursor? {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.readableDatabase
        return db.rawQuery(
            "SELECT * FROM ${Transacciones.TABLE_HERRAMIENTAS} WHERE ${Transacciones.herramienta_id} = ?",
            arrayOf(id.toString())
        )
    }

    fun actualizarHerramienta(id: Int, nombre: String, descripcion: String, especificaciones: String, foto: String?): Int {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.nombre_herramienta, nombre)
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
