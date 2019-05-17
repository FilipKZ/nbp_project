import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Scanner;

public class NBPConnect {

    private URL nbpURL;

    public NBPConnect() {
    }

    public NBPConnect(String currency) throws MalformedURLException {
        this.nbpURL = new URL("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/");
    }

    public NBPConnect(String currency, LocalDate date) throws MalformedURLException {
        this.nbpURL = new URL("http://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/" + date + "/?format=json");
    }

    private String establishConnection() throws IOException {
        URLConnection nbpConnection = nbpURL.openConnection();
        Scanner scanner = new Scanner(nbpConnection.getInputStream());
        return scanner.nextLine();

    }

    private void setNbpURL(String currency) throws MalformedURLException {
        this.nbpURL = new URL("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/");
    }

    private void setNbpURL(String currency, LocalDate date) throws MalformedURLException {
        this.nbpURL = new URL("http://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/" + date + "/?format=json");
    }

    public double getExchangeRateToday (String currency) throws IOException {
        Gson gson = new Gson();
        this.setNbpURL(currency);
        ExchangeRate exchangeRateToday = gson.fromJson(this.establishConnection(), ExchangeRate.class);
        return exchangeRateToday.getRate();
    }

    public double getPastExchangeRate (String currency, LocalDate pastDate) throws IOException {
        Gson gson = new Gson();
        this.nbpURL = new URL ("http://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/" + pastDate + "/?format=json");
        ExchangeRate exchangeRatePast = gson.fromJson(this.establishConnection(), ExchangeRate.class);
        return exchangeRatePast.getRate();
    }

    public String getConversion(String currency) throws IOException {
        Gson gson = new Gson();
        this.setNbpURL(currency);
        ExchangeRate exchangeRateToday = gson.fromJson(this.establishConnection(), ExchangeRate.class);
        return String.valueOf(System.out.printf("100 zł to: %.2f %s\n", exchangeRateToday.convertToPLN(), currency));
    }

    public String getBidToday (String currency) throws IOException {
        Gson gson = new Gson();
        this.nbpURL = new URL("http://api.nbp.pl/api/exchangerates/rates/c/" + currency + "/today/");
        ExchangeRate exchangeRateToday = gson.fromJson(this.establishConnection(), ExchangeRate.class);
        return exchangeRateToday.getRatesAll()[0].getBid();
    }

    public String getAskLastMonth (String currency) throws IOException {
        Gson gson = new Gson();
        this.setNbpURL(currency, LocalDate.now().minusMonths(1));
        ExchangeRate exchangeRateLastMonth = gson.fromJson(this.establishConnection(), ExchangeRate.class);
        return exchangeRateLastMonth.getRatesAll()[0].getAsk();
    }

    public String compareToLastMonth (String currency) throws IOException {
        double pastConversion = 100 / Double.parseDouble(this.getAskLastMonth(currency));
        double todaysConversion = pastConversion * Double.parseDouble(this.getBidToday(currency));
        double difference = pastConversion - todaysConversion;
        if (difference > 0) {
            return String.valueOf(System.out.printf("Dokonując zamiany 100 zł miesiąc temu uzyskaliśmy: %.2f %s, " +
                    "a dzisiaj %.2f zł.\nZyskaliśmy: %.2f\n",pastConversion, currency, todaysConversion, difference));
        } else if (difference < 0) {
            return String.valueOf(System.out.printf("Dokonując zamiany 100 zł miesiąc temu uzyskaliśmy: %.2f %s, " +
                    "a dzisiaj %.2f zł.\nStraciliśmy: %.2f\n",pastConversion, currency, todaysConversion, difference));
        } else {
            return String.valueOf(System.out.printf("Dokonując zamiany 100 zł miesiąc temu uzyskaliśmy: %.2f %s, " +
                    "a dzisiaj %.2f zł.\nNic się nie zmieniło\n", pastConversion, currency, todaysConversion));
        }
    }
}
