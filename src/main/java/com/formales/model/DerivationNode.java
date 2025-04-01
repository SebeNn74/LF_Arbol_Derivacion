package com.formales.model;

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

    public List<Production> getAppliedProductionSequence() {
        List<Production> sequence = new ArrayList<>();
        collectProductionsPreOrder(sequence);
        return sequence;
    }

    private void collectProductionsPreOrder(List<Production> sequenceList) {
        if (this.appliedProduction != null) {
            sequenceList.add(this.appliedProduction);
        }
        for (DerivationNode child : children) {
            child.collectProductionsPreOrder(sequenceList);
        }
    }

}
