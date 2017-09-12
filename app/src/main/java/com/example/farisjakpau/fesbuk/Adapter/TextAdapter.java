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
import com.example.farisjakpau.fesbuk.checked;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FarisJakpau on 4/19/2017.
 */

public class TextAdapter extends BaseAdapter {


    private Context context;
    private List<Text> textList;

    public TextAdapter(Context context, ArrayList<Text> arrayList) {
      //  super(context,0,arrayList);
        this.context = context;
        this.textList = arrayList;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {

        final View view = View.inflate(context, R.layout.list_style, null);
        Button b1 ;
        b1=(Button) view.findViewById(R.id.but1);
        final TextView mess = (TextView) view.findViewById(R.id.tmess);
        final TextView des = (TextView) view.findViewById(R.id.des);

        mess.setText(textList.get(position).getT_mss());
        des.setText(textList.get(position).getT_des());
        // view.setTag(textList.get(position).get);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,checked.class);
                intent.putExtra("mes" ,mess.getText());
                intent.putExtra("des",des.getText());
                context.startActivity(intent);
            }
        });


     /* b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View parent = (View) v.getParent();
                ListView listView = (ListView) parent.getParent();
                int pos = listView.getPositionForView(parent);

                String msg = textList.get(position).getT_mss();
                String des = textList.get(position).getT_des();

                try{
                    JSONObject json = new JSONObject(loadJson);
                    for(int i=0 ; i<=6236;i++){
                        String ayah = json.getJSONObject("quran").getJSONObject("quran-simple").getJSONObject(i+"").getString("ayah");
                        Log.d("ayah",ayah);

                        if (msg.contains(ayah) || des.contains(ayah)) {
                            Toast.makeText(view.getContext(),"Valid",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(view.getContext(),"Not Valid",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }); */

        return view;



    }

}
