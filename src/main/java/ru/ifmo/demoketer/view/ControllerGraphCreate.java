package ru.ifmo.demoketer.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.ifmo.demoketer.Main;
import ru.ifmo.demoketer.model.MainNode;

public class ControllerGraphCreate {


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

    private ru.ifmo.demoketer.Main main;


    private Stage dialogStage;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
        nameNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeNameProperty());
        descriptionNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeInputProperty());
        outputNodeGr.setCellValueFactory(cellData -> cellData.getValue().nodeOutputProperty());

        nameNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeNameProperty());
        descriptionNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeInputProperty());
        outputNodeGr.setCellValueFactory(
                cellData -> cellData.getValue().nodeOutputProperty());

        //nodeTableGr.getSelectionModel().selectedItemProperty().addListener(
              //  (observable, oldValue, newValue) -> showNodeDetails(newValue));
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

}
