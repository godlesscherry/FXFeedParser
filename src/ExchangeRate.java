import java.time.LocalDateTime;

public class ExchangeRate {
    private String currency;
    private double rate;
    private String lastUpdate;

    public ExchangeRate(String currency, double rate, String lastUpdate) {
        this.currency = currency;
        this.rate = rate;
        this.lastUpdate = lastUpdate;
    }


    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }
}
