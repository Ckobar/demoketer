package ru.ifmo.demoketer.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.ifmo.demoketer.Main;
import ru.ifmo.demoketer.model.MainNode;


public class NodeListController {
    @FXML
    private Label nodeNameList;
    @FXML
    private TextArea descriptionNodeList;
    @FXML
    private TextArea exampleNodeList;

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

    @FXML
    private void initialize() {
        nameNode.setCellValueFactory(cellData -> cellData.getValue().nodeNameProperty());
        descriptionNode.setCellValueFactory(cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNode.setCellValueFactory(cellData -> cellData.getValue().nodeInputProperty().asString());
        outputNode.setCellValueFactory(cellData -> cellData.getValue().nodeOutputProperty());

        nameNode.setCellValueFactory(
                cellData -> cellData.getValue().nodeNameProperty());
        descriptionNode.setCellValueFactory(
                cellData -> cellData.getValue().nodeDescriptionProperty());
        inputNode.setCellValueFactory(
                cellData -> cellData.getValue().nodeInputProperty().asString());
        outputNode.setCellValueFactory(
                cellData -> cellData.getValue().nodeOutputProperty());

        showNodeDetails(null);

        nodeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNodeDetails(newValue));
    }

    private void showNodeDetails(MainNode mainNode) {
        if (mainNode != null) {
            nodeNameList.setText(mainNode.getNodeName());
            descriptionNodeList.setText(mainNode.getNodeFullDescription());
            exampleNodeList.setText(mainNode.getNodeExample());
        } else {
            nodeNameList.setText("");
            descriptionNodeList.setText("");
            exampleNodeList.setText("");
        }
    }

    @FXML
    private void handleCreateGraph() {
        main.createGraph();
    }

    public void setMainApp(Main main) {
        this.main = main;
        nodeTable.setItems(main.getNodeData());
    }

    @FXML void runNode(){
        main.runNode();
    }

    @FXML void runGraph(){
        main.runGraph();
    }
}
