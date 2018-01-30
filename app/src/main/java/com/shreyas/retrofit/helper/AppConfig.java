package com.shreyas.retrofit.helper;

/**
 * Created by User on 30-01-2018.
 */

import com.google.gson.JsonElement;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public class AppConfig {

    public interface insert {
        @FormUrlEncoded
        @POST("/read/create.php")
        void insertData(
                @Field("name") String name,
                @Field("num") String num,
                @Field("password") String password,
                Callback<Response> callback);
    }

    public interface read {
        @GET("/read/read.php")
        void readData(Callback<JsonElement> callback);
    }

    public interface delete {
        @FormUrlEncoded
        @POST("/read/delete.php")
        void deleteData(
                @Field("name") String name,
                Callback<Response> callback);
    }

    public interface update {
        @FormUrlEncoded
        @POST("/read/update.php")
        void updateData(
                @Field("name") String name,
                @Field("num") String num,
                @Field("password") String password,
                Callback<Response> callback);
    }

}