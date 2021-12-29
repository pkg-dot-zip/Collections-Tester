package com.zimonishim.GUI.resultTables;

import com.zimonishim.GUI.ResultGUI;
import com.zimonishim.GUI.resultTables.resultTypes.ResultEntry;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Arrays;

public class ResultsTableViewHelper {

    public static TableView<ResultEntry> getResultsTableView() {
        TableView<ResultEntry> tableView = new TableView<>();

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableView.setRowFactory(tableView1 -> {

            //Init.
            final TableRow<ResultEntry> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem viewResultInfo = new MenuItem("Result Information");
            final MenuItem removeMenuItem = new MenuItem("Remove");

            //SetOnAction for menuItems.
            viewResultInfo.setOnAction(e -> new ResultGUI(tableView1.getSelectionModel().getSelectedItems()));

            removeMenuItem.setOnAction(e -> tableView1.getSelectionModel().getSelectedItems().forEach(resultEntry -> tableView1.getItems().remove(resultEntry)));

            contextMenu.getItems().addAll(viewResultInfo, removeMenuItem);

            //Set context menu on row, but use a binding to make it only show for non-empty rows.
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });

        TableColumn<ResultEntry, String> typeColumn = new TableColumn<>("Collection Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("collectionClassName"));
        typeColumn.setCellFactory((tableColumn) -> new TableCell<ResultEntry, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(!empty ? item : null); //We set the cell's text to null if the cell is empty.
            }
        });

        TableColumn<ResultEntry, String> actionNameColumn = new TableColumn<>("Action Name");
        actionNameColumn.setCellValueFactory(new PropertyValueFactory<>("actionName"));

        TableColumn<ResultEntry, int[]> beforeArrayColumn = new TableColumn<>("Before");
        beforeArrayColumn.setCellValueFactory(new PropertyValueFactory<>("beforeActionArray"));
        beforeArrayColumn.setCellFactory((tableColumn) -> arrayCell());

        TableColumn<ResultEntry, Long> totalTimeColumn = new TableColumn<>("Time");
        totalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalTime"));

        TableColumn<ResultEntry, int[]> afterArrayColumn = new TableColumn<>("After");
        afterArrayColumn.setCellValueFactory(new PropertyValueFactory<>("afterActionArray"));
        afterArrayColumn.setCellFactory((tableColumn) -> arrayCell(((tableCell, item, empty, resultEntry) -> {
            if (!Arrays.equals(item, resultEntry.getBeforeActionArray())) {
                tableCell.setStyle("-fx-text-fill: red;");
            }
        })));

        tableView.getColumns().addAll(
                typeColumn, actionNameColumn, beforeArrayColumn,
                totalTimeColumn, afterArrayColumn
        );

        return tableView;
    }

    private static TableCell<ResultEntry, int[]> arrayCell(IUpdateItem updateItem) {
        return new TableCell<ResultEntry, int[]>() {
            @Override
            protected void updateItem(int[] item, boolean empty) {
                super.updateItem(item, empty);

                this.setText(empty ? null : "" + item.length); //We set the cell's text to null if the cell is empty.

                //We check if it can run the updateItem code. For this we need to have a valid item.
                if (getTableRow() == null) return;

                if (getTableRow().getItem() == null) return;

                //We can now the specified code passed as the parameter.
                updateItem.onUpdate(this, item, empty, (ResultEntry) getTableRow().getItem());
            }
        };
    }

    private static TableCell<ResultEntry, int[]> arrayCell() {
        return arrayCell((tableCell, item, empty, resultEntry) -> {
        });
    }

    private interface IUpdateItem {
        void onUpdate(TableCell<ResultEntry, int[]> tableCell, int[] item, boolean empty, ResultEntry resultEntry);
    }
}
