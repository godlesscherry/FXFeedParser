import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExchangeRateTest {

    @Test
    public void testGetCurrency() {
        ExchangeRate exchangeRate = new ExchangeRate("USD", 1.2, "2022-01-01");
        assertEquals("USD", exchangeRate.getCurrency());
    }

    @Test
    public void testGetRate() {
        ExchangeRate exchangeRate = new ExchangeRate("USD", 1.2, "2022-01-01");
        assertEquals(1.2, exchangeRate.getRate(), 0);
    }

    @Test
    public void testGetLastUpdate() {
        ExchangeRate exchangeRate = new ExchangeRate("USD", 1.2, "2022-01-01");
        assertEquals("2022-01-01", exchangeRate.getLastUpdate());
    }
}
