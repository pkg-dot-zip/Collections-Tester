package com.zimonishim.GUI.resultTables;

import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Arrays;

//TODO: Don't put the entire test data in the cells. This is probably the cause of the lag.

public class ResultsTableViewHelper {

    public static TableView<ResultEntry> getResultsTableView(){
        TableView<ResultEntry> tableView = new TableView<>();

        TableColumn<ResultEntry, String> typeColumn = new TableColumn<>("Collection Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("collectionClassName"));
        typeColumn.setCellFactory((tableColumn) -> new TableCell<ResultEntry, String>(){
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(!empty ? item : null); //We set the cell's text to null if the cell is empty.

                //TODO: Support icons.
                //If no name is specified we do not set a value here.
//                if (item == null){
//                    return;
//                }

                //Here we apply the graphic with the type.
//                System.out.println("Setting graphic for " + item + ".");
//                this.setGraphic(IconHelper.getCollectionIcon(item));
            }
        });

        TableColumn<ResultEntry, String> actionNameColumn = new TableColumn<>("Action Name");
        actionNameColumn.setCellValueFactory(new PropertyValueFactory<>("actionName"));

        TableColumn<ResultEntry, Number[]> beforeArrayColumn = new TableColumn<>("Before");
        beforeArrayColumn.setCellValueFactory(new PropertyValueFactory<>("beforeActionArray"));
        beforeArrayColumn.setCellFactory((tableColumn) -> arrayCell());

        TableColumn<ResultEntry, Long> totalTimeColumn = new TableColumn<>("Time");
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));

        TableColumn<ResultEntry, Number[]> afterArrayColumn = new TableColumn<>("After");
        afterArrayColumn.setCellValueFactory(new PropertyValueFactory<>("afterActionArray"));
        afterArrayColumn.setCellFactory((tableColumn) -> arrayCell(((tableCell, item, empty, resultEntry) -> {
            if (!Arrays.equals(item, resultEntry.getBeforeActionArray())){
                tableCell.setStyle("-fx-text-fill: red;");
            }
        })));

        tableView.getColumns().addAll(
                typeColumn, actionNameColumn, beforeArrayColumn,
                totalTimeColumn, afterArrayColumn
        );

        return tableView;
    }

    private static TableCell<ResultEntry, Number[]> arrayCell(IUpdateItem updateItem){
        return new TableCell<ResultEntry, Number[]>() {
            @Override
            protected void updateItem(Number[] item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(!empty ? Arrays.toString(item) : null); //We set the cell's text to null if the cell is empty.

                //We check if can run the updateItem code. For this we need to have a valid item.
                if (getTableRow() == null){
                    return;
                }

                if (getTableRow().getItem() == null){
                    return;
                }

                //We can now the specified code passed as the parameter.
                updateItem.onUpdate(this, item, empty, (ResultEntry)getTableRow().getItem());
            }
        };
    }

    private static TableCell<ResultEntry, Number[]> arrayCell(){
        return arrayCell((tableCell, item, empty, resultEntry) -> {});
    }

    private interface IUpdateItem {
        void onUpdate(TableCell<ResultEntry, Number[]> tableCell, Number[] item, boolean empty, ResultEntry resultEntry);
    }
}