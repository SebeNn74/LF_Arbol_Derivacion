package model;

import java.util.*;

public class GrammarChecker {

    private Grammar grammar;
    private Parser parser;
    private boolean belongs;

    public GrammarChecker(Grammar gramar){
        this.grammar = gramar;
        parser = new Parser();
        belongs = false;
    }

    public boolean checkWord(String wordToCheck){
        belongs = parser.belongsToLanguage(grammar, wordToCheck);
        return belongs;
    }

    public DerivationNode getParticularDerivationTree(){
        return belongs ? parser.getDerivationTree() : null;
    }

    public DerivationNode getGeneralDerivationTree(){
        return belongs ? parser.getDerivationTree() : null;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }
}
