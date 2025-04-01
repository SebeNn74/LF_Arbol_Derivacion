package com.formales.model;

import java.util.*;

public class Grammar {
    Set<String> terminals;
    Set<String> nonTerminals;
    String startSymbol;
    ArrayList<Production> productions;
    Map<String, List<Production>> productionsMap;

    public Grammar(){
        terminals = new HashSet<>();
        nonTerminals = new HashSet<>();
        startSymbol = "";
        productions = new ArrayList<>();
        this.productionsMap = new HashMap<>();
    }

    public Grammar(Set<String> terminals, Set<String> nonTerminals, String startSymbol, ArrayList<Production> productions) {
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.startSymbol = startSymbol;
        this.productions = productions;
        this.productionsMap = new HashMap<>();
        indexProductions();
    }

    public void indexProductions() {
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

    public boolean addTerminal(String terminalSymbol) {
        return this.terminals.add(terminalSymbol);
    }

    public boolean addNonTerminal(String nonTerminalSymbol) {
        return this.nonTerminals.add(nonTerminalSymbol);
    }

    public boolean setStartSymbol(String startSymbol) {
        //Debe pertenecer a los simbolos no terminales
        if(nonTerminals.contains(startSymbol)){
            this.startSymbol = startSymbol;
            return true;
        }
        return false;
    }

    public void addProduction(Production production) {
        this.productions.add(production);
    }

    public String terminalsToString() {
        return "{" + String.join(", ", terminals) + "}";
    }

    public String nonTerminalsToString() {
        return "{" + String.join(", ", nonTerminals) + "}";
    }

    public String productionsToString(){
        return String.join("\n",productions.stream().map(Production::toString).toList());
    }

    public String generateExplorationTreeString() {
        List<String> lines = new ArrayList<>();

        return String.join("\n", lines);
    }

    public String getStartSymbol() {
        return startSymbol;
    }
}
