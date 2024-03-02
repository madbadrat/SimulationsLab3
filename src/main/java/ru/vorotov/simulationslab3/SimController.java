package ru.vorotov.simulationslab3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SimController implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private TextField ruleField;

    private final int GRID_SIZE = 120;
    private final int CELL_SIZE = 5;
    private final int[][] grid = new int[GRID_SIZE][GRID_SIZE];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawGrid();
    }

    private void drawGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setStroke(Color.BLACK);

                if (grid[i][j] == 1) cell.setFill(Color.BLUE);
                else cell.setFill(Color.WHITE);


                int x = i;
                int y = j;

                cell.setOnMouseClicked(event -> {
                    if (x == 0 && event.getButton() == MouseButton.PRIMARY) {
                        grid[x][y] = 1;
                        cell.setFill(Color.BLUE);
                    } else if (x == 0 && event.getButton() == MouseButton.SECONDARY) {
                        grid[x][y] = 0;
                        cell.setFill(Color.WHITE);
                    }
                });

                gridPane.add(cell, j, i);
            }
        }
    }

    public void onStartButtonClick(ActionEvent actionEvent) {
        // переводим значение rule в двоичное число
        String binaryString = String.format("%" + 8 + "s", Integer.toBinaryString(Integer.parseInt(ruleField.getText()))).replace(' ', '0');
        // переворачиваем
        binaryString = new StringBuilder(binaryString).reverse().toString();

        for (int i = 1; i < GRID_SIZE; i++) { // по строкам
            for (int j = 0; j < GRID_SIZE; j++) { // по столбикам
                String tempCode;
                if (j == 0) {
                    tempCode = String.valueOf(grid[i - 1][GRID_SIZE - 1]) + String.valueOf(grid[i - 1][j]) + String.valueOf(grid[i - 1][j + 1]); // двоичный код соседних ячеек
                } else if (j == GRID_SIZE - 1) {
                    tempCode = String.valueOf(grid[i - 1][j - 1]) + String.valueOf(grid[i - 1][j]) + String.valueOf(grid[i - 1][0]); // двоичный код соседних ячеек
                } else {
                    tempCode = String.valueOf(grid[i - 1][j - 1]) + String.valueOf(grid[i - 1][j]) + String.valueOf(grid[i - 1][j + 1]); // двоичный код соседних ячеек
                }

                grid[i][j] = Character.getNumericValue(binaryString.charAt(Integer.parseInt(tempCode, 2)));
            }
        }

        drawGrid();
    }

    public void onClearButtonClick(ActionEvent actionEvent) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = 0;
            }
        }
        drawGrid();
    }
}