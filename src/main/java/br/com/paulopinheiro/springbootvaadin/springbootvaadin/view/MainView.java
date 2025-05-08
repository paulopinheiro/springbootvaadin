package br.com.paulopinheiro.springbootvaadin.springbootvaadin.view;

import br.com.paulopinheiro.springbootvaadin.springbootvaadin.controller.WeatherService;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Coord;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Weather;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.WeatherReport;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends HorizontalLayout {
    @Autowired
    protected WeatherService weatherService;

    public MainView() {
        this.setWidth("200px");
        String city = "navegantes";
        if (weatherService == null) {
            System.out.println("Autowired failed!");
            weatherService = new WeatherService();
        }

        executeQuery(city);
    }

    private void executeQuery(String city) {
        WeatherReport weatherReport = weatherService.getWeatherReport(city);

        if (weatherReport!=null) {
            add(new Text("Weather report for " + city));
            showWeatherReport(weatherReport);
        }
        else {
            add(new Text("No weather report found for " + city));
        }
    }

    private void showWeatherReport(WeatherReport weatherReport) {
        showWeatherList(weatherReport.getWeatherList());
    }

    private void showCoord(Coord coord) {

    }

    private void showWeatherList(List<Weather> weatherList) {
        if (weatherList == null) {
            return;
        }
        String text = "Weather:\n";

        for (Weather w : weatherList) {
            text += "Id: " + String.valueOf(w.getId()) + "\n";
            text += "Main: " + w.getMain() + "\n";
            text += "Description: " + w.getDescription() + "\n";
            text += "Icon: " + w.getIcon() + "\n\n";
        }
        add(new TextArea(text));
    }
}
