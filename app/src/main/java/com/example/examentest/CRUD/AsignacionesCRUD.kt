package com.example.examentest.CRUD

import android.content.ContentValues
import android.content.Context
import com.example.examentest.Configuracion.SQLiteConexion
import com.example.examentest.Configuracion.Transacciones

class AsignacionesCRUD(private val context: Context) {

    fun insertarAsignacion(herramientaId: Int, tecnicoId: Int, fechaInicio: String, fechaFin: String): Long {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase

        val values = ContentValues().apply {
            put("herramienta_fk_id", herramientaId)
            put("tecnico_fk_id", tecnicoId)
            put(Transacciones.fecha_inicio, fechaInicio)
            put(Transacciones.fecha_fin, fechaFin)
            put(Transacciones.fecha_devolucion, "") // Valor inicial
            put(Transacciones.notas_entrega, "") // Valor inicial
            put(Transacciones.foto_entrega, "") // Valor inicial
            put(Transacciones.foto_devolucion, "") // Valor inicial
        }

        val resultado = db.insert(Transacciones.TABLE_ASIGNACIONES, null, values)
        db.close()
        return resultado
    }

    fun marcarDevolucion(asignacionId: Int, fechaDevolucion: String): Int {
        val conexion = SQLiteConexion(context, Transacciones.dbname, null, Transacciones.dbversion)
        val db = conexion.writableDatabase
        val values = ContentValues().apply {
            put(Transacciones.fecha_devolucion, fechaDevolucion)
        }
        val resultado = db.update(
            Transacciones.TABLE_ASIGNACIONES,
            values,
            "${Transacciones.asignacion_id} = ?",
            arrayOf(asignacionId.toString())
        )
        db.close()
        return resultado
    }
}
