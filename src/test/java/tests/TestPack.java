package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import runner.Executor;

import java.util.TreeSet;

import static datastorage.ResultsStorage.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.ChartBuilder.drawChart;

class TestPack extends Executor {
    private static final int ROLL_COUNT = 1000;
    private static final int POOL_SIZE = 100;
    private static final String URL_TEMPLATE = "https://www.random.org/dice/?num=%s";

    @BeforeEach
    void setUp() {
        clearResults();
    }

    @ParameterizedTest(name = "Verify max deviation for {arguments} dice")
    @ValueSource(ints = {1, 2})
    void testDiceRoller(int diceNumber) {
        String url = String.format(URL_TEMPLATE, diceNumber);
        executeDiceRoller(POOL_SIZE, ROLL_COUNT, url);

        TreeSet<Integer> values = new TreeSet<>(getResults().values());
        float maxDeviation = ((float) values.last() / values.first()) * 100 - 100;

        assertTrue(maxDeviation <= 5,
                "\nMaximum deviation of dice results is " + maxDeviation + "% but should be less than 5%."
                        + "\nThe dice rolling results are " + getResults().entrySet()
                        + "\nThe dice deviation results are " + getPercentageDeviation().entrySet());
    }

    @AfterEach
    void tearDown() {
        drawChart(ROLL_COUNT);
    }
}
