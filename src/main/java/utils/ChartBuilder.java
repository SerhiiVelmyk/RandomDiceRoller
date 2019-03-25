package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static datastorage.ResultsStorage.getPercentageDeviation;
import static utils.HTMLParser.getDiceCount;

public class ChartBuilder {
    private static String sep = File.separator;
    private static String pathToImagesFolder = System.getProperty("user.dir")
            + sep + "src" + sep + "test" + sep + "resources" + sep + "outputCharts" + sep;

    public static void drawChart(int rollCount) {
        XYSeries series = new XYSeries("");
        getPercentageDeviation().forEach(series::add);

        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                "Dice point",
                "% of deviation",
                dataSet,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        chart.setTitle(new TextTitle("Dice results deviation for " + rollCount + " rolls",
                        new Font("Arial", java.awt.Font.BOLD, 16)
                )
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        NumberAxis axis = (NumberAxis) plot.getDomainAxis();
        axis.setTickUnit(new NumberTickUnit(1.0));

        String imageName = "DeviationChart" + getDiceCount() + "Dice" + rollCount + "Rolls.png";

        try {
            ChartUtils.saveChartAsPNG(new File(pathToImagesFolder + imageName), chart, 550, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
