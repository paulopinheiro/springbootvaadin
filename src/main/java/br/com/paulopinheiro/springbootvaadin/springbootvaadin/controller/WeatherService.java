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
                Coord coord = getCoord(jsonWeatherReport.getJSONObject("coord"));
                List<Weather> weatherList = getWeatherList(jsonWeatherReport.getJSONArray("weather"));
                String base = jsonWeatherReport.getString("base");
                Main main = getMain(jsonWeatherReport.getJSONObject("main"));
                Integer visibility = jsonWeatherReport.getInt("visibility");
                Wind wind = getWind(jsonWeatherReport.getJSONObject("wind"));
                Clouds clouds = new Clouds(jsonWeatherReport.getJSONObject("clouds").getInt("all"));
                Timestamp dt = new Timestamp(jsonWeatherReport.getLong("dt"));
                Sys sys = getSys(jsonWeatherReport.getJSONObject("sys"));
                Integer id = jsonWeatherReport.getInt("id");
                String name = jsonWeatherReport.getString("name");
                Integer cod = jsonWeatherReport.getInt("cod");

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

    private Coord getCoord(JSONObject jsonCoord) {
        Coord coord = null;

        if (jsonCoord != null) {
            try {
                coord = new Coord(jsonCoord.getDouble("lat"), jsonCoord.getDouble("lon"));
            } catch (JSONException ex) {
                Logger.getLogger(WeatherService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return coord;
    }

    private List<Weather> getWeatherList(JSONArray jsonWeatherList) {
        List<Weather> weatherList = new ArrayList<>();
        if (jsonWeatherList != null) {
            for (int i = 0; i < jsonWeatherList.length(); i++) {
                try {
                    JSONObject jsonWeather = jsonWeatherList.getJSONObject(i);
                    weatherList.add(new Weather(jsonWeather.getInt("id"),
                            jsonWeather.getString("main"),
                            jsonWeather.getString("description"),
                            jsonWeather.getString("icon")));
                } catch (JSONException ex) {
                    Logger.getLogger(WeatherService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        return weatherList;
    }

    private Main getMain(JSONObject jsonMain) {
        Main main = null;

        if (jsonMain != null) {
            try {
                main = new Main(jsonMain.getInt("temp"),
                        jsonMain.getInt("pressure"),
                        jsonMain.getInt("humidity"),
                        jsonMain.getInt("temp_min"),
                        jsonMain.getInt("temp_max"));
            } catch (JSONException ex) {
                Logger.getLogger(WeatherService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return main;
    }

    private Wind getWind(JSONObject jsonWind) {
        Wind wind = null;

        if (jsonWind != null) {
            try {
                wind = new Wind(jsonWind.getDouble("speed"),
                        jsonWind.getDouble("deg"));
            } catch (JSONException ex) {
                Logger.getLogger(WeatherService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return wind;
    }

    private Sys getSys(JSONObject jsonSys) {
        Sys sys = null;

        if (jsonSys != null) {
            try {
                sys = new Sys(jsonSys.getInt("type"),
                        jsonSys.getInt("id"),
                        jsonSys.getString("country"),
                        new Timestamp(jsonSys.getLong("sunrise")),
                        new Timestamp(jsonSys.getLong("sunset")));
            } catch (JSONException ex) {
                Logger.getLogger(WeatherService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return sys;
    }
}
