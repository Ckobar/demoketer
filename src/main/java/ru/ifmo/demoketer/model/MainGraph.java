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
    private StringProperty typeKeter;
    private SimpleObjectProperty<List<MainNode>> listOfNodes;


    public MainGraph(String graphName, String graphDescription, String typeKeter, List<MainNode> listOfNodes) {
        this.graphName = new SimpleStringProperty(graphName);
        this.graphDescription = new SimpleStringProperty(graphDescription);
        this.typeKeter = new SimpleStringProperty(typeKeter);
        this.listOfNodes = new SimpleObjectProperty<>(listOfNodes);
    }

    public void setTypeKeter(String typeKeter) {
        this.typeKeter.set(typeKeter);
    }

    public void setListOfNodes(List<MainNode> listOfNodes) {
        this.listOfNodes.set(listOfNodes);
    }

    public String getTypeKeter() {
        return typeKeter.get();
    }

    public StringProperty typeKeterProperty() {
        return typeKeter;
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
