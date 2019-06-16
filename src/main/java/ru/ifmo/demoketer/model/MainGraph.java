package ru.ifmo.demoketer.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGraph {
    private StringProperty graphName;
    private StringProperty graphDescription;
    private SimpleObjectProperty<List<MainNode>> listOfNodes;

    public MainGraph(String graphName, String graphDescription, List<MainNode> listOfNodes) {
        this.graphName = new SimpleStringProperty(graphName);
        this.graphDescription = new SimpleStringProperty(graphDescription);
        this.listOfNodes = new SimpleObjectProperty<>(listOfNodes);
    }

    public void setGraphDescription(String graphDescription) {
        this.graphDescription.set(graphDescription);
    }

    public void setListOfNodes(ArrayList<MainNode> listOfNodes) {
        this.listOfNodes.set(listOfNodes);
    }

    public String getGraphDescription() {
        return graphDescription.get();
    }

    public StringProperty graphDescriptionProperty() {
        return graphDescription;
    }

    public List<MainNode> getListOfNodes() {
        return listOfNodes.get();
    }

    public SimpleObjectProperty<List<MainNode>> listOfNodesProperty() {
        return listOfNodes;
    }

    public void setGraphName(String graphName) {
        this.graphName.set(graphName);
    }

    public String getGraphName() {
        return graphName.get();
    }

    public StringProperty graphNameProperty() {
        return graphName;
    }

}
