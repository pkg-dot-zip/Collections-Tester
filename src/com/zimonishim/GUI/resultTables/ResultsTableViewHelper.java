package com.zimonishim.GUI.resultTables;

import com.zimonishim.GUI.ResultGUI;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.Arrays;

public class ResultsTableViewHelper {

    public static TableView<ResultEntry> getResultsTableView(){
        TableView<ResultEntry> tableView = new TableView<>();

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.setRowFactory(new Callback<TableView<ResultEntry>, TableRow<ResultEntry>>() {
            @Override
            public TableRow<ResultEntry> call(TableView<ResultEntry> tableView) {

                //Init.
                final TableRow<ResultEntry> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem viewResultInfo = new MenuItem("Result Information");
                final MenuItem removeMenuItem = new MenuItem("Remove");

                //SetOnAction for menuItems.
                viewResultInfo.setOnAction(e -> {
                    new ResultGUI(tableView.getSelectionModel().getSelectedItems());
                });

                removeMenuItem.setOnAction(e -> {
                    tableView.getSelectionModel().getSelectedItems().forEach(resultEntry -> tableView.getItems().remove(resultEntry));
                });

                contextMenu.getItems().addAll(viewResultInfo, removeMenuItem);

                //Set context menu on row, but use a binding to make it only show for non-empty rows.
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );

                return row;
            }
        });

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

                //TODO: Show first couple of entries instead of "Test".
                this.setText(!empty ? "Test" : null); //We set the cell's text to null if the cell is empty.

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