package com.example.farisjakpau.fesbuk.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.farisjakpau.fesbuk.R;
import com.example.farisjakpau.fesbuk.ObjectHolder.Text;
import com.example.farisjakpau.fesbuk.result;

import java.util.List;

/**
 * Created by FarisJakpau on 7/10/2017.
 */

public class ayatadapter extends BaseAdapter{

    private Context context;
    private List<Text> textList;

    public ayatadapter( Context context,List<Text> textList) {
        this.textList = textList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return textList.size();
    }

    @Override
    public Object getItem(int position) {
        return textList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(context, R.layout.chck_lay, null);

        final TextView text = (TextView)view.findViewById(R.id.ayat);
        text.setText(textList.get(position).getT_des());
        final Button b1 = (Button)view.findViewById(R.id.but11);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,result.class);
                intent.putExtra("ayat",text.getText());
                context.startActivity(intent);
            }
        });


      /*  convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,result.class);
                intent.putExtra("ayat",text.getText());
                context.startActivity(intent);

            }
        }); */

        return view;
    }


}
