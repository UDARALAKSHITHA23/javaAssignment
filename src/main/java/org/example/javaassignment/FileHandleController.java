package org.example.javaassignment;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileHandleController {

    ArrayList<String[]> mainDataSet = new ArrayList<String[]>();

    @FXML
    private TableView mainTable;

    @FXML
    private Label welcomeText;

    @FXML
    private TextArea textArea;

    @FXML
    private Label fileName;

    @FXML
    private ChoiceBox<Map.Entry<Integer, String>> choiceBox;


    @FXML
    protected void initialize() {
        choiceBox.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Map.Entry<Integer, String> entry) {
                return entry == null ? "" : entry.getValue();
            }

            @Override
            public Map.Entry<Integer, String> fromString(String string) {
                return null; // Not used
            }
        });


    }

    @FXML
    protected void sortButtonClick() {

        Map<Integer, Double> data = getData(choiceBox.getValue().getKey());

        long startTime = System.nanoTime();
        ArrayList<Integer> insertionSortResult = SortingAlgorithm.insertionSort(data);
        long insertionSortTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        ArrayList<Integer> shellSortResult = SortingAlgorithm.shellSort(data);
        long shellSortTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        ArrayList<Integer> quickSortResult = SortingAlgorithm.quickSort(data);
        long quickSortTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        ArrayList<Integer> mergeSortResult = SortingAlgorithm.mergeSort(data);
        long mergeSortTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        ArrayList<Integer> heapSortResult = SortingAlgorithm.heapSort(data);
        long heapSortTime = System.nanoTime() - startTime;





        StringBuilder sortTimes = new StringBuilder();
        sortTimes.append("Insertion sort time: ").append(insertionSortTime).append(" ns\n");
        sortTimes.append("Shell sort time: ").append(shellSortTime).append(" ns\n");
        sortTimes.append("Quick sort time: ").append(quickSortTime).append(" ns\n");
        sortTimes.append("Merge sort time: ").append(mergeSortTime).append(" ns\n");
        sortTimes.append("Heap sort time: ").append(heapSortTime).append(" ns\n");
        System.out.println(sortTimes.toString());
        textArea.setText(sortTimes.toString());
        System.out.print(choiceBox.getValue().getKey());

        mainTable.getItems().clear();
        for (Integer i : shellSortResult) {
            mainTable.getItems().add(mainDataSet.get(i));
        }


    }

    private Map<Integer, Double> getData(int col){
        Map<Integer, Double> data = new HashMap<>();

        for (int i=0; i<mainDataSet.size(); i++) {
            Double value = 0.0;
            try{
                value=Double.parseDouble(mainDataSet.get(i)[col]);
            }catch (Exception e){
                value=0.0;
            }
            data.put(i, value);
        }
        return data;
    }

    @FXML
    protected void onFileSelectButtonClick(){
        choiceBox.getItems().clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            fileName.setText(file.getName());
            ObservableList<String[]> data = FXCollections.observableArrayList();

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isFirstLine = true;
                boolean isSecondLine = true;
                Map<Integer, String> items=new HashMap<>();
                ArrayList<String> colNames=new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    String[] row = line.split(",");

                    if (isFirstLine) {
                        for (int i = 0; i < row.length; i++) {
                            TableColumn<String[], String> column = new TableColumn<>(row[i]);
                            column.setSortable(false);
                            final int colIndex = i;
                            column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[colIndex]));
                            mainTable.getColumns().add(column);
                            colNames.add(row[i]);
                        }
                        isFirstLine = false;
                    } else if (isSecondLine) {
                        data.add(row);
                        mainDataSet.add(row);
                        for (int x=0; x<row.length; x++) {
                            try {
                                double doubleValue = Double.parseDouble(row[x]);
                            } catch (NumberFormatException e) {
                                continue;
                            }
                            items.put(x, colNames.get(x));
                        }
                        isSecondLine = false;
                    } else {
                        data.add(row);
                        mainDataSet.add(row);
                    }
                }

                choiceBox.setItems(FXCollections.observableArrayList(items.entrySet()));
                mainTable.setItems(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}