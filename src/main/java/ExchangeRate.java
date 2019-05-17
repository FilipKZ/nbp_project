
public class ExchangeRate {
    private String table, currency, code;
    private Rates[] rates = new Rates[]{};

    public ExchangeRate() {
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return Double.parseDouble(rates[0].getMid());
    }

    public void setRates(Rates[] rates) {
        this.rates = rates;
    }

    public double convertToPLN () {
        return 100 / this.getRate();
    }

    public Rates[] getRatesAll() {
        return rates;
    }
}
