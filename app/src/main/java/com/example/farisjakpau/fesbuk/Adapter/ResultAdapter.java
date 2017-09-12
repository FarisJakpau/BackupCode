package com.example.farisjakpau.fesbuk.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.farisjakpau.fesbuk.ObjectHolder.ResultHolder;
import com.example.farisjakpau.fesbuk.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by FarisJakpau on 9/3/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    List<ResultHolder> data ;

    LayoutInflater inflater;
    ResultHolder current ;

    public ResultAdapter(List<ResultHolder> data) {
        this.data = data;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.from(parent.getContext()).inflate(R.layout.result_view,parent,false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        current = data.get(position);

        holder.MeaningAyat.setText(current.getSurahName());
        holder.AyatResult.setText(current.getAyah());
        holder.surah.setText(current.getSurah());
        holder.num.setText(current.getNum());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder{

        TextView AyatResult , MeaningAyat,surah,num;

        public ResultViewHolder(View itemView) {
            super(itemView);

            AyatResult = (TextView)itemView.findViewById(R.id.Result_ayah);
            MeaningAyat = (TextView)itemView.findViewById(R.id.Result_maksud);
            surah = (TextView)itemView.findViewById(R.id.result_surah);
            num = (TextView)itemView.findViewById(R.id.num_ayah);
        }
    }
}
