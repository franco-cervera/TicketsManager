package com.example.ticketsmanager.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ticketsmanager.database.DatabaseHelper;
import com.example.ticketsmanager.model.Mensaje;

import java.util.ArrayList;
import java.util.List;

public class MensajeDAO {
    private DatabaseHelper dbHelper;

    public MensajeDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Mensaje> obtenerMensajes() {
        List<Mensaje> mensajes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT m.id_mensaje, m.id_tecnico, m.asunto, m.mensaje, m.estado, u.nombreUsuario " +
                "FROM mensajes m " +
                "JOIN usuarios u ON m.id_tecnico = u.id";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idMensaje = cursor.getInt(cursor.getColumnIndex("id_mensaje"));
                @SuppressLint("Range") int idTecnico = cursor.getInt(cursor.getColumnIndex("id_tecnico"));
                @SuppressLint("Range") String asunto = cursor.getString(cursor.getColumnIndex("asunto"));
                @SuppressLint("Range") String mensaje = cursor.getString(cursor.getColumnIndex("mensaje"));
                @SuppressLint("Range") String estado = cursor.getString(cursor.getColumnIndex("estado"));
                @SuppressLint("Range") String nombreUsuario = cursor.getString(cursor.getColumnIndex("nombreUsuario")); // Cambiar aquí

                Mensaje mensajeObj = new Mensaje(idMensaje, idTecnico, asunto, mensaje, estado, nombreUsuario);
                mensajes.add(mensajeObj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mensajes;
    }


    public void marcarMensajeComoLeido(int idMensaje) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("estado", "leído");

        db.update("mensajes", values, "id_mensaje = ?", new String[]{String.valueOf(idMensaje)});
    }
}
