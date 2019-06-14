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
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.ifmo.demoketer.model.MainNode;

public class Main extends Application {

    private ObservableList<String> nodeList = FXCollections.observableArrayList();
    private ObservableList<MainNode> nodeData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        nodeData.add(new MainNode("sortNode", "Sorting file rows", "sortedNodeIn.txt","sortedNodeOut.txt"));
        nodeData.add(new MainNode("countRowsNode", "Counting file rows", "countRowsNodeIn.txt","countRowsNodeOut.txt"));
        nodeData.add(new MainNode("uniqueWordNode", "Find unique file rows", "uniqueWordNodeIn.txt","uniqueWordNodeOut.txt"));
        nodeData.add(new MainNode("filterNode", "Filtering file rows by RegExp", "filterNodeIn.txt","filterNodeOut.txt"));

        for (MainNode mainNode : nodeData) {
            nodeList.add(mainNode.getNodeName());
        }

        URL resource = getClass().getResource("/sample.fxml");
        Parent root = FXMLLoader.load(resource);
        primaryStage.setTitle("Keter");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public ObservableList<MainNode> getNodeData() {
        return nodeData;
    }

    public ObservableList<String> getNodeList() {
        return nodeList;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
