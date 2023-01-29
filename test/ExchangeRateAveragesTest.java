import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import org.junit.Test;

public class ExchangeRateAveragesTest {
    @Test
    public void testAddExchangeRate() {
        ExchangeRateAverages averages = new ExchangeRateAverages();
        averages.addExchangeRate("20210101", "USD", 1.0);
        averages.addExchangeRate("20210101", "EUR", 0.9);
        averages.addExchangeRate("20210102", "USD", 1.1);
        averages.addExchangeRate("20210201", "USD", 1.2);
        averages.addExchangeRate("20210201", "EUR", 0.8);
        averages.addExchangeRate("20210301", "USD", 1.3);
        averages.addExchangeRate("20210401", "USD", 1.4);
        averages.addExchangeRate("20210401", "EUR", 0.7);

        Map<String, Map<String, Double>> monthlyAverages = averages.getMonthlyAverages();
        assertEquals(1.05, monthlyAverages.get("202101").get("USD"), 0.001);
        assertEquals(0.9, monthlyAverages.get("202101").get("EUR"), 0.001);
        assertEquals(1.2, monthlyAverages.get("202102").get("USD"), 0.001);
        assertEquals(0.8, monthlyAverages.get("202102").get("EUR"), 0.001);
        assertEquals(1.3, monthlyAverages.get("202103").get("USD"), 0.001);
        assertEquals(1.4, monthlyAverages.get("202104").get("USD"), 0.001);
        assertEquals(0.7, monthlyAverages.get("202104").get("EUR"), 0.001);

        Map<String, Map<String, Double>> yearlyAverages = averages.getYearlyAverages();
        assertEquals(1.2, yearlyAverages.get("2021").get("USD"), 0.001);

    }

    @Test
    public void testPrintYearlyAverages() {
        ExchangeRateAverages averages = new ExchangeRateAverages();
        averages.addExchangeRate("2022-01-01", "USD", 1.2);
        averages.addExchangeRate("2022-02-01", "USD", 1.3);
        averages.addExchangeRate("2022-03-01", "USD", 1.4);
        averages.addExchangeRate("2021-01-01", "EUR", 0.9);


        // Capture the output of the printYearlyAverages method
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        averages.printYearlyAverages();

        // Assert that the output is as expected
        String expectedOutput = "Updated Yearly averages\n" +
                "year: 2022\n" +
                "USD 1.3\n" +
                "year: 2021\n" +
                "EUR 0.9\n";
        expectedOutput = expectedOutput.replace("\n", System.lineSeparator());
        assertEquals(expectedOutput, outContent.toString());

        // Reset the output stream
        System.setOut(System.out);
    }

    @Test
    public void testPrintMonthlyAverages(){
        ExchangeRateAverages exchangeRateAverages = new ExchangeRateAverages();
        exchangeRateAverages.addExchangeRate("2022-01-01", "USD", 1.0);
        exchangeRateAverages.addExchangeRate("2022-01-01", "EUR", 1.2);
        exchangeRateAverages.addExchangeRate("2022-02-01", "USD", 2.0);
        exchangeRateAverages.addExchangeRate("2022-02-01", "EUR", 2.4);
        exchangeRateAverages.printMonthlyAverages();
    }



}