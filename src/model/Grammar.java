package model;

import java.util.*;

public class Grammar {
    Set<String> terminals;
    Set<String> nonTerminals;
    String startSymbol;
    List<Production> productions;
    Map<String, List<Production>> productionsMap;

    public Grammar(Set<String> terminals, Set<String> nonTerminals, String startSymbol, List<Production> productions) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.startSymbol = startSymbol;
        this.productions = productions;
        this.productionsMap = new HashMap<>();
        indexProductions();
    }

    private void indexProductions() {
        for (Production p : productions) {
            productionsMap.computeIfAbsent(p.nonTerminal, k -> new ArrayList<>()).add(p);
        }
    }

    public List<Production> getProductionsFor(String nonTerminal) {
        return productionsMap.getOrDefault(nonTerminal, Collections.emptyList());
    }

    public boolean isTerminal(String symbol) {
        return terminals.contains(symbol);
    }

    public boolean isNonTerminal(String symbol) {
        return nonTerminals.contains(symbol);
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public String getStartSymbol() {
        return startSymbol;
    }
}
