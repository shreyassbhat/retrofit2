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
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateData extends AppCompatActivity {

    String BASE_URL = "http://swara.epizy.com";

    EditText edt_name, edt_num, edt_password;
    Button btn_update;

    String  name,num, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_name = (EditText) findViewById(R.id.name);
        edt_num = (EditText) findViewById(R.id.num);
        edt_password = (EditText) findViewById(R.id.password);

        btn_update = (Button) findViewById(R.id.update);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        num = i.getStringExtra("num");
        password = i.getStringExtra("password");

        edt_name.setText(name);
        edt_num.setText(num);
        edt_password.setText(password);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update_data(name);

            }
        });

    }

    public void update_data(String name) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.update api = adapter.create(AppConfig.update.class);

        api.updateData(
                edt_name.getText().toString(),
                edt_num.getText().toString(),
                edt_password.getText().toString(),

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

                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DisplayData.class));
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(UpdateData.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}