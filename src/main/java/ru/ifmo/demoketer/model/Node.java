package ru.ifmo.demoketer.model;

import java.io.File;
import java.io.IOException;

interface Node {
    //78,9 ГБ (84 726 474 282 байт)


    String sortNode(String nodeFileIn, String nodeFileOut) throws IOException;
    String countRowsNode(String nodeFileIn, String nodeFileOut) throws IOException;
    String uniqueWordNode(String nodeFileIn, String nodeFileOut) throws IOException;
    String filterNode(String nodeFileIn, String regExp, String nodeFileOut) throws IOException;
}
