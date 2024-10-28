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
        values.put("password", "");
        values.put("tipo", usuario.getTipo());
        values.put("bloqueado", usuario.isBloqueado() ? 1 : 0); // Convertir boolean a int

        long id = db.insert("usuarios", null, values);
        usuario.setId((int) id); // Establece el ID generado en el objeto Usuario

        ContentValues updateValues = new ContentValues();
        updateValues.put("password", String.valueOf(id)); // Usar el ID como la nueva contraseña

        db.update("usuarios", updateValues, "id = ?", new String[]{String.valueOf(id)});

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
                    cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
            );

            // Recupera el estado de bloqueado y lo establece en el objeto Usuario
            usuario.setBloqueado(cursor.getInt(cursor.getColumnIndexOrThrow("bloqueado")) == 1); // Asegúrate de que aquí se lea el estado correctamente

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
        values.put("password", usuario.getPassword());
        values.put("tipo", usuario.getTipo());
        values.put("bloqueado", usuario.isBloqueado() ? 1 : 0); // Convertir boolean a int

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
                        cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                );

                // Recupera el estado de bloqueado y lo establece en el objeto Usuario
                usuario.setBloqueado(cursor.getInt(cursor.getColumnIndexOrThrow("bloqueado")) == 1); // Asegúrate de que aquí se lea el estado correctamente

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
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
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

    public Usuario validarCredenciales(String idUsuario, String password, String tipo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE id = ? AND password = ? AND tipo = ? AND bloqueado = 0";
        Cursor cursor = db.rawQuery(query, new String[]{idUsuario, password, tipo});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
            boolean bloqueado = cursor.getInt(cursor.getColumnIndexOrThrow("bloqueado")) == 1;

            Usuario usuario = new Usuario(id, cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario")), pass, tipoUsuario);
            usuario.setBloqueado(bloqueado);
            cursor.close();
            db.close();
            return usuario;
        } else {
            cursor.close();
            db.close();
            return null; // No se encontró ningún usuario que coincida
        }
    }




    public boolean existeUsuario(String idUsuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM usuarios WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{idUsuario});

        boolean existe = cursor.moveToFirst(); // true si existe, false si no
        cursor.close();
        db.close();
        return existe;
    }


}
