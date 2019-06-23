package ru.ifmo.demoketer.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ifmo.demoketer.Main;
import ru.ifmo.demoketer.model.MainGraph;
import ru.ifmo.demoketer.model.MainNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GraphRunController {

    private ObservableList<MainGraph> listOfGraph = FXCollections.observableArrayList();

    @FXML
    private TableView<MainGraph> nodeTableRG;
    @FXML
    private TableColumn<MainGraph, String> nameNodeGR;
    @FXML
    private TableColumn<MainGraph, String> descriptionNodeGR;

    @FXML
    private TextArea selectGraph = new TextArea("");

    @FXML
    private TextArea resultRunGraph = new TextArea("");

    private Main main;


    private Stage dialogStage;
    private boolean okClicked = false;


    @FXML
    private void initialize() {
        searchAllGraph();

        nameNodeGR.setCellValueFactory(cellData -> cellData.getValue().graphNameProperty());
        descriptionNodeGR.setCellValueFactory(cellData -> cellData.getValue().graphDescriptionProperty());

        nameNodeGR.setCellValueFactory(
                cellData -> cellData.getValue().graphNameProperty());
        descriptionNodeGR.setCellValueFactory(
                cellData -> cellData.getValue().graphDescriptionProperty());

        nodeTableRG.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showNodeDetails());

    }

    private void searchAllGraph(){
        File f = new File(".");
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("graphketer");
            }
        });
        File[] rezMatchingFiles = new File[matchingFiles.length];
        for (int i = 0; i < matchingFiles.length; i++) {
            File file = new File(matchingFiles[i].toString().replace(".\\", ""));
            rezMatchingFiles[i] = file;
        }
        initAllGraph(rezMatchingFiles);
    }

    private void initAllGraph(File[] matchingFiles){
        try {
            for (File file : matchingFiles) {
                FileInputStream inStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
                String strLineT = "";
                String strLine = "";
                ObjectMapper objectMapper = new ObjectMapper();

                while ((strLineT = bufferedReader.readLine()) != null) {
                    strLine+=strLineT;
                }

                //MainGraph mainGraphN = objectMapper.readValue(strLine, MainGraph.class);
                //listOfGraph.add(mainGraphN);
                bufferedReader.close();
                parsStrGraph(strLine);
            }
        }catch(IOException ioEx){
            System.out.println("WTF");
        }
        /*catch(Exception ioEx){
            System.out.println("...");
        }*/
    }

    private void parsStrGraph(String strLine){

        /*this.graphName = new SimpleStringProperty(graphName);
        this.graphDescription = new SimpleStringProperty(graphDescription);
        this.typeKeter = new SimpleStringProperty(typeKeter);
        this.listOfNodes = new SimpleObjectProperty<>(listOfNodes);*/

        try {
            JSONObject obj = new JSONObject(strLine);

            String graphName = obj.getString("graphName");
            String typeKeter = obj.getString("typeKeter");
            String graphDescription = obj.getString("graphDescription");
            JSONArray listOfNodes = obj.getJSONArray("listOfNodes");

            List<MainNode> listMainNodeFroGraph = new ArrayList<>();
            for (int i = 0; i < listOfNodes.length(); i++) {
                String nodeName = listOfNodes.getJSONObject(i).getString("nodeName");
                String nodeDescription = listOfNodes.getJSONObject(i).getString("nodeDescription");
                String nodeOutput = listOfNodes.getJSONObject(i).getString("nodeOutput");
                String nodeFullDescription = listOfNodes.getJSONObject(i).getString("nodeFullDescription");
                String nodeExample = listOfNodes.getJSONObject(i).getString("nodeExample");
                String typeKeterN = listOfNodes.getJSONObject(i).getString("typeKeter");
                String uuidNode = listOfNodes.getJSONObject(i).getString("uuidNode");
                JSONArray nodeInput = listOfNodes.getJSONObject(i).getJSONArray("nodeInput");
                List<String> nodeInpArr = new ArrayList<>();
                for (int j = 0; j < nodeInput.length(); j++) {
                    String inp = nodeInput.getString(j);
                    nodeInpArr.add(inp);
                }
                MainNode mainNode = new MainNode(nodeName, nodeDescription, nodeInpArr,
                        nodeOutput, nodeFullDescription, nodeExample, typeKeterN);
                listMainNodeFroGraph.add(mainNode);
            }
            MainGraph mainGraphLp = new MainGraph(graphName,graphDescription, typeKeter, listMainNodeFroGraph);
            listOfGraph.add(mainGraphLp);
        }catch (JSONException exception){

        }
    }


    private void showNodeDetails() {
        if (listOfGraph != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                selectGraph.setText(mapper.writeValueAsString(nodeTableRG.getSelectionModel().getSelectedItem()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
    }

    public void setMainApp(Main main) {
        this.main = main;
        nodeTableRG.setItems(listOfGraph);
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleCloseGR() {
        okClicked = true;
        dialogStage.close();
    }


    @FXML
    private void handleRunGraph() {
        resultRunGraph.setText("");

        if(nodeTableRG.getSelectionModel().getSelectedItem()==null) {
            main.alertNotFNode("Ошибка запуска графа","Граф не выбран \nПожалуйста, выберите граф");
            return;
        }

        try {
        for (MainNode mainNode : nodeTableRG.getSelectionModel().getSelectedItem().getListOfNodes()) {
            if(mainNode.getNodeName().equals("Узел сортировки")){
                resultRunGraph.setText(resultRunGraph.getText() + "Запускается Узел сортировки. Входные параметры: " + mainNode.getNodeInput().get(0) +
                        "\nВыходные параметры: " + mainNode.getNodeOutput() +
                        "\nResult: \n" + mainNode.sortNode(mainNode.getNodeInput().get(0), mainNode.getNodeOutput()) +
                        "\n==============================\n\n");
            }
            else if(mainNode.getNodeName().equals("Узел подсчёта строк")){
                resultRunGraph.setText(resultRunGraph.getText() + "Запускается Узел подсчёта строк. Входные параметры: " + mainNode.getNodeInput().get(0) +
                        "\nВыходные параметры: " + mainNode.getNodeOutput() +
                        "\nResult: \n" + mainNode.countRowsNode(mainNode.getNodeInput().get(0), mainNode.getNodeOutput()) +
                        "\n==============================\n\n");
            }
            else if(mainNode.getNodeName().equals("Узел выборки уникальных элементов")){
                resultRunGraph.setText(resultRunGraph.getText() + "Запускается Узел выборки уникальных элементов. Входные параметры: " + mainNode.getNodeInput().get(0) +
                        "\nВыходные параметры: " + mainNode.getNodeOutput() +
                        "\nResult: \n" + mainNode.uniqueWordNode(mainNode.getNodeInput().get(0), mainNode.getNodeOutput()) +
                        "\n==============================\n\n");
            }
            else if(mainNode.getNodeName().equals("Узел фильтрации")){
                resultRunGraph.setText(resultRunGraph.getText() +  "Запускается Узел фильтрации. Входные параметры:  " + mainNode.getNodeInput().get(0) + " " + mainNode.getNodeInput().get(1) +
                        "\nВыходные параметры: " + mainNode.getNodeOutput() +
                        "\nResult: \n" + mainNode.filterNode(mainNode.getNodeInput().get(0), mainNode.getNodeInput().get(1), mainNode.getNodeOutput()) +
                        "\n==============================\n\n");
            }
        }
        }catch (Exception e){
            main.alertNotFNode("Ошибка запуска графа","Ошибка в заданных параметрах");
        }
    }
}
