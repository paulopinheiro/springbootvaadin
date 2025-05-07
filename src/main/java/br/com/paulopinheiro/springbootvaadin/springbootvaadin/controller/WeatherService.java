package br.com.paulopinheiro.springbootvaadin.springbootvaadin.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherService {
    private OkHttpClient client;
    private Response response;

    public JSONObject getWeather(String name) {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.openweathermap.org/data/2.5/weather?q="+name+"&units=metrics&APPID=54eef856e67127b942989bdecb657292").build();
        try {
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException ex) {
            Logger.getLogger(WeatherService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
