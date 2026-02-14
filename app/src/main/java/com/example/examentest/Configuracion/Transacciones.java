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


    public static final String TABLE_NAME = "Tecnicos";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_ESPECIALIDAD = "especialidad";



    public static final String DropTableHerramientas = "DROP TABLE IF EXISTS " + tbherramientas;


    /*
    public static final String CreateTablePerson = " CREATE TABLE " + tbpersons + " ( " +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            nombres + " TEXT , " +
            apellidos + " TEXT , " +
            edad + " INTEGER , " +
            correo + " TEXT , " +
            foto + " TEXT ) " ;

     */

    public static final String TABLE_HERRAMIENTAS = "Herramientas";

    public static final String CreateTableHerramientas =
            "CREATE TABLE " + TABLE_HERRAMIENTAS + " ( " +
                    herramienta_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    nombre + " TEXT NOT NULL, " +
                    descripcion + " TEXT NOT NULL, " +
                    especificaciones + " TEXT NOT NULL, " +
                    foto + " TEXT, " +
                    estado + " TEXT NOT NULL DEFAULT 'DISPONIBLE' )";

    /*
    public static final String CreateTableConversions = " CREATE TABLE " + tbconversions + " ( " +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            from_code + " TEXT , " +
            to_code + " TEXT , " +
            amount + " REAL , " +
            result + " REAL , " +
            date + " TEXT ) " ;



    //DDL  DROP
    //public static final String DropTablePerson = "DROP TABLE IF Exists " + tbpersons;

    //DML
    //public static final String SelectTablePerson ="SELECT * FROM " + tbpersons;

    //DDL rates
    public static final String DropTableRates = "DROP TABLE IF EXISTS " + tbrates;

    //DML
    public static final String SelectTableRates = "SELECT * FROM " + tbrates;

    //DDL conversions
    public static final String DropTableConversions = "DROP TABLE IF EXISTS " + tbconversions;

    //DML
    public static final String SelectTableConversions = "SELECT * FROM " + tbconversions;

*/
}
