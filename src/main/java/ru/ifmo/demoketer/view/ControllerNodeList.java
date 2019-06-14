package ru.ifmo.demoketer.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.MenuItem;
import ru.ifmo.demoketer.model.MainNode;


public class ControllerNodeList {
    @FXML
    private MenuItem nodeList;
    @FXML
    private TableView<MainNode> nodeTable;
    @FXML
    private TableColumn<MainNode, String> nameNode;
    @FXML
    private TableColumn<MainNode, String> descriptionNode;
    @FXML
    private TableColumn<MainNode, String> inputNode;
    @FXML
    private TableColumn<MainNode, String> outputNode;

    private ru.ifmo.demoketer.Main main;

    public ControllerNodeList() {
    }

    @FXML
    private void initialize() {
        nameNode.setCellValueFactory(cellData -> cellData.getValue().nodeNameProperty());
        descriptionNode.setCellValueFactory(cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNode.setCellValueFactory(cellData -> cellData.getValue().nodeInputProperty());
        outputNode.setCellValueFactory(cellData -> cellData.getValue().nodeOutputProperty());
    }

    public void setMainApp(ru.ifmo.demoketer.Main main) {
        this.main = main;

        nodeTable.setItems(main.getNodeData());
    }
}
