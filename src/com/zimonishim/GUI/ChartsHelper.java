package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.util.StatisticsHelper;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ChartsHelper {

    public static Pane getChartsPane(Map<String, Collection<ResultEntry>> results){
        TilePane tilePane = new TilePane();

        results.forEach((s, resultEntryCollection) -> {
            tilePane.getChildren().add(getChartScrollPane(getBarChart(s, resultEntryCollection)));
            tilePane.getChildren().add(getChartScrollPane(getScatterChart(s, resultEntryCollection)));
        });

        return tilePane;
    }

    private static ScrollPane getChartScrollPane(Chart chart){
        ScrollPane scrollPane = new ScrollPane(chart);

        scrollPane.maxHeight(500);
        scrollPane.maxWidth(500);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        //TODO: Create zoom in.

        return scrollPane;
    }

    private static BarChart<String, Number> getBarChart(String title, Collection<ResultEntry> results){

        //Setting the Axis.
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Collections");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Time in NS");

        //Initializing the chart and setting the title.
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);

        //Here we set the Data.
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("Average"); //TODO: Make series for differentAmounts.

            //Retrieving data from StatisticsHelper.
        StatisticsHelper.getAverageOfAllCollections(results).forEach((s, aLong) -> dataSeries1.getData().add(new XYChart.Data<>(s, aLong)));

        barChart.getData().add(dataSeries1);

        return barChart;
    }

    private static ScatterChart<Number, Number> getScatterChart(String title, Collection<ResultEntry> results){

        //Setting the Axis.
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Collections");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Time in NS");

        //Initializing the chart and setting the title.
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle(title);

        //Retrieving data from StatisticsHelper.
        for (Map.Entry<String, Set<Long>> entry : StatisticsHelper.getCollectionMap(results).entrySet()) {
            String s = entry.getKey();
            Set<Long> longs = entry.getValue();
            XYChart.Series<Number, Number> dataSeries1 = new XYChart.Series<>();
            dataSeries1.setName(s);

            int i = 0;
            for (Long aLong : longs) {
                i++;
                dataSeries1.getData().add(new XYChart.Data<>(i, aLong));
            }

            scatterChart.getData().add(dataSeries1);
        }

        return scatterChart;
    }
}
