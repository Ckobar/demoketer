package ru.ifmo.demoketer.model;

import java.io.*;
import java.util.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainNode implements Node {

    private StringProperty nodeName;
    private StringProperty nodeDescription;
    private SimpleObjectProperty<List<String>> nodeInput;
    private StringProperty nodeOutput;
    private StringProperty nodeFullDescription;
    private StringProperty nodeExample;
    private StringProperty typeKeter;
    private UUID uuidNode = UUID.randomUUID();

    public MainNode(String nodeName, String nodeDescription, List<String> nodeInput, String nodeOutput,
                    String nodeFullDescription, String nodeExample, String typeKeter) {
        this.nodeName = new SimpleStringProperty(nodeName);
        this.nodeDescription = new SimpleStringProperty(nodeDescription);
        this.nodeInput = new SimpleObjectProperty<>(nodeInput);
        this.nodeOutput = new SimpleStringProperty(nodeOutput);
        this.nodeFullDescription = new SimpleStringProperty(nodeFullDescription);
        this.nodeExample = new SimpleStringProperty(nodeExample);
        this.typeKeter = new SimpleStringProperty(typeKeter);
    }

    public MainNode(String nodeName, String nodeDescription, List<String> nodeInput, String nodeOutput,
                    String nodeFullDescription, String nodeExample, String typeKeter, UUID uuidNode) {
        this.nodeName = new SimpleStringProperty(nodeName);
        this.nodeDescription = new SimpleStringProperty(nodeDescription);
        this.nodeInput = new SimpleObjectProperty<>(nodeInput);
        this.nodeOutput = new SimpleStringProperty(nodeOutput);
        this.nodeFullDescription = new SimpleStringProperty(nodeFullDescription);
        this.nodeExample = new SimpleStringProperty(nodeExample);
        this.typeKeter = new SimpleStringProperty(typeKeter);
        this.uuidNode = uuidNode;
    }

    public MainNode() {
    }

    public void setNodeFullDescription(String nodeFullDescription) {
        this.nodeFullDescription.set(nodeFullDescription);
    }

    public void setNodeExample(String nodeExample) {
        this.nodeExample.set(nodeExample);
    }

    public String getNodeFullDescription() {
        return nodeFullDescription.get();
    }

    public StringProperty nodeFullDescriptionProperty() {
        return nodeFullDescription;
    }

    public String getNodeExample() {
        return nodeExample.get();
    }

    public StringProperty nodeExampleProperty() {
        return nodeExample;
    }

    public void setNodeName(String nodeName) {
        this.nodeName.set(nodeName);
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription.set(nodeDescription);
    }

    public void setNodeOutput(String nodeOutput) {
        this.nodeOutput.set(nodeOutput);
    }

    public String getNodeName() {
        return nodeName.get();
    }

    public StringProperty nodeNameProperty() {
        return nodeName;
    }

    public String getNodeDescription() {
        return nodeDescription.get();
    }

    public StringProperty nodeDescriptionProperty() {
        return nodeDescription;
    }

    public String getNodeOutput() {
        return nodeOutput.get();
    }

    public StringProperty nodeOutputProperty() {
        return nodeOutput;
    }

    public void setNodeInput(List<String> nodeInput) {
        this.nodeInput.set(nodeInput);
    }

    public void setUuidNode(UUID uuidNode) {
        this.uuidNode = uuidNode;
    }

    public void setTypeKeter(String typeKeter) {
        this.typeKeter.set(typeKeter);
    }

    public List<String> getNodeInput() {
        return nodeInput.get();
    }

    public SimpleObjectProperty<List<String>> nodeInputProperty() {
        return nodeInput;
    }

    public String getTypeKeter() {
        return typeKeter.get();
    }

    public StringProperty typeKeterProperty() {
        return typeKeter;
    }

    public UUID getUuidNode() {
        return uuidNode;
    }

    @Override
    public String sortNode(String nodeFileIn, String nodeFileOut) {
        String out="Запускается sortNode...\n";
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            FileInputStream inStream = new FileInputStream(nodeFileIn);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            String strLine;

            out+="Данные файла: \n";
            while((strLine = bufferedReader.readLine()) != null){
                arrayList.add(strLine);
                out+=strLine+"\n";
                System.out.println(strLine);
            }

            bufferedReader.close();
            Collections.sort(arrayList);
            System.out.println("--------");

            BufferedWriter bw = new BufferedWriter(new FileWriter(nodeFileOut));
            out+="Выходные данные: \n";
            for(String counter: arrayList){
                bw.write(counter);
                bw.write("\r\n");
                System.out.println(counter);
                out+=counter + "\n";
            }
            bw.close();

        }
        catch(IOException ioEx){
            System.out.println("WTF");
        }
        return out;
    }

    @Override
    public String countRowsNode(String nodeFileIn, String nodeFileOut) {
        String out="Запускается countRowsNode...\n";
        int countRows = 0;
        try{
            FileInputStream inStream = new FileInputStream(nodeFileIn);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            String strLine;

            out+="Данные файла: \n";
            while((strLine = bufferedReader.readLine()) != null){
                countRows++;
                out+=strLine+"\n";
            }
            System.out.println(countRows);
            bufferedReader.close();

            System.out.println("--------");

            out+="Выходные данные: \n";
            BufferedWriter bw = new BufferedWriter(new FileWriter(nodeFileOut));
            bw.write(String.valueOf(countRows));
            out+=countRows;
            bw.close();

        }
        catch(IOException ioEx){
            System.out.println("WTF");
        }
        return out;
    }

    @Override
    public String uniqueWordNode(String nodeFileIn, String nodeFileOut) {
        String out="Запускается uniqueWordNode...\n";
        HashSet<String> hashSet = new HashSet<>();
        HashMap<UUID, String> hashMap = new HashMap<>();
        try{
            FileInputStream inStream = new FileInputStream(nodeFileIn);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            String strLine;

            out+="Данные файла: \n";
            while((strLine = bufferedReader.readLine()) != null){
                hashSet.add(strLine);
                System.out.println(strLine);
                out+=strLine+"\n";
            }

            System.out.println("-----");

            bufferedReader.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(nodeFileOut));
            out+="Выходные данные: \n";
            for(String counter : hashSet){
                UUID uuid = UUID.randomUUID();
                hashMap.put(uuid, counter);
                System.out.println(uuid + " " + counter);
                bw.write(String.valueOf(uuid));
                bw.write(", ");
                bw.write(counter);
                bw.write("\r\n");
                out+=uuid + " " + counter + "\n";
            }

            bw.close();

        }
        catch(IOException ioEx){
            System.out.println("WTF");
        }
        return out;
    }


    @Override
    public String filterNode(String nodeFileIn, String regExp, String nodeFileOut) {
        String out="Запускается filterNode...\n";
        ArrayList<String> arrayList = new ArrayList<>();
        try{
            FileInputStream inStream = new FileInputStream(nodeFileIn);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            String strLine;

            out+="Данные файла: \n";
            while((strLine = bufferedReader.readLine()) != null){
                if (strLine.matches(regExp)) {
                    arrayList.add(strLine);
                }
                out+=strLine+"\n";
                System.out.println(strLine);
            }

            bufferedReader.close();

            System.out.println("--------");

            BufferedWriter bw = new BufferedWriter(new FileWriter(nodeFileOut));
            out+="Выходные данные: \n";
            for(String counter: arrayList){
                bw.write(counter);
                bw.write("\r\n");
                System.out.println(counter);
                out += counter + "\n";
            }
            bw.close();

        }
        catch(IOException ioEx){
            System.out.println("WTF");
        }
        return out;
    }

}
