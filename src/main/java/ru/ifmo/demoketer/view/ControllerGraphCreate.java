package ru.ifmo.demoketer.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.stage.Stage;
import ru.ifmo.demoketer.Main;
import ru.ifmo.demoketer.model.MainGraph;
import ru.ifmo.demoketer.model.MainNode;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerGraphCreate {

    MainGraph mainGraph;
    List<MainNode> lisfOfNodes = new ArrayList<>();

    @FXML
    private TableView<MainNode> nodeTableGr;
    @FXML
    private TableColumn<MainNode, String> nameNodeGr;
    @FXML
    private TableColumn<MainNode, String> descriptionNodeGr;
    @FXML
    private TableColumn<MainNode, String> inputNodeGr;
    @FXML
    private TableColumn<MainNode, String> outputNodeGr;
    @FXML
    private Label selectInputParamNode;

    @FXML
    private TextField graphName;
    @FXML
    private TextField graphDescription;
    @FXML
    private TextField graphInputParam;
    @FXML
    private TextArea addedNodeList = new TextArea("");

    private ru.ifmo.demoketer.Main main;


    private Stage dialogStage;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
        nameNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeNameProperty());
        descriptionNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeInputProperty().asString());
        outputNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeOutputProperty());

        nameNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeNameProperty());
        descriptionNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeInputProperty().asString());
        outputNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeOutputProperty());


        nodeTableGr.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNodeDetails(newValue));

    }

    private void showNodeDetails(MainNode mainNode) {
        //ObservableList<TableColumn<MainNode, ?>> statusColumns = selectInputParamNode.getColumns();
        //selectInputParamNode.setText("");
        if (mainNode != null) {
            //ObservableList<String> data = FXCollections.observableArrayList(mainNode.getNodeInput());
            selectInputParamNode.setText(mainNode.getNodeInput().toString());
            //selectInputParamNode.setCellFactory(ChoiceBoxTableCell.forTableColumn(data));
        } else {
            //selectInputParamNode.setCellValueFactory(cellData -> cellData.getValue().);
        }
    }

    public void setMainApp(Main main) {
        this.main = main;
        nodeTableGr.setItems(main.getNodeData());
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleClose() {
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
    private void handleAddNode() {
        if(nodeTableGr.getSelectionModel().getSelectedItem()==null) {
            alertNotFNode();
            return;
        }
        MainNode mainNode = nodeTableGr.getSelectionModel().getSelectedItem();
        lisfOfNodes.add(mainNode);
        addedNodeList.setText(addedNodeList.getText() + " -> " + nodeTableGr.getSelectionModel().getSelectedItem().getNodeName());
    }

    @FXML
    private void handleSaveGraph() {
        if(lisfOfNodes.isEmpty()) {
            alertNotFNode();
            return;
        }

        if(graphName.getText().isEmpty() || graphInputParam.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Error");
            alert.setHeaderText("Mame or input param is empty");
            alert.showAndWait();
            return;
        }

        List<String> tempList = Arrays.asList(graphInputParam.getText().split("\\s*,\\s*"));
        MainNode newTempMainNode = lisfOfNodes.get(lisfOfNodes.size()-1);
        newTempMainNode.setNodeInput(tempList);
        lisfOfNodes.set(lisfOfNodes.size()-1, newTempMainNode);

        try {
        mainGraph = new MainGraph(graphName.getText(), graphDescription.getText(), lisfOfNodes);
            String fileName = graphName.getText() + ".graphketer";
            ObjectMapper mapper = new ObjectMapper();

            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(mapper.writeValueAsString(mainGraph));
            bw.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (IOException ioEx){
            System.out.println("WTF");
        }

        lisfOfNodes = null;
        addedNodeList.setText("");
        okClicked = true;
        dialogStage.close();
    }
}
