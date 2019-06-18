/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ifmo.demoketer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.ifmo.demoketer.model.MainNode;
import ru.ifmo.demoketer.view.*;

public class Main extends Application {

    private ObservableList<String> nodeList = FXCollections.observableArrayList();
    private ObservableList<MainNode> nodeData = FXCollections.observableArrayList();

    private Stage primaryStage;
    private BorderPane root;

    public Main(){

        nodeData.add(new MainNode("sortNode", "Sorting file rows",
                new ArrayList<String>(Arrays.asList("sortedNodeIn.txt")),
                "sortedNodeOut.txt",
                "Полное описание ноды sortNode. " +
                        "Входные данные (дефолтные): sortedNodeIn.txt. " +
                        "Нода сортирует строки файла",
                "пример запуска ноды sortNode: зайти во вкладку запуска, указать параметтры и запустить", "node"));
        nodeData.add(new MainNode("countRowsNode", "Counting file rows",
                new ArrayList<String>(Arrays.asList("countRowsNodeIn.txt")),
                "countRowsNodeOut.txt",
                "Полное описание ноды countRowsNode" +
                        "Входные данные (дефолтные): countRowsNodeIn.txt. " +
                        "Нода считает кол-во строк файла",
                "пример запуска ноды countRowsNode: зайти во вкладку запуска, указать параметтры и запустить", "node"));
        nodeData.add(new MainNode("uniqueWordNode", "Find unique file rows",
                new ArrayList<String>(Arrays.asList("uniqueWordNodeIn.txt")),
                "uniqueWordNodeOut.txt",
                "Полное описание ноды uniqueWordNode" +
                        "Входные данные (дефолтные): uniqueWordNodeIn.txt. " +
                        "Нода производит выборку уникальных записей строк файла и создаёт уникальный ключ для каждой строки.",
                "пример запуска ноды uniqueWordNode: зайти во вкладку запуска, указать параметтры и запустить", "node"));
        nodeData.add(new MainNode("filterNode", "Filtering file rows by RegExp.",
                new ArrayList<String>(Arrays.asList("filterNodeIn.txt", "RegExp")),
                "filterNodeOut.txt",
                "Полное описание ноды filterNode" +
                        "Входные данные (дефолтные): filterNodeIn.txt, RegExp. " +
                        "Нода производит фильтрацию строк файла на основании заданного RegExp",
                "пример запуска ноды filterNode: зайти во вкладку запуска, указать параметтры и запустить", "node" ));
        for (MainNode mainNode : nodeData) {
            nodeList.add(mainNode.getNodeName());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Keter");
        //this.primaryStage.setScene(new Scene(root, 1200, 600));
        this.primaryStage.setResizable(false);

        if(authorization()) {
            initRootLayout();
            showNodeData();
        }
        //this.primaryStage.show();
    }

    public void initRootLayout() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/rootLayout.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MainNode> getNodeData() {
        return nodeData;
    }

    public ObservableList<String> getNodeList() {
        return nodeList;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showNodeData() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/sample.fxml"));
            AnchorPane nodeOverview = (AnchorPane) loader.load();

            root.setCenter(nodeOverview);

            ControllerNodeList controller = loader.getController();
            //controller.setMainApp(this);
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authorization() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/authorization.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setTitle("authorization");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ControllerAuthorization controller = loader.getController();
            controller.setDialogStage(dialogStage);
            ///controller.setPerson(person);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            controller.setMainApp(this);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createGraph() {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/createGraph.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setTitle("Create graph");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ControllerGraphCreate controller = loader.getController();
            controller.setDialogStage(dialogStage);
            ///controller.setPerson(person);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            controller.setMainApp(this);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean runNode() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/runNode.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setTitle("Run node");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ControllerNodeRun controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMainApp(this);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean runGraph() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/runGraph.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setTitle("Run graph");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ControllerGraphRun controller = loader.getController();
            controller.setDialogStage(dialogStage);

            controller.setMainApp(this);
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
