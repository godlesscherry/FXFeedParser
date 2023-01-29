import java.text.ParseException;

public class Main {
    private FileParser fileParser;


    public Main() {
        fileParser = new FileParser();

    }

    public void sendFile(String fileContent) throws ParseException {
        fileParser.parse(fileContent);
    }

    public static void main(String[] args) throws ParseException {
        Main main = new Main();
        String fileContent1 = "START-OF-FILE\nDATE=20181015\nSTART-OF-FIELD-LIST\nCURRENCY\nEXCHANGE_RATE\nLAST_UPDATE\nEND-OF-FIELD-LIST\nSTART-OF-EXCHANGE-RATES\nCHF|0.9832|17:12:59 10/14/2018|\nGBP|0.7849|17:12:59 10/14/2018|\nEUR|0.8677|17:13:00 10/14/2018|\nEND-OF-EXCHANGE-RATES\nEND-OF-FILE";
        String fileContent2 = "START-OF-FILE\nDATE=20181016\nSTART-OF-FIELD-LIST\nCURRENCY\nEXCHANGE_RATE\nLAST_UPDATE\nEND-OF-FIELD-LIST\nSTART-OF-EXCHANGE-RATES\nCHF|2.9832|17:12:59 10/14/2018|\nGBP|0.7849|17:12:59 10/14/2018|\nEUR|0.8677|17:13:00 10/14/2018|\nEND-OF-EXCHANGE-RATES\nEND-OF-FILE";

        main.sendFile(fileContent1);
        main.sendFile(fileContent2);
    }
}