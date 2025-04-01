package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class DerivationNode {
    String symbol;
    List<DerivationNode> children;
    GProduction appliedGProduction;

    public DerivationNode(String symbol) {
        this.symbol = symbol;
        this.children = new ArrayList<>();
    }

    public void addChild(DerivationNode child) {
        this.children.add(child);
    }

    public List<DerivationNode> getChildren() {
        return children;
    }

    public String getSymbol() {
        return symbol;
    }

}
