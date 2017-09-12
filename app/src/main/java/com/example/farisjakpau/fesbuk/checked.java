package com.example.farisjakpau.fesbuk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.farisjakpau.fesbuk.Adapter.ayatadapter;
import com.example.farisjakpau.fesbuk.ObjectHolder.Text;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checked extends AppCompatActivity {

    private ayatadapter adapter;
    private ListView lv ;
    private List<String> ayat = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked);
        ArrayList<Text> arrayList = new ArrayList();
        lv = (ListView)findViewById(R.id.listview);

        String JsonString = loadJSONFromAsset() ;
        JSONObject jsonObject;

        String mess = getIntent().getStringExtra("mes");
        String des = getIntent().getStringExtra("des");

        isArabic(mess);
        isArabic(des);

        try {
            jsonObject = new JSONObject(JsonString);

            for(int i=0 ; i<ayat.size();i++){
                String arabic = ayat.get(i);

              /*  for(int k=0 ; k<6200;k++){
                    String m = Integer.toString(k);
                    String output =jsonObject.getJSONObject("quran").getJSONObject("quran-simple").getJSONObject(m).getString("verse"); */

                   // if(output.matches("(.*)" + arabic + "(.*)")== true){
                        Text tt = new Text();
                        tt.setT_des(arabic);
                        arrayList.add(tt);
                   // }

            }

        } catch (JSONException e) {
        }

        adapter = new ayatadapter(getApplicationContext(),arrayList);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);


    }


    public void isArabic(String text) {
        Pattern p = Pattern.compile("\\p{InArabic}+(?:\\s+\\p{InArabic}+)*");
        Matcher m = p.matcher(text);

        while(m.find()){
            if(m.group().length() != 0){
                ayat.add(m.group());
            }
        }

     /*   for(int i =0 ; i<ayat.size();i++){
            System.out.println(ayat.get(i));
            System.out.println("border");
        }  */
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();  // optional depending on your needs
        ayat.clear();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("quran1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
