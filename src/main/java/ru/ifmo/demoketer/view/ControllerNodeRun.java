package ru.ifmo.demoketer.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

public class ControllerNodeRun {

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
    private Label selectInputParamNodeR;

    @FXML
    private TextField graphInputParamR;
    @FXML
    private TextField graphOutputParamR;

    @FXML
    private TextArea resultRunNode = new TextArea("");

    private Main main;


    private Stage dialogStage;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
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
            selectInputParamNodeR.setText(mainNode.getNodeInput().toString());
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

    private void alertNotFNode(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Error");
        alert.setHeaderText("No node selected");
        alert.setHeaderText("Need to add a node");
        alert.showAndWait();
    }

    @FXML
    private void handleRunNode() {
        resultRunNode.setText("");

        if(nodeTableR.getSelectionModel().getSelectedItem()==null) {
            alertNotFNode();
            return;
        }

        if(graphOutputParamR.getText().isEmpty() || graphInputParamR.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Output or input param is empty");
            alert.showAndWait();
            return;
        }

        List<String> tempList = Arrays.asList(graphInputParamR.getText().split("\\s*,\\s*"));

        MainNode newTempMainNode = nodeTableR.getSelectionModel().getSelectedItem();
        if(newTempMainNode.getNodeName().equals("sortNode")){
            resultRunNode.setText("Run sortNode. Input param: " + tempList.get(0) +
                    "\nResult: \n" + newTempMainNode.sortNode(tempList.get(0), graphOutputParamR.getText()));
        }
        else if(newTempMainNode.getNodeName().equals("countRowsNode")){
            resultRunNode.setText("Run countRowsNode. Input param: " + tempList.get(0) +
                    "\nResult: \n" + newTempMainNode.countRowsNode(tempList.get(0), graphOutputParamR.getText()));
        }
        else if(newTempMainNode.getNodeName().equals("uniqueWordNode")){
            resultRunNode.setText("Run uniqueWordNode. Input param: " + tempList.get(0) +
                    "\nResult: \n" + newTempMainNode.uniqueWordNode(tempList.get(0), graphOutputParamR.getText()));
        }
        else if(newTempMainNode.getNodeName().equals("filterNode")){
            resultRunNode.setText( "Run filterNode. Input param: " + tempList.get(0) +
                    "\nResult: \n" + newTempMainNode.filterNode(tempList.get(0), tempList.get(1), graphOutputParamR.getText()));
        }



        //okClicked = true;
        //dialogStage.close();
    }
}
