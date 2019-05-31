package com.example.ezavedyraksaputra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ezavedyraksaputra.model.KecamatanModel;
import com.example.ezavedyraksaputra.model.KotaModel;
import com.example.ezavedyraksaputra.model.PropinsiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] propinsi1, propinsi2;
    String[] kota1, kota2;
    ArrayList<String> propId = new ArrayList<>();
    ArrayList<String> kotaId = new ArrayList<>();
    ArrayList<String> propinsiArray = new ArrayList<>();
    ArrayList<String> kotaArray = new ArrayList<>();
    ArrayList<String> kecamatanArray = new ArrayList<>();

    Spinner spinnerPropinsi, spinnerKota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerPropinsi = findViewById(R.id.spinnerPropinsi);
        spinnerKota = findViewById(R.id.spinnerKota);

        GetPropinsi();

        spinnerPropinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String propinsi = (String) parent.getItemAtPosition(position);

                if (!propinsi.equals("Pilih Provinsi")) {
                    //Log.d("GetKota", String.valueOf(position));
                    GetKota(propId.get(position-1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String propinsi = (String) parent.getItemAtPosition(position);
                if (!propinsi.equals("Pilih Kota / Kabupaten")) {
                    GetKota(propId.get(position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void GetKota(String propinsi) {
        AndroidNetworking.get("https://kodepos-2d475.firebaseio.com/list_kotakab/" + propinsi + ".json?print=pretty")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("GetKota", response.toString());
                        kota1 = response.toString().substring(1, response.toString().length()-1).split(",");

                        kotaArray.add("Pilih Kota / Kabupaten");

                        for (int i=0; i < kota1.length; i++) {
                            kota2 = kota1[i].split(":");
                            kotaId.add(kota2[0]);
                            kotaArray.add(kota2[1].replace("\"", ""));
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_dropdown_item, kotaArray);

                        spinnerKota.setAdapter(adapter);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RequestError", error.toString());
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void GetPropinsi() {
        AndroidNetworking.get("https://kodepos-2d475.firebaseio.com/list_propinsi.json?print=pretty")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("GetPropinsi", response.toString().substring(1, response.toString().length()-1));
                        propinsi1 = response.toString().substring(1, response.toString().length()-1).split(",");

                        propinsiArray.add("Pilih Provinsi");

                        for (int i=0; i < propinsi1.length; i++) {
                            propinsi2 = propinsi1[i].split(":");
                            propId.add(propinsi2[0].replace("\"", ""));
                            Log.d("GetKota", propId.get(i));
                            propinsiArray.add(propinsi2[1].replace("\"", ""));
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_dropdown_item, propinsiArray);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinnerPropinsi.setAdapter(adapter);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RequestError", error.toString());
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void GetKecamatan(String kota) {
        AndroidNetworking.get("https://kodepos-2d475.firebaseio.com/kota_kab/" + kota + ".json?print=pretty")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.d("GetKecamatan", response.toString());

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RequestError", error.toString());
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
