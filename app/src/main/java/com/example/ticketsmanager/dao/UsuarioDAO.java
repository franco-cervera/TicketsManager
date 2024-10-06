package com.example.ticketsmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ticketsmanager.database.DatabaseHelper;
import com.example.ticketsmanager.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario, Integer> {

    private final DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void crear(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreUsuario", usuario.getNombreUsuario());
        values.put("contrasena", usuario.getContrasena());
        values.put("tipo", usuario.getTipo());

        long id = db.insert("usuarios", null, values);
        usuario.setId((int) id); // Establece el ID generado en el objeto Usuario

        db.close();
    }


    @Override
    public Usuario listar(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            Usuario usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario")), // Obtener nombre de usuario
                    cursor.getString(cursor.getColumnIndexOrThrow("contrasena")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
            );
            cursor.close();
            db.close();
            return usuario;
        }
        cursor.close();
        db.close();
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("contrasena", usuario.getContrasena());
        values.put("tipo", usuario.getTipo());

        db.update("usuarios", values, "id = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    @Override
    public void eliminar(Integer id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("usuarios", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM usuarios";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario")), // Obtener nombre de usuario
                        cursor.getString(cursor.getColumnIndexOrThrow("contrasena")),
                        cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                );
                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return usuarios;
    }

    public Usuario listarPorTipo(String tipo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE tipo = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tipo});

        if (cursor != null && cursor.moveToFirst()) {
            Usuario usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario")),
                    cursor.getString(cursor.getColumnIndexOrThrow("contrasena")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
            );
            cursor.close();
            db.close();
            return usuario;
        }
        cursor.close();
        db.close();
        return null; // Si no existe, retornar null
    }

    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE nombreUsuario = ? AND contrasena = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombreUsuario, contrasena});

        boolean existe = cursor.moveToFirst(); // true si existe, false si no
        cursor.close();
        db.close();
        return existe;
    }


}
