package com.example.btngsn.Retrofit;


public class APIUtils {
    public static final String Base_Url = "http://192.168.1.12/batdongsan/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
