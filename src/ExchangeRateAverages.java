import java.util.HashMap;
import java.util.Map;

public class ExchangeRateAverages {
    // Key: yyyyMM, Value: Map of currency and average rate
    private Map<String, Map<String, Double>> monthlyAverages;
    // Key: yyyy, Value: Map of currency and average rate
    private Map<String, Map<String, Double>> yearlyAverages;
    // Key: yyyyMM, Value: Map of currency and total occurrences
    private Map<String, Map<String, Integer>> monthlyCounts;
    // Key: yyyy, Value: Map of currency and total occurrences
    private Map<String, Map<String, Integer>> yearlyCounts;

    public ExchangeRateAverages() {
        monthlyAverages = new HashMap<>();
        yearlyAverages = new HashMap<>();
        monthlyCounts = new HashMap<>();
        yearlyCounts = new HashMap<>();
    }

    public void addExchangeRate(String date, String currency, double rate) {
        // Extract year and month from date
        String year = date.substring(0, 4);
        String month = date.substring(0, 6);

        // Add to monthly averages
        Map<String, Double> monthAverages = monthlyAverages.get(month);
        Map<String, Integer> monthCounts = monthlyCounts.get(month);
        if (monthAverages == null) {
            monthAverages = new HashMap<>();
            monthCounts = new HashMap<>();
            monthlyAverages.put(month, monthAverages);
            monthlyCounts.put(month, monthCounts);
        }
        Integer count = monthCounts.get(currency);
        if (count == null) {
            count = 0;
        }
        monthAverages.put(currency, (monthAverages.get(currency) == null ? rate : (monthAverages.get(currency) * count + rate) / (count + 1)));
        monthCounts.put(currency, count + 1);

        // Add to yearly averages
        Map<String, Double> yearAverages = yearlyAverages.get(year);
        Map<String, Integer> yearCounts = yearlyCounts.get(year);
        if (yearAverages == null) {
            yearAverages = new HashMap<>();
            yearCounts = new HashMap<>();
            yearlyAverages.put(year, yearAverages);
            yearlyCounts.put(year, yearCounts);
        }
        count = yearCounts.get(currency);
        if (count == null) {
            count = 0;
        }
        yearAverages.put(currency, (yearAverages.get(currency) == null ? rate : (yearAverages.get(currency) * count + rate) / (count + 1)));
        yearCounts.put(currency, count + 1);
    }

    public Map<String, Map<String, Double>> getMonthlyAverages() {
        return monthlyAverages;
    }

    public Map<String, Map<String, Double>> getYearlyAverages() {
        return yearlyAverages;
    }

    public void printYearlyAverages(){
        System.out.println("Updated Yearly averages");
        for (Map.Entry<String, Map<String, Double>> entry : yearlyAverages.entrySet()) {
            System.out.println("year: "+ entry.getKey());
            for (Map.Entry<String, Double> entry2 : entry.getValue().entrySet()) {
                System.out.println(entry2.getKey() + " " + entry2.getValue());
            }
        }
    }

    public void printMonthlyAverages(){
        System.out.println("Updated Monthly averages:");
        for (Map.Entry<String, Map<String, Double>> entry : monthlyAverages.entrySet()) {
            System.out.println("month: " + entry.getKey());
            for (Map.Entry<String, Double> entry2 : entry.getValue().entrySet()) {
                System.out.println(entry2.getKey() + " " + entry2.getValue());
            }
        }
    }
}
