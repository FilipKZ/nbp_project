import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        NBPConnect nbpConnect = new NBPConnect();

        String currency = "eur";
        nbpConnect.getConversion(currency);
        nbpConnect.compareToLastMonth(currency);

        System.out.println();
        currency = "gbp";
        nbpConnect.getConversion(currency);
        nbpConnect.compareToLastMonth(currency);

        System.out.println();
        currency = "chf";
        nbpConnect.getConversion(currency);
        nbpConnect.compareToLastMonth(currency);

        System.out.println();
        currency = "usd";
        nbpConnect.getConversion(currency);
        nbpConnect.compareToLastMonth(currency);
        

    }

}
