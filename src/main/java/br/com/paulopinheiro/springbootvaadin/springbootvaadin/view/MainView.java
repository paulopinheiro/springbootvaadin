package br.com.paulopinheiro.springbootvaadin.springbootvaadin.view;

import br.com.paulopinheiro.springbootvaadin.springbootvaadin.controller.WeatherService;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.Weather;
import br.com.paulopinheiro.springbootvaadin.springbootvaadin.model.WeatherReport;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {
    private static final String ICON_URL = "http://openweathermap.org/img/w/";

    @Autowired
    private WeatherService weatherService;

    private TextField cityTextField;
    private Button queryButton;
    private VerticalLayout dashboard = new VerticalLayout();

    public MainView() {
        // That shouldn't be necessary
        // Springboot shoud inject weatherService with @Autowired
        if (weatherService == null) {
            System.out.println("Autowired failed!");
            weatherService = new WeatherService();
        }

        setUpLayout();
        setUpForm();
        setUpDashboard();
    }

    private void setUpLayout() {
        setWidth("100%");
        setMargin(true);
        setSpacing(true);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

    private void setUpForm() {
        HorizontalLayout formLayout = new HorizontalLayout();
        formLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        formLayout.setSpacing(true);
        formLayout.setMargin(true);

        cityTextField = new TextField();
        cityTextField.setLabel("City");
        cityTextField.setTitle("City");
        cityTextField.setWidth("80%");
        add(cityTextField);

        queryButton = new Button();
        queryButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        queryButton.setText("Query");
        queryButton.addClickListener(event -> {
            executeQuery(this.cityTextField.getValue());
        });
        
        add(queryButton);
    }

    private void setUpDashboard() {
        dashboard.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dashboard.setWidthFull();
        add(dashboard);
    }

    private void executeQuery(String city) {
        WeatherReport weatherReport = weatherService.getWeatherReport(city);
        dashboard.removeAll();

        if (weatherReport!=null) {
            updateDashboard(weatherReport);
        }
        else {
           dashboard.add(new Text("No weather report found for " + city));
        }
    }

    private void updateDashboard(WeatherReport weatherReport) {
        Span title = new Span("Weather report for " + weatherReport.getName() +
                              " - " + weatherReport.getSys().getCountry());
        title.getStyle().set("color", "blue");
        title.getStyle().set("font-family", "Arial");
        title.getStyle().set("font-size", "48px");
        dashboard.add(title);

        dashboard.add(getWeatherListLayout(weatherReport.getWeatherList()));
        
    }
 
    private HorizontalLayout getWeatherListLayout(List<Weather> weatherList) {
        HorizontalLayout weatherListLayout = new HorizontalLayout();
        if (weatherList != null) {
            for (Weather w : weatherList) 
                weatherListLayout.add(getWeatherLayout(w));
        }

        return weatherListLayout;
    }

    private HorizontalLayout getWeatherLayout(Weather weather) {
        HorizontalLayout weatherLayout = new HorizontalLayout();
        weatherLayout.getElement().getStyle().set("border","1px solid black");
        weatherLayout.setMargin(true);
        weatherLayout.setSpacing(true);

        String url = ICON_URL + weather.getIcon() + ".png";

        Image image = new Image(url,url);
        image.setWidth("100px");
        image.setHeight("100px");

        String title = "Weather id: " + weather.getId();
        String text = "Main: " + weather.getMain() + "\n";
        text+= "Description: " + weather.getDescription();

        TextArea weatherTextArea = new TextArea();
        weatherTextArea.setTitle(title);
        weatherTextArea.setLabel(title);
        weatherTextArea.setValue(text);
        weatherTextArea.setWidthFull();

        weatherLayout.add(image, weatherTextArea);
        return weatherLayout;
    }
}
