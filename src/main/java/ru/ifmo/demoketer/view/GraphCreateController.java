package ru.ifmo.demoketer.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GraphCreateController {

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
    private List<Label> lableInputParam;
    @FXML
    private Label lableOutputParam;

    @FXML
    private List<TextField> graphInputParam;
    @FXML
    private TextField graphOutputParam;

    @FXML
    private Button buttonAddNode;

    @FXML
    private GridPane gridGraphRun;

    private static GridPane GridGraphRunDef;


    @FXML
    private TreeItem rootItem = new TreeItem("Структура графа");
    @FXML
    private TreeView treeOutStructure;
    @FXML
    private TextField graphName;
    @FXML
    private TextField graphDescription;

    @FXML
    private TextArea addedNodeList = new TextArea("");

    private ru.ifmo.demoketer.Main main;


    private Stage dialogStage;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
        GridGraphRunDef = gridGraphRun;

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
        buttonAddNode.setDisable(false);
        if(mainNode.getNodeName().equals("Узел алгоритма Шора")){
            buttonAddNode.setDisable(true);
            return;
        }
        if (mainNode != null) {
            //lableInputParam.setText(mainNode.getNodeInput().toString());
            lableInputParam = new ArrayList<>();
            graphInputParam = new ArrayList<>();
            GridGraphRunDef.getChildren().clear();
            GridGraphRunDef = gridGraphRun;


            int rowsIndex = 0;
            for (String input : mainNode.getNodeInput()) {
                GridGraphRunDef.addRow(rowsIndex);

                Label label = new Label("Введите входой параметр " + input);
                GridGraphRunDef.add(label, 0, rowsIndex);
                lableInputParam.add(label);

                TextField textField = new TextField();
                GridGraphRunDef.add(textField, 1, rowsIndex);
                graphInputParam.add(textField);

                rowsIndex+=1;
                GridGraphRunDef.addRow(rowsIndex);
                GridGraphRunDef.add(new Separator(), 0, rowsIndex);
                GridGraphRunDef.add(new Separator(), 1, rowsIndex);
                rowsIndex+=1;
            }

            GridGraphRunDef.addRow(rowsIndex);

            Label label = new Label("Введите выходной параметр " + mainNode.getNodeOutput());
            GridGraphRunDef.add(label, 0, rowsIndex);
            lableOutputParam = label;

            TextField textField = new TextField();
            GridGraphRunDef.add(textField, 1, rowsIndex);
            graphOutputParam = textField;

            GridGraphRunDef.addRow(rowsIndex);
            rowsIndex+=1;
            GridGraphRunDef.add(new Separator(), 0, rowsIndex);
            GridGraphRunDef.add(new Separator(), 1, rowsIndex);
            GridGraphRunDef.addRow(rowsIndex + 1);
            GridGraphRunDef.getRowConstraints().setAll(new RowConstraints(25, 30, 40));
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

    private boolean inputIsEmpty(){
        for (TextField textField : graphInputParam) {
            if (textField.getText().isEmpty())
                return true;
        }
        return false;
    }

    @FXML
    private void handleAddNode() {
        if(nodeTableGr.getSelectionModel().getSelectedItem()==null) {
            main.alertNotFNode("Ошибка добавления узла", "Не выбран узел \nПожалуйста, выберите один из узлов из списка");
            return;
        }

        if(graphOutputParam.getText().isEmpty() || inputIsEmpty()){
            main.alertNotFNode("Ошибка добавления узла","Входной или выходной параметры пусты");
            return;
        }

        List<String> tempList = new ArrayList<>();
        for (TextField input : graphInputParam) {
            tempList.add(input.getText());
        }
        MainNode mainNode = new MainNode(nodeTableGr.getSelectionModel().getSelectedItem());
        mainNode.setNodeInput(tempList);
        mainNode.setNodeOutput(graphOutputParam.getText());
        if(lisfOfNodes.isEmpty()){
            lisfOfNodes.add(mainNode);
            rootItem.getChildren().add(new TreeItem(mainNode.getNodeName() + " [" + mainNode.getNodeInput() +  ", " + mainNode.getNodeOutput() + "]"));
            treeOutStructure.setRoot(rootItem);
        }
        else{
            sortedNodes(mainNode); //входящий файл всегда первый. иначе жопа
        }

        //addedNodeList.setText(addedNodeList.getText() + " -> " + nodeTableGr.getSelectionModel().getSelectedItem().getNodeName());
    }

    private boolean sortedNodes(MainNode newMainNode){
        boolean flag = false;
        //String inputOne = newMainNode.getNodeInput().get(0);
        for (MainNode mainNode : lisfOfNodes) {
            if(mainNode.getNodeOutput().equals(newMainNode.getNodeInput().get(0))){

                boolean flagItem = recAddItem(newMainNode, rootItem);
                if(!flagItem){
                    main.alertNotFNode("Ошибка добавления узла","Ошибка создания структуры");
                    return false;
                }
                lisfOfNodes.add(lisfOfNodes.indexOf(mainNode)+1, newMainNode);
                return true;
            }
        }

        if(!flag)
            main.alertNotFNode("Ошибка добавления узла","Входный параметры узла не найден в выходных параметрах ранних узлов");
        return flag;
    }

    private boolean recAddItem(MainNode newMainNode, TreeItem treeItemInput){
        for (int i = 0; i < treeItemInput.getChildren().size(); i++) {
            TreeItem treeItem = (TreeItem) treeItemInput.getChildren().get(i);
            Pattern p = Pattern.compile(".*" + newMainNode.getNodeInput().get(0) + "]$");
            Matcher m = p.matcher(treeItem.getValue().toString());
            if(m.matches()){
                ((TreeItem) treeItemInput.getChildren().get(i)).getChildren().add(new TreeItem(newMainNode.getNodeName() + " [" + newMainNode.getNodeInput() +  ", " + newMainNode.getNodeOutput() + "]"));
                return true;
            }
        }

        for (int i = 0; i < treeItemInput.getChildren().size(); i++) {
            if (recAddItem(newMainNode, (TreeItem) treeItemInput.getChildren().get(i))){
                return true;
            }
        }

        return false;
    }

    @FXML
    private void handleSaveGraph() {
        if(lisfOfNodes.isEmpty()) {
            main.alertNotFNode("Ошибка создания графа","Не найдены узлы \nПожалуйста, добавьте один из узлов из списка");
            return;
        }

        if(graphName.getText().isEmpty()){
            main.alertNotFNode("Ошибка создания графа","Имя графа не задано");
            return;
        }

        try {
        mainGraph = new MainGraph(graphName.getText(), graphDescription.getText(), "graph", lisfOfNodes);
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
