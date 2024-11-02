package com.example.ticketsmanager.controller.tecnico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.model.Ticket;

import java.util.List;

public class TicketAdapterTecnico extends RecyclerView.Adapter<TicketAdapterTecnico.TicketViewHolder> {

    private List<Ticket> ticketList;
    private OnTicketClickListener onTicketClickListener;

    public TicketAdapterTecnico(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void setOnTicketClickListener(OnTicketClickListener listener) {
        this.onTicketClickListener = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.bind(ticket);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo, tvEstado, tvDescripcion;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }

        public void bind(Ticket ticket) {
            tvTitulo.setText(ticket.getTitulo());
            tvEstado.setText(ticket.getEstado().toString());
            tvDescripcion.setText(ticket.getDescripcion()); // Asignar la descripción

            // Configurar el clic en el ticket
            itemView.setOnClickListener(v -> {
                if (onTicketClickListener != null) {
                    onTicketClickListener.onTicketClick(ticket);
                }
            });
        }
    }

    // Interfaz para el listener de clic
    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    public void updateData(List<Ticket> newTicketList) {
        this.ticketList = newTicketList;
        notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
}
