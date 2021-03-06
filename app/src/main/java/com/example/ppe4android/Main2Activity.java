package com.example.ppe4android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        changepage2();
        Button btnApiAll = (Button) findViewById(R.id.btn_api_all);

        btnApiAll.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //le contexte est l'instance (this) de l'activity APIActivity
                RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);

                //url du service à consommer
                String url = "http://192.168.0.10:8082/ppe4/public/api/produit";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ListView lst;
                                lst = (ListView) findViewById(R.id.list_api_all);
                                //parcours des Clients retournés
                                ArrayList<String> lesClients = new ArrayList<String>();
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject item = jsonArray.getJSONObject(i);
                                        String libelle = item.getString("libelle");
                                        lesClients.add(libelle);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //Remplissage de la listview
                                ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, lesClients);
                                lst.setAdapter(arrayadapter);

                                //Si on veut obtenir une action lorsque l’on clique sur un élément de la listview !
                                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        TextView tv = (TextView) view;
                                        Toast.makeText(Main2Activity.this, tv.getText() + "  " + position, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    TextView textApiAll = (TextView) findViewById(R.id.text_api_all);
                    textApiAll.setText("Erreur, " + error.getMessage());
                    }        // CAS d’ERREUR


                });
                queue.add(stringRequest);
            }
        });
    }
    private void changepage2(){
        Button btnchange1 = (Button) findViewById(R.id.idchange2);
        btnchange1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(intent);
            }
        });

    }
}
