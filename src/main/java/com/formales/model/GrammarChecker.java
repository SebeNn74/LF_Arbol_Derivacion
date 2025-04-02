package com.formales.model;

import java.util.List;

public class GrammarChecker {

    private Grammar grammar;
    private Parser parser;
    private boolean belongs;

    public GrammarChecker() {
        parser = new Parser();
        belongs = false;
    }

    public boolean checkWord(String wordToCheck) {
        belongs = parser.belongsToLanguage(grammar, wordToCheck);
        return belongs;
    }

    public String getRelationshipBelonging(){
        return belongs ? "W ∈ L(G) : Pertenece" : "W ∉ L(G) : No pertenece";
    }

    public String getParticularDerivationTree() {
        List<List<String>> sequenceSteps = parser.getLeftmostDerivationSequenceSteps();
        if (!sequenceSteps.isEmpty()) {
            return Parser.formatDerivationSequence(sequenceSteps);
        } else {
            System.out.println("Error: La palabra pertenece pero no se pudo reconstruir la secuencia de derivación.");
        }
        return "";
    }

    public String getGeneralDerivationTree() {
        return grammar.generateSententialFormTreeString(5);
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public Grammar getGrammar() {
        return grammar;
    }

}
