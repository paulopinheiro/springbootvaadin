package br.com.paulopinheiro.springbootvaadin.springbootvaadin.controller;

import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Clouds;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Coord;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Main;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Sys;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Weather;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.WeatherReport;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Wind;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final static String APIKEY = "54eef856e67127b942989bdecb657292";
    private final static String WEATHERURL = "http://api.openweathermap.org/data/2.5/weather";
    private final OkHttpClient client = new OkHttpClient();

    public WeatherReport getWeatherReport(String cityName) {
        WeatherReport weatherReport = null;
        try {
            JSONObject jsonWeatherReport = getJsonWeatherReport(cityName);
            if (jsonWeatherReport != null) {
                System.out.println(jsonWeatherReport.toString());
                Coord coord = jsonWeatherReport.isNull("coord") ? null : getCoord(jsonWeatherReport.getJSONObject("coord"));
                List<Weather> weatherList = jsonWeatherReport.isNull("weather") ? null : getWeatherList(jsonWeatherReport.getJSONArray("weather"));
                String base = jsonWeatherReport.isNull("base") ? null : jsonWeatherReport.getString("base");
                Main main = jsonWeatherReport.isNull("main") ? null : getMain(jsonWeatherReport.getJSONObject("main"));
                Integer visibility = jsonWeatherReport.isNull("visibility") ? null : jsonWeatherReport.getInt("visibility");
                Wind wind = jsonWeatherReport.isNull("wind") ? null : getWind(jsonWeatherReport.getJSONObject("wind"));
                Clouds clouds = jsonWeatherReport.isNull("clouds") ? null : getClouds(jsonWeatherReport.getJSONObject("clouds"));
                Timestamp dt = jsonWeatherReport.isNull("dt") ? null : new Timestamp(jsonWeatherReport.getLong("dt"));
                Sys sys = jsonWeatherReport.isNull("sys") ? null : getSys(jsonWeatherReport.getJSONObject("sys"));
                Integer id = jsonWeatherReport.isNull("id") ? null : jsonWeatherReport.getInt("id");
                String name = jsonWeatherReport.isNull("name") ? null : jsonWeatherReport.getString("name");
                Integer cod = jsonWeatherReport.isNull("cod") ? null : jsonWeatherReport.getInt("cod");

                weatherReport = new WeatherReport(coord, weatherList, base, main,
                                                  visibility, wind, clouds, dt, sys,
                                                  id, name, cod);
            }
        } catch (JSONException | IOException ex) {
            Logger.getLogger(WeatherService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return weatherReport;
    }

    private JSONObject getJsonWeatherReport(String name) throws JSONException, IOException {
        Request request = new Request.Builder().url(WEATHERURL + "?q=" + name
                + "&units=metrics"
                + "&APPID=" + APIKEY).build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return new JSONObject(response.body().string());
        }

        return null;
    }

    private Coord getCoord(JSONObject jsonCoord) throws JSONException {
        if (jsonCoord==null) return null;
        if (jsonCoord.isNull("lat")||jsonCoord.isNull("lon")) return null;

        return new Coord(jsonCoord.getDouble("lat"), jsonCoord.getDouble("lon"));
    }

    private List<Weather> getWeatherList(JSONArray jsonWeatherList) {
        if (jsonWeatherList==null) return null;

        List<Weather> weatherList = new ArrayList<>();
        for (int i = 0; i < jsonWeatherList.length(); i++) {
            try {
                weatherList.add(getWeather(jsonWeatherList.getJSONObject(i)));
            } catch (JSONException ex) {}
        }
        return weatherList;
    }

    private Weather getWeather(JSONObject jsonWeather) throws JSONException {
        if (jsonWeather==null) return null;

        return new Weather(jsonWeather.isNull("id") ? null : jsonWeather.getInt("id"),
                           jsonWeather.isNull("main") ? null : jsonWeather.getString("main"),
                           jsonWeather.isNull("description") ? null : jsonWeather.getString("description"),
                           jsonWeather.isNull("icon") ? null : jsonWeather.getString("icon"));
    }

    private Main getMain(JSONObject jsonMain) throws JSONException {
        if (jsonMain==null) return null;

        return new Main(jsonMain.isNull("temp") ? null : jsonMain.getInt("temp"),
                        jsonMain.isNull("pressure") ? null : jsonMain.getInt("pressure"),
                        jsonMain.isNull("humidity") ? null : jsonMain.getInt("humidity"),
                        jsonMain.isNull("temp_min") ? null : jsonMain.getInt("temp_min"),
                        jsonMain.isNull("temp_max") ? null : jsonMain.getInt("temp_max"));
    }

    private Wind getWind(JSONObject jsonWind) throws JSONException {
        if (jsonWind==null) return null;
        if (jsonWind.isNull("speed")||jsonWind.isNull("deg")) return null;

        return new Wind(jsonWind.getDouble("speed"),
                         jsonWind.getDouble("deg"));
    }

    private Clouds getClouds(JSONObject jsonClouds) throws JSONException {
        if (jsonClouds==null) return null;

        return jsonClouds.isNull("all") ? null : new Clouds(jsonClouds.getInt("all"));
    }

    private Sys getSys(JSONObject jsonSys) throws JSONException {
        if (jsonSys == null) return null;

        return new Sys(jsonSys.isNull("type") ? null : jsonSys.getInt("type"),
                       jsonSys.isNull("id") ? null : jsonSys.getInt("id"),
                       jsonSys.isNull("country") ? null : jsonSys.getString("country"),
                       jsonSys.isNull("sunrise") ? null : new Timestamp(jsonSys.getLong("sunrise")),
                       jsonSys.isNull("sunset") ? null : new Timestamp(jsonSys.getLong("sunset")));

    }
}
