package com.example.ticketsmanager.controller.trabajador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.model.Ticket;

import java.util.List;

public class TicketAdapterTrabajador extends RecyclerView.Adapter<TicketAdapterTrabajador.TicketViewHolder> {

    private List<Ticket> ticketList;
    private OnTicketClickListener onTicketClickListener;

    public TicketAdapterTrabajador(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void setOnTicketClickListener(OnTicketClickListener listener) {
        this.onTicketClickListener = listener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_trabajador, parent, false);
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

        TextView tvTitulo, tvEstado, tvTecnico, tvDescripcion;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvTecnico = itemView.findViewById(R.id.tvTecnico);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }

        public void bind(Ticket ticket) {
            tvTitulo.setText(ticket.getTitulo());
            tvEstado.setText(ticket.getEstado().toString());

            if (ticket.getIdTecnico() > 0) {
                tvTecnico.setText("Técnico ID: " + ticket.getIdTecnico());
            } else {
                tvTecnico.setText("Sin técnico asignado");
            }

            tvDescripcion.setText(ticket.getDescripcion());

            // Configurar el clic en el elemento
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
        notifyDataSetChanged();
    }
}
