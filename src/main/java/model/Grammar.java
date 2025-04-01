package model;

import java.util.*;

public class Grammar {
    Set<String> terminals;
    Set<String> nonTerminals;
    String startSymbol;
    ArrayList<GProduction> GProductions;
    Map<String, List<GProduction>> productionsMap;

    public Grammar(){
        terminals = new HashSet<>();
        nonTerminals = new HashSet<>();
        startSymbol = "";
        GProductions = new ArrayList<>();
    }

    public Grammar(Set<String> terminals, Set<String> nonTerminals, String startSymbol, ArrayList<GProduction> GProductions) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.startSymbol = startSymbol;
        this.GProductions = GProductions;
        this.productionsMap = new HashMap<>();
        indexProductions();
    }

    public void indexProductions() {
        for (GProduction p : GProductions) {
            productionsMap.computeIfAbsent(p.nonTerminal, k -> new ArrayList<>()).add(p);
        }
    }

    public List<GProduction> getProductionsFor(String nonTerminal) {
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

    public void setTerminals(Set<String> terminals) {
        this.terminals = terminals;
    }

    public void setNonTerminals(Set<String> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }

    public void setGProductions(ArrayList<GProduction> GProductions) {
        this.GProductions = GProductions;
    }
}
