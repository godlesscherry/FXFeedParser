import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class FileParserTest {
    FileParser fileParser = new FileParser();
    @Test
    public void testParse() throws ParseException {
        // create a FileParser object
        FileParser fileParser = new FileParser();

        // create a test file content
        String fileContent = "DATE=20221101\nSTART-OF-EXCHANGE-RATES\nUSD|1.1712|15:44:01 01/11/2022\nGBP|1.3123|15:44:01 01/11/2022\nEUR|1.0987|15:44:01 01/11/2022\nEND-OF-EXCHANGE-RATES";

        // call the parse method on the fileParser object and store the returned list of ExchangeRate objects
        List<ExchangeRate> exchangeRates = fileParser.parse(fileContent);

        // assert that the list is not null
        assertNotNull(exchangeRates);

        // assert that the size of the list is 3
        assertEquals(3, exchangeRates.size());

        // assert that the first ExchangeRate object in the list has the expected currency, rate, and lastUpdate values
        assertEquals("USD", exchangeRates.get(0).getCurrency());
        assertEquals(1.1712, exchangeRates.get(0).getRate(), 0.0001);
        assertEquals("15:44:01 01/11/2022", exchangeRates.get(0).getLastUpdate());

        // repeat the above steps for the second and third ExchangeRate objects in the list
        assertEquals("GBP", exchangeRates.get(1).getCurrency());
        assertEquals(1.3123, exchangeRates.get(1).getRate(), 0.0001);
        assertEquals("15:44:01 01/11/2022", exchangeRates.get(1).getLastUpdate());

        assertEquals("EUR", exchangeRates.get(2).getCurrency());
        assertEquals(1.0987, exchangeRates.get(2).getRate(), 0.0001);
        assertEquals("15:44:01 01/11/2022", exchangeRates.get(2).getLastUpdate());
    }
    @Test
    public void testCheckLargeVariations() throws Exception {
        String fileContent1 = "START-OF-FILE\nDATE=20181015\nSTART-OF-FIELD-LIST\nCURRENCY\nEXCHANGE_RATE\nLAST_UPDATE\nEND-OF-FIELD-LIST\nSTART-OF-EXCHANGE-RATES\nCHF|0.9832|17:12:59 10/14/2018|\nGBP|0.7849|17:12:59 10/14/2018|\nEUR|0.8677|17:13:00 10/14/2018|\nEND-OF-EXCHANGE-RATES\nEND-OF-FILE";
        String fileContent2 = "START-OF-FILE\nDATE=20181016\nSTART-OF-FIELD-LIST\nCURRENCY\nEXCHANGE_RATE\nLAST_UPDATE\nEND-OF-FIELD-LIST\nSTART-OF-EXCHANGE-RATES\nCHF|2.9832|17:12:59 10/14/2018|\nGBP|0.7849|17:12:59 10/14/2018|\nEUR|0.8677|17:13:00 10/14/2018|\nEND-OF-EXCHANGE-RATES\nEND-OF-FILE";

        // check if console output has the string "Large variation detected in"
        // Capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        // Call the method
        fileParser.parse(fileContent1);
        fileParser.parse(fileContent2);
        // Restore the console output
        System.setOut(originalOut);
        // Check if the console output contains the expected string
        assertTrue(outContent.toString().contains("Large variation detected in"));
    }

    @Test
    public void testCheckDuplicate() {
        // Test with a new file
        String fileContent = "DATE=2022-01-01\nSTART-OF-EXCHANGE-RATES\nUSD|1.1|12:00:00 01/01/2022\nEUR|0.9|12:00:00 01/01/2022\nGBP|0.8|12:00:00 01/01/2022\nEND-OF-EXCHANGE-RATES\n";
        assertFalse(fileParser.checkDuplicate(fileContent));

        // Test with a duplicate file
        assertTrue(fileParser.checkDuplicate(fileContent));
    }
}

