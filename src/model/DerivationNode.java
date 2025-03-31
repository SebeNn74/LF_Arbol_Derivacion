package model;

import java.util.ArrayList;
import java.util.List;

public class DerivationNode {
    String symbol;
    List<DerivationNode> children;
    Production appliedProduction;

    public DerivationNode(String symbol) {
        this.symbol = symbol;
        this.children = new ArrayList<>();
    }

    public void addChild(DerivationNode child) {
        this.children.add(child);
    }

}
