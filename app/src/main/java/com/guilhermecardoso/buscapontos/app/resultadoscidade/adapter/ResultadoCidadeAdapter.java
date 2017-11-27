package com.guilhermecardoso.buscapontos.app.resultadoscidade.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guilhermecardoso.buscapontos.R;
import com.guilhermecardoso.buscapontos.app.resultadoscidade.ResultadosCidadeContract;
import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

public class ResultadoCidadeAdapter extends RecyclerView.Adapter<ResultadoCidadeAdapter.ViewHolder>  {

    private List<Cidade> cidades;
    ResultadosCidadeContract.Actions presenter;

    public ResultadoCidadeAdapter(ResultadosCidadeContract.Actions presenter) {
        cidades = new ArrayList<>();
        this.presenter = presenter;
    }

    public void addAll(List<Cidade> cidades) {
        this.cidades = cidades;
        notifyDataSetChanged();
    }

    public void clear() {
        cidades.clear();
        notifyDataSetChanged();
    }

    @Override
    public ResultadoCidadeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_resultado_cidade, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ResultadoCidadeAdapter.ViewHolder holder, int position) {

        if (cidades.size() > 0) {
            final Cidade cidadeAtual = cidades.get(position);
            holder.textViewCidade.setText(cidadeAtual.getNome());
            holder.textViewEstado.setText(cidadeAtual.getEstado());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.loadDetails(cidadeAtual);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cidades.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_cidade)
        TextView textViewCidade;
        @BindView(R.id.text_estado)
        TextView textViewEstado;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
