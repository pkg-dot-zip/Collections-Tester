package com.zimonishim.GUI;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import com.zimonishim.util.StatisticsHelper;
import javafx.scene.chart.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.util.Collection;

public class ChartsHelper {

    public static Pane getChartsPane(Collection<ResultEntry> results){
        TilePane tilePane = new TilePane();

        tilePane.getChildren().addAll(
                getChartScrollPane(getBarChart(results))
        );

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

    private static BarChart<String, Number> getBarChart(Collection<ResultEntry> results){

        //Setting the Axis.
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Collections");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Time in NS");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        //Here we set the Data.
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("See TODO in ChartsHelper"); //TODO: Make series for differentAmounts.

            //Retrieving data from StatisticsHelper.
        StatisticsHelper.getAverageOfAllCollections(results).forEach((s, aLong) -> {
            dataSeries1.getData().add(new XYChart.Data<>(s, aLong));
        });

        barChart.getData().add(dataSeries1);

        return barChart;
    }
}