package ru.ifmo.demoketer.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import ru.ifmo.demoketer.Main;
import ru.ifmo.demoketer.model.MainGraph;
import ru.ifmo.demoketer.model.MainNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeRunController {

    @FXML
    private TableView<MainNode> nodeTableR;
    @FXML
    private TableColumn<MainNode, String> nameNodeR;
    @FXML
    private TableColumn<MainNode, String> descriptionNodeR;
    @FXML
    private TableColumn<MainNode, String> inputNodeR;
    @FXML
    private TableColumn<MainNode, String> outputNodeR;
    @FXML
    private List<Label> selectInputParamNodeR;
    @FXML
    private Label selectOutputParamNodeR;

    @FXML
    private List<TextField> nodeInputParamR;
    @FXML
    private TextField nodeOutputParamR;

    @FXML
    private TextArea resultRunNode = new TextArea("");
    @FXML
    private GridPane gridNodeRun;

    @FXML
    private Button buttonRunNode;

    private static GridPane GridNodeRunDef;

    private Main main;


    private Stage dialogStage;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
        GridNodeRunDef = gridNodeRun;

        nameNodeR.setCellValueFactory(cellData -> cellData.getValue().nodeNameProperty());
        descriptionNodeR.setCellValueFactory(cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNodeR.setCellValueFactory(cellData -> cellData.getValue().nodeInputProperty().asString());
        outputNodeR.setCellValueFactory(cellData -> cellData.getValue().nodeOutputProperty());

        nameNodeR.setCellValueFactory(
                cellData -> cellData.getValue().nodeNameProperty());
        descriptionNodeR.setCellValueFactory(
                cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNodeR.setCellValueFactory(
                cellData -> cellData.getValue().nodeInputProperty().asString());
        outputNodeR.setCellValueFactory(
                cellData -> cellData.getValue().nodeOutputProperty());


        nodeTableR.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNodeDetails(newValue));

    }

    private void showNodeDetails(MainNode mainNode) {

        if (mainNode != null) {
            //selectInputParamNodeR.setText(mainNode.getNodeInput().toString());
            selectInputParamNodeR = new ArrayList<>();
            nodeInputParamR = new ArrayList<>();
            gridNodeRun.getChildren().clear();
            gridNodeRun = GridNodeRunDef;

            if(mainNode.getNodeName().equals("Узел алгоритма Шора")){
                //buttonRunNode.setDisable(true);
                return;
            }

            int rowsIndex = 0;
            for (String input : mainNode.getNodeInput()) {
                gridNodeRun.addRow(rowsIndex);

                Label label = new Label("Введите входой параметр " + input);
                gridNodeRun.add(label, 0, rowsIndex);
                selectInputParamNodeR.add(label);

                TextField textField = new TextField();
                gridNodeRun.add(textField, 1, rowsIndex);
                nodeInputParamR.add(textField);

                rowsIndex+=1;
                gridNodeRun.addRow(rowsIndex);
                gridNodeRun.add(new Separator(), 0, rowsIndex);
                gridNodeRun.add(new Separator(), 1, rowsIndex);
                rowsIndex+=1;
            }

            gridNodeRun.addRow(rowsIndex);

            Label label = new Label("Введите выходной параметр " + mainNode.getNodeOutput());
            gridNodeRun.add(label, 0, rowsIndex);
            selectOutputParamNodeR = label;

            TextField textField = new TextField();
            gridNodeRun.add(textField, 1, rowsIndex);
            nodeOutputParamR = textField;

            gridNodeRun.addRow(rowsIndex);
            rowsIndex+=1;
            gridNodeRun.add(new Separator(), 0, rowsIndex);
            gridNodeRun.add(new Separator(), 1, rowsIndex);
            gridNodeRun.addRow(rowsIndex + 1);
            gridNodeRun.getRowConstraints().setAll(new RowConstraints(25, 30, 40));
        }
    }

    public void setMainApp(Main main) {
        this.main = main;
        nodeTableR.setItems(main.getNodeData());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleCloseR() {
        okClicked = true;
        dialogStage.close();
    }

    private boolean inputIsEmpty(){
        for (TextField textField : nodeInputParamR) {
            if (textField.getText().isEmpty())
                return true;
        }
        return false;
    }

    @FXML
    private void handleRunNode() {
        resultRunNode.setText("");

        /**
         * SHOR!
         */
        if(nodeTableR.getSelectionModel().getSelectedItem().getNodeName().equals("Узел алгоритма Шора")){
            resultRunNode.setText("Запускается Узел алгоритма Шора. Входные параметры: \n" + main.inputShor +
                    "\nResult: \n" + "Запускается Узел алгоритма Шора...\n" + "Данные файла: \n" + "null\n" +
                    "Выходные данные: \n" + main.outputShor);
            return;
        }


        if(nodeTableR.getSelectionModel().getSelectedItem()==null) {
            main.alertNotFNode("Ошибка запуска узла", "Узел не выбран \nНеобходимо выбрать узел");
            return;
        }

        if(nodeOutputParamR.getText().isEmpty() || inputIsEmpty()){
            main.alertNotFNode("Ошибка запуска узла", "Входной или выходной параметры пусты");
            return;
        }

        //List<String> tempList = Arrays.asList(graphInputParamR.getText().split("\\s*,\\s*"));

        try {
            MainNode newTempMainNode = nodeTableR.getSelectionModel().getSelectedItem();
            if (newTempMainNode.getNodeName().equals("Узел сортировки")) {
                resultRunNode.setText("Запускается Узел сортировки. Входные параметры: " + nodeInputParamR.get(0).getText() +
                        "\nResult: \n" + newTempMainNode.sortNode(nodeInputParamR.get(0).getText(), nodeOutputParamR.getText()));
            } else if (newTempMainNode.getNodeName().equals("Узел подсчёта строк")) {
                resultRunNode.setText("Запускается Узел подсчёта строк. Входные параметры: " + nodeInputParamR.get(0).getText() +
                        "\nResult: \n" + newTempMainNode.countRowsNode(nodeInputParamR.get(0).getText(), nodeOutputParamR.getText()));
            } else if (newTempMainNode.getNodeName().equals("Узел выборки уникальных элементов")) {
                resultRunNode.setText("Запускается Узел выборки уникальных элементов. Входные параметры: " + nodeInputParamR.get(0).getText() +
                        "\nResult: \n" + newTempMainNode.uniqueWordNode(nodeInputParamR.get(0).getText(), nodeOutputParamR.getText()));
            } else if (newTempMainNode.getNodeName().equals("Узел фильтрации")) {
                resultRunNode.setText("Запускается Узел фильтрации. Входные параметры:  " + nodeInputParamR.get(0).getText() + ", " + nodeInputParamR.get(1).getText() +
                        "\nResult: \n" + newTempMainNode.filterNode(nodeInputParamR.get(0).getText(), nodeInputParamR.get(1).getText(), nodeOutputParamR.getText()));
            }
        }catch (Exception e){
            main.alertNotFNode("Ошибка запуска узла","Ошибка в заданных параметрах");
        }


        //okClicked = true;
        //dialogStage.close();
    }
}
