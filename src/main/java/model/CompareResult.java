package model;

import json.CompareEnum;
import json.DroidRAKey;
import org.json.simple.JSONArray;

import java.util.HashSet;

public class CompareResult {

    private CompareEnum compareEnum;

    private HashSet<DroidRAKey> missingDroidRAKey;

    private HashSet<DroidRAKey> newDroidRAKey;

    private JSONArray actualLeftJson;

    private JSONArray actualRightJson;

    public HashSet<DroidRAKey> getMissingDroidRAKey() {
        return missingDroidRAKey;
    }

    public void setMissingDroidRAKey(HashSet<DroidRAKey> missingDroidRAKey) {
        this.missingDroidRAKey = missingDroidRAKey;
    }

    public HashSet<DroidRAKey> getNewDroidRAKey() {
        return newDroidRAKey;
    }

    public void setNewDroidRAKey(HashSet<DroidRAKey> newDroidRAKey) {
        this.newDroidRAKey = newDroidRAKey;
    }

    public CompareEnum getCompareEnum() {
        return compareEnum;
    }

    public void setCompareEnum(CompareEnum compareEnum) {
        this.compareEnum = compareEnum;
    }

    public JSONArray getActualLeftJson() {
        return actualLeftJson;
    }

    public void setActualLeftJson(JSONArray actualLeftJson) {
        this.actualLeftJson = actualLeftJson;
    }

    public JSONArray getActualRightJson() {
        return actualRightJson;
    }

    public void setActualRightJson(JSONArray actualRightJson) {
        this.actualRightJson = actualRightJson;
    }
}
