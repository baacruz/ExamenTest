package com.example.examentest.Configuracion;

public class Transacciones {
    public static final String dbname = "DBPM01";

    public static final int dbversion = 1;

    //DB Tables

    public static final String tbherramientas = "herramientas";
    public static final String tbtecnicos = "tecnicos";
    public static final String tbasignaciones = "asignaciones";



    //Table Rates Fields
    //  Tabla Herramientas
    public static final String herramienta_id = "id";
    public static final String nombre = "nombre";
    public static final String descripcion = "descripcion";
    public static final String especificaciones = "especificaciones";
    public static final String foto = "foto"; //uri
    public static final String estado = "estado"; //por defecto disponible

    //Tecnicos
    public static final String tecnico_id = "tecnico_id";
    public static final String nombre_tecnico = "nombre";
    public static final String telefono = "telefono";
    public static final String especialidad = "especialidad";

    //Asignaciones
    public static final String asignacion_id = "asignacion_id";
    public static final String fecha_inicio = "fecha_inicio";
    public static final String fecha_fin = "fecha_fin";
    public static final String fecha_devolucion = "fecha_devolucion";
    public static final String notas_entrega = "notas_entrega"; //uri foto al entregar
    public static final String foto_entrega = "foto_entrega"; //uri foto entrega
    public static final String foto_devolucion = "foto_devolucion"; //uri foto devolucion
    //FK A herramientas
    //FK A tecnicos



    //Drops
    public static final String DropTableHerramientas = "DROP TABLE IF EXISTS " + tbherramientas;
    public static final String DropTableTecnicos = "DROP TABLE IF EXISTS " + tbtecnicos;
    public static final String DropTableAsignaciones = "DROP TABLE IF EXISTS " + tbasignaciones;


    public static final String TABLE_HERRAMIENTAS = "Herramientas";
    public static final String TABLE_TECNICOS = "Tecnicos";
    public static final String TABLE_ASIGNACIONES = "Asignaciones";


    public static final String CreateTableHerramientas =
            "CREATE TABLE IF NOT EXISTS " + TABLE_HERRAMIENTAS  + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"  +
                    nombre + " TEXT NOT NULL, " +
                    descripcion + " TEXT NOT NULL, " +
                    especificaciones + " TEXT NOT NULL, " +
                    foto + " TEXT, " +
                    estado + " TEXT NOT NULL DEFAULT 'DISPONIBLE' )";


    public static final String CreateTableTecnicos =
            "CREATE TABLE IF NOT EXISTS " + TABLE_TECNICOS + " ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    nombre_tecnico + " TEXT NOT NULL, " +
                    telefono + " TEXT NOT NULL, " +
                    especialidad + " TEXT NOT NULL )";

    public static final String CreateTableAsignaciones =
            "CREATE TABLE IF NOT EXISTS " + TABLE_ASIGNACIONES + " ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    herramienta_id + " INTEGER NOT NULL, " +
                    tecnico_id + " INTEGER NOT NULL, " +
                    fecha_inicio + " TEXT NOT NULL, " +
                    fecha_fin + " TEXT NOT NULL, " +
                    fecha_devolucion + " TEXT NOT NULL, " +
                    notas_entrega + " TEXT NOT NULL, " +
                    foto_entrega + " TEXT NOT NULL, " +
                    foto_devolucion + " TEXT NOT NULL, " +
                    "FOREIGN KEY(" + herramienta_id + ") REFERENCES " + TABLE_HERRAMIENTAS + "(" + herramienta_id + "), " +
                    "FOREIGN KEY(" + tecnico_id + ") REFERENCES " + TABLE_TECNICOS + "(" + tecnico_id + ")" + ")";


    //DML

    public static final String SelectTableHerramientas = "SELECT * FROM " + TABLE_HERRAMIENTAS;

    public static final String SelectTableTecnicos = "SELECT * FROM " + TABLE_TECNICOS;

    public static final String SelectTableAsignaciones = "SELECT * FROM " + TABLE_ASIGNACIONES;

}
