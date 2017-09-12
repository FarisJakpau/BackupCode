package com.example.farisjakpau.fesbuk;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farisjakpau.fesbuk.ObjectHolder.ResultHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;

public class result extends AppCompatActivity {

    List<ResultHolder> data;
    ArrayList<String> aya = new ArrayList<>();
    RecyclerView recyclerView;
    Searching searching;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = (RecyclerView)findViewById(R.id.Recycler_Result);
        data = new ArrayList<>();

        String check = getIntent().getStringExtra("ayat").trim();
        searching = new Searching(check ,recyclerView , getApplicationContext() );
        searching.execute();
        System.out.println("Taubat la nak " + result);

        Toast.makeText(this,check,Toast.LENGTH_LONG).show();

        String JsonString = loadJSONFromAsset() ;
        JSONObject jsonObject;
        String gabung = "";
        boolean found = false;

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(check);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);

//
//
//        try {
//            jsonObject = new JSONObject(JsonString);
//
//                for(int k=1 ; k<=6200;k++) {
//                    String m = Integer.toString(k);
//                    String ayat = jsonObject.getJSONObject("quran").getJSONObject("quran-simple").getJSONObject(m).getString("verse");
//                    String id = jsonObject.getJSONObject("quran").getJSONObject("quran-simple").getJSONObject(m).getString("id");
//                    String surah = jsonObject.getJSONObject("quran").getJSONObject("quran-simple").getJSONObject(m).getString("surah");
//                    String num_ayat = jsonObject.getJSONObject("quran").getJSONObject("quran-simple").getJSONObject(m).getString("ayah");
//
//                   // aya.add(ayat);
//                    ayat = ayat.replaceAll(" \\u06da",""); // jeem stop
//                    ayat = ayat.replaceAll(" \\u06d6",""); // Sad Lam alif maksura
//                    ayat = ayat.replaceAll(" \\u06d7",""); // kof lam
//                    ayat = ayat.replaceAll(" \\u06d9",""); // lam alif
//                    ayat = ayat.replaceAll(" \\u06db",""); // three dots
//                    ayat = ayat.replaceAll(" \\u06dc",""); // seen
//                    ayat = ayat.replaceAll(" \\u06dd",""); // tanda akhir surah
//                    ayat = ayat.replaceAll(" \\u06de",""); // rub al hizb
//                    ayat = ayat.replaceAll(" \\u06df",""); // rounded zero
//                    ayat = ayat.replaceAll(" \\u06e0",""); // rectangular zero
//                    ayat = ayat.replaceAll(" \\u06e2",""); // mim stop
//                    ayat = ayat.replaceAll(" \\u06e3",""); // seen bawah
//                    ayat = ayat.replaceAll(" \\u06e4",""); // madda
//                    ayat = ayat.replaceAll(" \\u06e5",""); // wau kecik
//                    ayat = ayat.replaceAll(" \\u06e6",""); // ya kecik
//                    ayat = ayat.replaceAll(" \\u06e8",""); // nun atas
//                    ayat = ayat.replaceAll(" \\u06e9",""); // sajdah
//                    ayat = ayat.replaceAll(" \\u06ea",""); // EMPTY CENTER LOW STOP
//                    ayat = ayat.replaceAll(" \\u06eb",""); // EMPTY CENTER HIGH STOP
//                    ayat = ayat.replaceAll(" \\u06ec",""); // ROUNDED HIGH STOP
//                    ayat = ayat.replaceAll(" \\u06ed",""); // mim bawah
//
//                    String gabung_new = Normalizer.normalize(ayat,Form.NFD).replaceAll("\\p{M}", "");
//                    gabung = gabung+ " " + gabung_new;
//                }
//
//
//
//        } catch (JSONException e) {
//        }
//
//        check = check.replaceAll(" \\u06da","");
//        check = check.replaceAll(" \\u06da",""); // jeem stop
//        check = check.replaceAll(" \\u06d6",""); // Sad Lam alif maksura
//        check = check.replaceAll(" \\u06d7",""); // kof lam
//        check = check.replaceAll(" \\u06d9",""); // lam alif
//        check = check.replaceAll(" \\u06db",""); // three dots
//        check = check.replaceAll(" \\u06dc",""); // seen
//        check = check.replaceAll(" \\u06dd",""); // tanda akhir surah
//        check = check.replaceAll(" \\u06de",""); // rub al hizb
//        check = check.replaceAll(" \\u06df",""); // rounded zero
//        check = check.replaceAll(" \\u06e0",""); // rectangular zero
//        check = check.replaceAll(" \\u06e2",""); // mim stop
//        check = check.replaceAll(" \\u06e3",""); // seen bawah
//        check = check.replaceAll(" \\u06e4",""); // madda
//        check = check.replaceAll(" \\u06e5",""); // wau kecik
//        check = check.replaceAll(" \\u06e6",""); // ya kecik
//        check = check.replaceAll(" \\u06e8",""); // nun atas
//        check = check.replaceAll(" \\u06e9",""); // sajdah
//        check = check.replaceAll(" \\u06ea",""); // EMPTY CENTER LOW STOP
//        check = check.replaceAll(" \\u06eb",""); // EMPTY CENTER HIGH STOP
//        check = check.replaceAll(" \\u06ec",""); // ROUNDED HIGH STOP
//        check = check.replaceAll(" \\u06ed",""); // mim bawah
//
//
//        String check_new =  Normalizer.normalize(check,Form.NFD).replaceAll("\\p{M}", "");
//        found = gabung.matches("(.*)" + check_new + "(.*)");
//
//
//        tv_ori.setText(check);
//        tv_buang.setText(check_new);
//
//
//        if(found == true){
//            ayat.setText("true");
//        }
//        else
//            ayat.setText("False");
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("MalayTranslation.json");
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
