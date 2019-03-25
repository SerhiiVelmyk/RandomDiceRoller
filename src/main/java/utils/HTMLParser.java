package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

class HTMLParser {

    private static List<String> listDiceValues;

    static int getDiceCount() {
        return listDiceValues.size();
    }

    static int getDiceSumValueFromHTML(String html) {
        Document doc = Jsoup.parse(html);
        int sum = 0;
        listDiceValues = doc.getElementsByAttributeValueContaining("src", "dice").eachAttr("alt");

        for (String listDiceValue : listDiceValues) {
            sum += Integer.parseInt(listDiceValue);
        }

        return sum;
    }
}
