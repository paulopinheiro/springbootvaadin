package br.com.paulopinheiro.springbootvaadin.springbootvaadin.view;

import br.com.paulopinheiro.springbootvaadin.springbootvaadin.controller.WeatherService;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

@Route("")
public class MainView extends HorizontalLayout {
    //@Autowired - doesn't compile
    private WeatherService weatherService;

    public MainView() {
        try {
            //Unnecessary if @Autowired works
            weatherService = new WeatherService();
            System.out.println(weatherService.getWeather("mumbai").getString("coord"));
        } catch (JSONException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
