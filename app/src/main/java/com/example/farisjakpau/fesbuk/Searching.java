package com.example.farisjakpau.fesbuk;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.farisjakpau.fesbuk.Adapter.ResultAdapter;
import com.example.farisjakpau.fesbuk.ObjectHolder.ResultHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FarisJakpau on 9/2/2017.
 */

public class Searching extends AsyncTask<Object , Object , String> {
    String ayah = "";
    String result = "";
    RecyclerView recyclerView;
    List<ResultHolder> data;
    Context context;

    public Searching(String ayah, RecyclerView recyclerView , Context context) {
        this.context = context;
        this.ayah = ayah;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            URL url = new URL("http://staging.quran.com:3000/api/v3/search?q="+ayah+"&size=20&page=0&language=en");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine())!= null){
                buffer.append(line + "\n");
            }
            reader.close();
            result = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        String JsonString = loadJSONFromAsset() ;
        data = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(result);
            System.out.println("Response from API "+ object);
            JSONArray object1 = object.getJSONArray("results");
            System.out.println("Response REsult "+ object1);
//            JSONObject object2;
            //Trabsaltion JSON parsing
            JSONObject meanig = new JSONObject(JsonString);
//            JSONObject obj = meanig.getJSONObject("object");
            JSONObject obj1 = meanig .getJSONObject("quran");
            JSONArray obj2 = obj1.getJSONArray("sura");
            JSONObject obj3,obj5 ,surah;
            JSONArray obj4;

            for(int i= 0 ;i<object1.length();i++){
               ResultHolder holder = new ResultHolder();
                System.out.println("TEST LOOPING");
                JSONObject object2 = object1.getJSONObject(i);
                System.out.println("OBjct 2 " + object2);
                holder.setAyah(object2.getString("text_madani"));
                int num_surah = object2.getInt("chapter_id");
                int num_ayah = object2.getInt("verse_number");
                System.out.println("num surah" );

                obj3 = obj2.getJSONObject(num_surah-1);
                holder.setSurah(obj3.getString("name"));
                obj4 = obj3.getJSONArray("aya");
                obj5 = obj4.getJSONObject(num_ayah-1);
                holder.setSurahName(obj5.getString("text"));
                holder.setNum(obj5.getString("index"));
                data.add(holder);
            }
            ResultAdapter adapter = new ResultAdapter(data);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = context.getAssets().open("MalayTranslation.json");
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
