package com.example.ticketsmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ticketsmanager.model.Ticket;
import com.example.ticketsmanager.model.Ticket.EstadoTicket;
import com.example.ticketsmanager.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements DAO<Ticket, Integer> {
    private final DatabaseHelper dbHelper;

    public TicketDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void crear(Ticket ticket) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", ticket.getTitulo());
        values.put("descripcion", ticket.getDescripcion());
        values.put("id_trabajador", ticket.getIdTrabajador());
        values.put("id_tecnico", ticket.getIdTecnico());
        values.put("estado", ticket.getEstado().name()); // Guardar el estado como String

        db.insert("tickets", null, values);
        db.close();
    }

    @Override
    public Ticket listar(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM tickets WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        Ticket ticket = null;
        if (cursor != null && cursor.moveToFirst()) {
            ticket = new Ticket(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_trabajador")),
                    EstadoTicket.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("estado"))) // Convertir el String a EstadoTicket
            );
            ticket.setIdTecnico(cursor.getInt(cursor.getColumnIndexOrThrow("id_tecnico")));
            cursor.close();
        }
        db.close();
        return ticket;
    }

    @Override
    public void actualizar(Ticket ticket) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", ticket.getTitulo());
        values.put("descripcion", ticket.getDescripcion());
        values.put("id_trabajador", ticket.getIdTrabajador());
        values.put("id_tecnico", ticket.getIdTecnico());
        values.put("estado", ticket.getEstado().name()); // Guardar el estado como String

        db.update("tickets", values, "id = ?", new String[]{String.valueOf(ticket.getId())});
        db.close();
    }

    @Override
    public void eliminar(Integer id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("tickets", "id = ?", new String[]{String.valueOf(id)});
        db.close();

    }

    @Override
    public List<Ticket> listarTodos() {
        List<Ticket> tickets = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM tickets";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Ticket ticket = new Ticket(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_trabajador")),
                        EstadoTicket.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("estado"))) // Convertir el String a EstadoTicket
                );
                ticket.setIdTecnico(cursor.getInt(cursor.getColumnIndexOrThrow("id_tecnico")));
                tickets.add(ticket);
            }
            cursor.close();
        }
        db.close();
        return tickets;
    }
}
