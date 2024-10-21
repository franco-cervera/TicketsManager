package com.example.ticketsmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "tickets_manager.db";
    private static final int DATABASE_VERSION = 2;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String createUsuariosTable = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreUsuario TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "tipo TEXT NOT NULL," +
                "bloqueado BOOLEAN NOT NULL" +
                ");";

        // Crear tabla de tickets
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

        db.execSQL(createUsuariosTable);
        db.execSQL(createTicketsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar las actualizaciones de la base de datos
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS tickets");
        onCreate(db);
    }

    // Método para obtener la contraseña del usuario por ID
    public String obtenerPasswordPorId(int idUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM usuarios WHERE id = ?", new String[]{String.valueOf(idUsuario)});

        if (cursor != null && cursor.moveToFirst()) {
            String password = cursor.getString(0);
            cursor.close();
            return password;
        }

        return null; // Usuario no encontrado
    }

    // Método para actualizar la contraseña del usuario
    public boolean actualizarPassword(int idUsuario, String nuevaPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", nuevaPassword);

        int rows = db.update("usuarios", values, "id = ?", new String[]{String.valueOf(idUsuario)});
        return rows > 0; // Devuelve true si la actualización fue exitosa
    }

}
