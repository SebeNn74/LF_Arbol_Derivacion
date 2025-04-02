package com.formales.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExplorationNode {
    List<String> form; // Lista de símbolo
    List<ExplorationNode> children;
    int depth;

    public ExplorationNode(List<String> form, int depth) {
        this.form = Collections.unmodifiableList(new ArrayList<>(form));
        this.children = new ArrayList<>();
        this.depth = depth;
    }

    // Método para obtener lista de simbolos como un solo String
    public String getFormAsString() {
        if (form.isEmpty()) {
            return "ε";
        }
        return String.join("", form);
    }

    public void addChild(ExplorationNode child) {
        this.children.add(child);
    }

    public List<ExplorationNode> getChildren() { return children; }

}
