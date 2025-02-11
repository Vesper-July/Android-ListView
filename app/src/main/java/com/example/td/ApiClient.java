package com.example.td;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {

    private static final String BASE_URL = "http://192.168.1.5:8080/";
    private static ItemsApiService apiService;

    public static ItemsApiService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ItemsApiService.class);
        }
        return apiService;
    }

}
