package ru.ifmo.demoketer.model;

import java.io.File;

interface Node {
    //78,9 ГБ (84 726 474 282 байт)


    String sortNode(String nodeFileIn, String nodeFileOut);
    String countRowsNode(String nodeFileIn, String nodeFileOut);
    String uniqueWordNode(String nodeFileIn, String nodeFileOut);
    String filterNode(String nodeFileIn, String regExp, String nodeFileOut);
}
