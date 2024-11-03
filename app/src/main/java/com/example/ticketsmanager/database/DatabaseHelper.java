package com.example.ticketsmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "tickets_manager.db";
    private static final int DATABASE_VERSION = 4;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsuariosTable = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreUsuario TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "bloqueado BOOLEAN NOT NULL," +
                "marcas INT NOT NULL," +
                "fallas INT NOT NULL" +
                ");";


        String createTicketsTable = "CREATE TABLE tickets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titulo TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "id_trabajador INTEGER," +
                "id_tecnico INTEGER," +
                "estado TEXT NOT NULL," +
                "FOREIGN KEY (id_trabajador) REFERENCES usuarios(id)," +
                "FOREIGN KEY (id_tecnico) REFERENCES usuarios(id)" +
                ");";


        String createMensajesTable = "CREATE TABLE mensajes (" +
                "id_mensaje INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_tecnico INTEGER NOT NULL," +
                "asunto TEXT NOT NULL," +
                "mensaje TEXT NOT NULL," +
                "estado TEXT NOT NULL CHECK (estado IN ('leído', 'no leído'))," +
                "FOREIGN KEY (id_tecnico) REFERENCES usuarios(id)" +
                ");";


        db.execSQL(createUsuariosTable);
        db.execSQL(createTicketsTable);
        db.execSQL(createMensajesTable);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            String createMensajesTable = "CREATE TABLE mensajes (" +
                    "id_mensaje INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id_tecnico INTEGER NOT NULL," +
                    "asunto TEXT NOT NULL," +
                    "mensaje TEXT NOT NULL," +
                    "estado TEXT NOT NULL CHECK (estado IN ('leído', 'no leído'))," +
                    "FOREIGN KEY (id_tecnico) REFERENCES usuarios(id)" +
                    ");";
            db.execSQL(createMensajesTable);
        }
    }

    /*
    PARA CUANDO TODO ESTE LISTO, INCREMENTAR 1 VERSION A LA BD Y EJECUTAR
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS tickets");
        db.execSQL("DROP TABLE IF EXISTS mensajes");
        onCreate(db);
    }*/



    public String obtenerPasswordPorId(int idUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM usuarios WHERE id = ?", new String[]{String.valueOf(idUsuario)});

        if (cursor != null && cursor.moveToFirst()) {
            String password = cursor.getString(0);
            cursor.close();
            return password;
        }

        return null;
    }


    public boolean actualizarPassword(int idUsuario, String nuevaPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", nuevaPassword);

        int rows = db.update("usuarios", values, "id = ?", new String[]{String.valueOf(idUsuario)});
        return rows > 0; // Devuelve true si la actualización fue exitosa
    }

    public void blanquearPassword(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", String.valueOf(userId)); // La contraseña será igual al ID del usuario
        db.update("usuarios", values, "id = ?", new String[]{String.valueOf(userId)});
        db.close();
    }



}
