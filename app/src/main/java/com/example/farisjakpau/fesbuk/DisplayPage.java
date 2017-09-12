package com.example.farisjakpau.fesbuk;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.farisjakpau.fesbuk.Adapter.TextAdapter;
import com.example.farisjakpau.fesbuk.ObjectHolder.Text;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayPage extends AppCompatActivity {

    private TextAdapter adapter;
    private FirebaseUser user;
    Button but;
    private ListView lv;
    TextView tv2;
    private FirebaseAuth mAuth;
    TextView tt;
    AccessToken acc = AccessToken.getCurrentAccessToken();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar pgb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_page);

        mAuth = FirebaseAuth.getInstance();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        but = (Button)findViewById(R.id.but1);
        user = FirebaseAuth.getInstance().getCurrentUser();
        // tv2 = (TextView)findViewById(R.id.tv2);
        //tv2.setText(acc.toString());

        if(user != null){
          //  pgb.setVisibility(View.VISIBLE);
            fesbukStat(acc);
           // signOut();
        }
        else
            tt.setText("No Data");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               fesbukStat(acc);
            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    public void signOut(){
        Intent i = new Intent(DisplayPage.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        startActivity(i);

        finish();

    }

    private void fesbukStat(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        ArrayList<Text> arrayList = new ArrayList(); //creating listview
                        try {
                            JSONArray array = object.getJSONObject("feed").getJSONArray("data");
                            Log.i("JArray",array.length()+"");

                            for(int i=0 ; i<100 ;i++){
                                Log.i("testloop" , array.length()+"");
                                Text text = new Text();

                                JSONObject mm = array.getJSONObject(i);

                                if(mm.has("message") && mm.has("description")){
                                    String msj = mm.getString("message");
                                    text.setT_mss(msj);
                                    String des = mm.getString("description");
                                    text.setT_des(des);
                                    arrayList.add(text);
                                }

                                else if(mm.has("message")){
                                    String msj = mm.getString("message");
                                    text.setT_mss(msj);
                                    arrayList.add(text);
                                }

                                else if(mm.has("description")){
                                    String des = mm.getString("description");
                                    text.setT_des(des);
                                    arrayList.add(text);
                                }

                                else
                                     continue;;

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                         lv = (ListView)findViewById(R.id.listview);
                        adapter = new TextAdapter(getApplicationContext(),arrayList);
                        adapter.notifyDataSetChanged();
                        lv.setAdapter(adapter);

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,feed.limit(1000){message,description}");
        request.setParameters(parameters);
        request.executeAsync();
        swipeRefreshLayout.setRefreshing(false);
    }

}
