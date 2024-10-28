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
        values.put("estado", ticket.getEstado().name());


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
                    cursor.getInt(cursor.getColumnIndexOrThrow("id_tecnico")),
                    EstadoTicket.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("estado"))) // Convertir el String a EstadoTicket
            );
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
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_tecnico")),
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


    public int contarTicketsAtendidosPorTecnico(int idTecnico) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            String query = "SELECT COUNT(*) FROM tickets WHERE estado = ? AND id_tecnico = ?";
            cursor = db.rawQuery(query, new String[]{EstadoTicket.ATENDIDO.name(), String.valueOf(idTecnico)});

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return count;
    }


    // Método para listar los tickets que no están finalizados
    public List<Ticket> listarNoFinalizados() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM tickets WHERE estado != ?";
        Cursor cursor = db.rawQuery(query, new String[]{EstadoTicket.FINALIZADO.name()});

        if (cursor.moveToFirst()) {
            do {
                Ticket ticket = new Ticket(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_trabajador")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("id_tecnico")),
                        EstadoTicket.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("estado"))) // Convertir el String a EstadoTicket
                );
                ticket.setIdTecnico(cursor.getInt(cursor.getColumnIndexOrThrow("id_tecnico")));
                tickets.add(ticket);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tickets;
    }


}
