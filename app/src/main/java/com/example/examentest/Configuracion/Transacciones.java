package com.example.examentest.Configuracion;

public class Transacciones {
    public static final String dbname = "DBPM01";
    public static final int dbversion = 2; // Versión incrementada para forzar la actualización

    // Nombres de las tablas
    public static final String TABLE_HERRAMIENTAS = "Herramientas";
    public static final String TABLE_TECNICOS = "Tecnicos";
    public static final String TABLE_ASIGNACIONES = "Asignaciones";

    // Campos de la tabla Herramientas
    public static final String herramienta_id = "id";
    public static final String nombre_herramienta = "nombre_herramienta"; // Renombrado para mayor claridad
    public static final String descripcion = "descripcion";
    public static final String especificaciones = "especificaciones";
    public static final String foto = "foto";
    public static final String estado = "estado";

    // Campos de la tabla Tecnicos
    public static final String tecnico_id = "tecnico_id";
    public static final String nombre_tecnico = "nombre_tecnico";
    public static final String telefono = "telefono";
    public static final String especialidad = "especialidad";

    // Campos de la tabla Asignaciones
    public static final String asignacion_id = "asignacion_id";
    public static final String fecha_inicio = "fecha_inicio";
    public static final String fecha_fin = "fecha_fin";
    public static final String fecha_devolucion = "fecha_devolucion";
    public static final String notas_entrega = "notas_entrega";
    public static final String foto_entrega = "foto_entrega";
    public static final String foto_devolucion = "foto_devolucion";

    // Sentencias para eliminar tablas
    public static final String DropTableHerramientas = "DROP TABLE IF EXISTS " + TABLE_HERRAMIENTAS;
    public static final String DropTableTecnicos = "DROP TABLE IF EXISTS " + TABLE_TECNICOS;
    public static final String DropTableAsignaciones = "DROP TABLE IF EXISTS " + TABLE_ASIGNACIONES;

    // Sentencia para crear la tabla Herramientas
    public static final String CreateTableHerramientas =
            "CREATE TABLE IF NOT EXISTS " + TABLE_HERRAMIENTAS + " ( " +
                    herramienta_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    nombre_herramienta + " TEXT NOT NULL, " + // Usando la constante renombrada
                    descripcion + " TEXT NOT NULL, " +
                    especificaciones + " TEXT NOT NULL, " +
                    foto + " TEXT, " +
                    estado + " TEXT NOT NULL DEFAULT 'DISPONIBLE' )";

    // Sentencia para crear la tabla Tecnicos
    public static final String CreateTableTecnicos =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TECNICOS + " ( " +
                    tecnico_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    nombre_tecnico + " TEXT NOT NULL, " +
                    telefono + " TEXT NOT NULL, " +
                    especialidad + " TEXT NOT NULL )";

    // Sentencia para crear la tabla Asignaciones (CORREGIDA)
    public static final String CreateTableAsignaciones =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASIGNACIONES + " ( " +
                    asignacion_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "herramienta_fk_id INTEGER NOT NULL, " + // Columna de clave foránea sin duplicados
                    "tecnico_fk_id INTEGER NOT NULL, " +   // Columna de clave foránea sin duplicados
                    fecha_inicio + " TEXT NOT NULL, " +
                    fecha_fin + " TEXT NOT NULL, " +
                    fecha_devolucion + " TEXT, " +
                    notas_entrega + " TEXT, " +
                    foto_entrega + " TEXT, " +
                    foto_devolucion + " TEXT, " +
                    "FOREIGN KEY(herramienta_fk_id) REFERENCES " + TABLE_HERRAMIENTAS + "(id), " +
                    "FOREIGN KEY(tecnico_fk_id) REFERENCES " + TABLE_TECNICOS + "(tecnico_id)" + ")";

    // Sentencias DML
    public static final String SelectTableHerramientas = "SELECT * FROM " + TABLE_HERRAMIENTAS;
    public static final String SelectTableTecnicos = "SELECT * FROM " + TABLE_TECNICOS;
    public static final String SelectTableAsignaciones = "SELECT * FROM " + TABLE_ASIGNACIONES;
}
