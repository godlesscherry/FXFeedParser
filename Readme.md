# Big Company's new FX feed
Big Company whose main currency is USD have signed a contract to receive a state of the art feed of exchange rates... via a file which is delivered to
them each day.
### An example file is below:
````
START-OF-FILE
DATE=20181015
START-OF-FIELD-LIST
CURRENCY
EXCHANGE_RATE
LAST_UPDATE
END-OF-FIELD-LIST
START-OF-EXCHANGE-RATES
CHF|0.9832|17:12:59 10/14/2018|
GBP|0.7849|17:12:59 10/14/2018|
EUR|0.8677|17:13:00 10/14/2018|
END-OF-EXCHANGE-RATES
END-OF-FILE
````
- The EXCHANGE_RATE is always against the US Dollar e.g. 1 USD = 0.7846 GBP.
- Each day a new file is delivered. Occasionally if a mistake has been made more than one file can be delivered intra-day.
- Big Company would like a program that continually processes these files and implements the following requirements:
- If a duplicate file is delivered it should be ignored
- If a given rate varies by more than 20% day on day this should be "flagged"
- A monthly average rate for each EXCHANGE_RATE should be calculated and published by the program
- A yearly average rate for each EXCHANGE_RATE should be calculated and published by the program

#### Some hints:
- To simplify the task you can ignore any interactions with the file system. For example you could implement a method:

```
public void sendFile(String fileContent)
```
- which you invoke to simulate a file being received.
- A program which writes its output to the "standard" output stream is enough.
- There is no persistence required, so when the program is terminated it is perfectly acceptable for it to lose all its state.

### Important:
- the only libraries which can be used are Java SE (any version) and JUnit (any version)
- Test Driven Development (TDD) is the recommended approach. In any case the solution must have automated tests
there is no Graphical User Interface (other than using the command line) required and no persistence. It's a simple program
if a requirement is ambiguous, make a sensible assumption
- Please deliver all artefacts, so the program and tests can be executed.