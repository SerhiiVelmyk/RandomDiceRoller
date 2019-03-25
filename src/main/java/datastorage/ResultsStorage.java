package datastorage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;

public class ResultsStorage {
    private static Hashtable<Integer, Integer> results = new Hashtable<>();
    private static HashMap<Integer, Float> percentageDeviation = new HashMap<>();

    public static void recordValue(int value) {
        if (results.get(value) == null) {
            results.put(value, 1);
        } else {
            int count = results.get(value);
            results.put(value, ++count);
        }
    }

    public static Hashtable<Integer, Integer> getResults() {
        return results;
    }

    public static void clearResults() {
        results.clear();
        percentageDeviation.clear();
    }

    public static HashMap<Integer, Float> getPercentageDeviation() {
        int minValue = Collections.min(results.values());
        results.forEach((key, value) -> percentageDeviation.put(key, ((float) value / minValue) * 100 - 100));

        return percentageDeviation;
    }
}
