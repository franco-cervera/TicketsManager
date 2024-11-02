package com.example.ticketsmanager.controller.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketsmanager.R;
import com.example.ticketsmanager.model.Ticket;

import java.util.List;

    public class TicketAdapterAdmin extends RecyclerView.Adapter<com.example.ticketsmanager.controller.Admin.TicketAdapterAdmin.TicketViewHolder> {

        private List<Ticket> ticketList;
        private OnTicketClickListener onTicketClickListener;

        public TicketAdapterAdmin(List<Ticket> ticketList) {
            this.ticketList = ticketList;
        }

        public void setOnTicketClickListener(com.example.ticketsmanager.controller.Admin.TicketAdapterAdmin.OnTicketClickListener listener) {
            this.onTicketClickListener = listener;
        }

        @NonNull
        @Override
        public com.example.ticketsmanager.controller.Admin.TicketAdapterAdmin.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_adm, parent, false);
            return new com.example.ticketsmanager.controller.Admin.TicketAdapterAdmin.TicketViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.ticketsmanager.controller.Admin.TicketAdapterAdmin.TicketViewHolder holder, int position) {
            Ticket ticket = ticketList.get(position);
            holder.bind(ticket);
        }

        @Override
        public int getItemCount() {
            return ticketList.size();
        }

        class TicketViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitulo, tvEstado, tvTecnico, tvDescripcion, tvTrabajador;

            public TicketViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitulo = itemView.findViewById(R.id.tvTitulo);
                tvEstado = itemView.findViewById(R.id.tvEstado);
                tvTecnico = itemView.findViewById(R.id.tvTecnico);
                tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
                tvTrabajador = itemView.findViewById(R.id.tvTrabajador);
            }

            public void bind(Ticket ticket) {
                tvTitulo.setText(ticket.getTitulo());
                tvEstado.setText(ticket.getEstado().toString());

                if (ticket.getIdTecnico() > 0) {
                    tvTecnico.setText("Técnico ID: " + ticket.getIdTecnico());
                } else {
                    tvTecnico.setText("Sin técnico asignado");
                }
                if (ticket.getIdTrabajador() > 0) {
                    tvTrabajador.setText("Trabajador ID: " + ticket.getIdTrabajador());
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
