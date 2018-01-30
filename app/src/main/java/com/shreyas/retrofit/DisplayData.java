package com.shreyas.retrofit;

/**
 * Created by User on 30-01-2018.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.shreyas.retrofit.helper.AppConfig;
import com.shreyas.retrofit.helper.ListViewAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DisplayData extends AppCompatActivity {

    String BASE_URL = "http://swara.epizy.com";

    ListView details_list;
    ListViewAdapter displayAdapter;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> num = new ArrayList<>();
    ArrayList<String> password = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        details_list = (ListView) findViewById(R.id.retrieve);
        displayAdapter = new ListViewAdapter(getApplicationContext(), name, num, password);
        displayData();

        details_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {
                Intent i = new Intent(getApplicationContext(),UpdateData.class);
                i.putExtra("name",name.get(position));
                i.putExtra("num",num.get(position));
                i.putExtra("password",password.get(position));
                startActivity(i);

            }
        });

        details_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long ids) {
                delete(name.get(position));
                return true;
            }
        });
    }


    public void displayData() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.read api = adapter.create(AppConfig.read.class);

        api.readData(new Callback<JsonElement>() {
                         @Override
                         public void success(JsonElement result, Response response) {

                             String myResponse = result.toString();
                             Log.d("response", "" + myResponse);

                             try {
                                 JSONObject jObj = new JSONObject(myResponse);

                                 int success = jObj.getInt("success");

                                 if (success == 1) {

                                     JSONArray jsonArray = jObj.getJSONArray("details");
                                     for (int i = 0; i < jsonArray.length(); i++) {

                                         JSONObject jo = jsonArray.getJSONObject(i);

                                         name.add(jo.getString("name"));
                                         num.add(jo.getString("num"));
                                         password.add(jo.getString("password"));

                                     }

                                     details_list.setAdapter(displayAdapter);

                                 } else {
                                     Toast.makeText(getApplicationContext(), "No Details Found", Toast.LENGTH_SHORT).show();
                                 }
                             } catch (JSONException e) {
                                 Log.d("exception", e.toString());
                             }
                         }

                         @Override
                         public void failure(RetrofitError error) {
                             Log.d("Failure", error.toString());
                             Toast.makeText(DisplayData.this, error.toString(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    public void delete(String name){

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.delete api = adapter.create(AppConfig.delete.class);

        api.deleteData(
                name,
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int success = jObj.getInt("success");

                            if(success == 1){
                                Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                                recreate();
                            } else{
                                Toast.makeText(getApplicationContext(), "Deletion Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(DisplayData.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

}
