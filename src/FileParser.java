import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
    private Set<String> processedFiles;
    private static final String DATE_PATTERN = "DATE=(\\d{8})";
    private static final String CURRENCY_PATTERN = "(\\w{3})\\|(\\d\\.\\d{4})\\|(\\d{2}:\\d{2}:\\d{2} \\d{2}/\\d{2}/\\d{4})";

    Map<String, List<ExchangeRate>> currencyExchangeRateMap;
    private Map<String, Double> currencyMonthlyAverageMap;
    private Map<String, Double> currencyYearlyAverageMap;

    ExchangeRateAverages exchangeRateAverages;


    public FileParser() {
        processedFiles = new HashSet<>();
        this.currencyExchangeRateMap = new HashMap<>();
        this.currencyMonthlyAverageMap = new HashMap<>();
        this.currencyYearlyAverageMap = new HashMap<>();
        this.exchangeRateAverages = new ExchangeRateAverages();
    }

    public List<ExchangeRate> parse(String fileContent) throws ParseException {
        if (checkDuplicate(fileContent)) {
            System.out.println("Duplicate file detected!");
            return null;
        }
        // logic for parsing the file and creating ExchangeRate object
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        // Extract date
        Matcher dateMatcher = Pattern.compile(DATE_PATTERN).matcher(fileContent);
        String date = null;
        if (dateMatcher.find()) {
            date = (dateMatcher.group(1));
        }

        //check for the line START-OF-EXCHANGE-RATES
        if (fileContent.contains("START-OF-EXCHANGE-RATES")) {
            //check for the line END-OF-EXCHANGE-RATES
            if (fileContent.contains("END-OF-EXCHANGE-RATES")) {
                //extract the currencies and exchange rates
                Matcher currencyMatcher = Pattern.compile(CURRENCY_PATTERN).matcher(fileContent);

                System.out.println("#######  Parsing exchange rates parsed from a new file! ########"); //debugging
                while (currencyMatcher.find()) {
                    String currency = currencyMatcher.group(1);
                    double rate = Double.parseDouble(currencyMatcher.group(2));
                    String lastUpdate = currencyMatcher.group(3);
                    ExchangeRate exchangeRate = new ExchangeRate(currency, rate, lastUpdate);
                    System.out.println(exchangeRate.getCurrency() + " " + exchangeRate.getRate() + " " + exchangeRate.getLastUpdate());
                    exchangeRates.add(exchangeRate);
                    exchangeRateAverages.addExchangeRate(date, currency, rate);
                }
            }
        }
        //add exchangeRates and date to currencyExchange
        currencyExchangeRateMap.put(date, exchangeRates);
        // call checkLargeVariations
        checkLargeVariations(date, exchangeRates);
        exchangeRateAverages.printMonthlyAverages();
        exchangeRateAverages.printYearlyAverages();

        return exchangeRates;

    }

    boolean checkDuplicate(String fileContent) {
        if (processedFiles.contains(fileContent)) {
            return true;
        }
        processedFiles.add(fileContent);
        return false;
    }


    void checkLargeVariations(String date, List<ExchangeRate> exchangeRates) throws ParseException {
        // check the previous day's exchange rates for the given currency and compare it with the current day's exchange rate
        // if the difference is more than 20%, then print the currency and the difference
        // if there is no previous day's exchange rate, then ignore

        //calculate the date string for the previous day
        String previousDate = calculatePreviousDate(date);
        //get the previous day's exchange rates
        List<ExchangeRate> previousDayExchangeRates = currencyExchangeRateMap.get(previousDate);
        //check if the previous day's exchange rates are available
        if (previousDayExchangeRates != null) {
            //iterate through the current day's exchange rates
            for (ExchangeRate exchangeRate : exchangeRates) {
                //get the currency
                String currency = exchangeRate.getCurrency();
                //get the current day's exchange rate
                double currentDayExchangeRate = exchangeRate.getRate();
                //get the previous day's exchange rate for the same currency
                double previousDayExchangeRate = getPreviousDayExchangeRate(previousDayExchangeRates, currency);
                //check if the previous day's exchange rate is available
                if (previousDayExchangeRate != 0) {
                    //calculate the difference
                    double difference = currentDayExchangeRate - previousDayExchangeRate;
                    //check if the difference is more than 20%
                    if (difference > 0.2) {
                        //print the currency and the difference
                        System.out.println("Large variation detected in " + currency + " difference was: " + difference);
                    }
                }
            }
        }

    }

    private double getPreviousDayExchangeRate(List<ExchangeRate> previousDayExchangeRates, String currency) {
        //iterate through the previous day's exchange rates
        for (ExchangeRate exchangeRate : previousDayExchangeRates) {
            //check if the currency matches
            if (exchangeRate.getCurrency().equals(currency)) {
                //return the exchange rate
                return exchangeRate.getRate();
            }
        }
        return 0;
    }

    private String calculatePreviousDate(String dateString) throws ParseException {
        // logic for calculating the previous date, given today's date
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        // Get a Date object from the date string
        Date myDate = dateFormat.parse(dateString);

        Date oneDayBefore = new Date(myDate.getTime() - 1);

        String result = dateFormat.format(oneDayBefore);
        return result;
    }

}

