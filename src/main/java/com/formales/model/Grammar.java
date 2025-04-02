package com.formales.model;

import java.util.*;
import java.util.stream.Collectors;

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

    public boolean isValid(){
        //Condiciones para que una gramática sea valida:
        // Terminales = Finito no vacio
        // No terminales = Finito no vacio
        // Simbolo inicial = No vacio, debe pertenecer a los No terminales
        // Producciones = No vacio, NT -> T/NT
        if(!terminals.isEmpty() && !nonTerminals.isEmpty() && !startSymbol.isEmpty() && !productions.isEmpty()){
            return true;
        }
        return false;
    }

    public void indexProductions() {
        for (Production p : productions) {
            productionsMap.computeIfAbsent(p.nonTerminal, k -> new ArrayList<>()).add(p);
        }
    }

    public List<Production> getHashProductionsFor(String nonTerminal) {
        return new ArrayList<>(new HashSet<>(productionsMap.getOrDefault(nonTerminal, List.of())));
    }

    public List<Production> getProductionsFor(String nonTerminal) {
        return new ArrayList<>(productionsMap.getOrDefault(nonTerminal, List.of()));
    }

    public boolean isTerminal(String symbol) {
        return terminals.contains(symbol);
    }

    public boolean isNonTerminal(String symbol) {
        return nonTerminals.contains(symbol);
    }

    public boolean addTerminal(String terminalSymbol) {
        if(!terminalSymbol.isEmpty()){
            //No puede ser un simbolo no terminal
            if(!nonTerminals.contains(terminalSymbol)){
                return this.terminals.add(terminalSymbol);
            }
        }
        return false;
    }

    public boolean addNonTerminal(String nonTerminalSymbol) {
        if (!nonTerminalSymbol.isEmpty()){
            //No puede ser un simbolo terminal
            if(!terminals.contains(nonTerminalSymbol)) {
                return this.nonTerminals.add(nonTerminalSymbol);
            }
        }
        return false;
    }

    public boolean setStartSymbol(String startSymbol) {
        //Debe pertenecer a los simbolos no terminales
        if(isNonTerminal(startSymbol)){
            this.startSymbol = startSymbol;
            return true;
        }
        return false;
    }

    public boolean addProduction(Production production) {
        if(isNonTerminal(production.getNonTerminal())){
            if(production.getExpansion().stream().allMatch(symbol -> isTerminal(symbol) || isNonTerminal(symbol))){
                return this.productions.add(production);
            }
        }
        return false;
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

    private ExplorationNode buildExplorationGraph(int maxDepth) {
        ExplorationNode root = new ExplorationNode(List.of(startSymbol), 0);
        Queue<ExplorationNode> queue = new LinkedList<>();
        queue.add(root);
        Set<String> expandedForms = new HashSet<>();  // Usamos Set<String> en lugar de Set<List<String>>

        while (!queue.isEmpty()) {
            ExplorationNode currentNode = queue.poll();
            String currentFormStr = String.join(" ", currentNode.form); // Convertimos a String único
            // Si ya expandimos esta forma, la ignoramos
            if (!expandedForms.add(currentFormStr)) {
                continue;
            }

            // Límite de profundidad
            if (currentNode.depth >= maxDepth) {
                continue;
            }

            // Buscar el primer no terminal
            int leftmostNTIndex = -1;
            String leftmostNT = null;
            for (int i = 0; i < currentNode.form.size(); i++) {
                String symbol = currentNode.form.get(i);
                if (isNonTerminal(symbol)) {
                    leftmostNTIndex = i;
                    leftmostNT = symbol;
                    break; // Solo tomamos el primer no terminal
                }
            }

            // Si no hay no terminales, no expandimos más
            if (leftmostNT == null) {
                continue;
            }

            // Generar hijos
            List<Production> productions = getHashProductionsFor(leftmostNT);
            for (Production p : productions) {
                List<String> nextFormList = new ArrayList<>(currentNode.form);
                nextFormList.subList(leftmostNTIndex, leftmostNTIndex + 1).clear();
                nextFormList.addAll(leftmostNTIndex, p.getExpansion());
                String nextFormStr = String.join(" ", nextFormList);

                // Si ya expandimos esta forma antes, no la agregamos
                if (!expandedForms.contains(nextFormStr)) {
                    ExplorationNode childNode = new ExplorationNode(nextFormList, currentNode.depth + 1);
                    currentNode.addChild(childNode);
                    queue.add(childNode);
                }
            }
        }
        return root;
    }

    public String generateSententialFormTreeString(int maxDepth) {
        if ( startSymbol == null) return "Gramática no válida.";

        ExplorationNode root = buildExplorationGraph(maxDepth);
        List<String> lines = new ArrayList<>();
        renderExplorationTree(root, lines, "", true, maxDepth);

        return String.join("\n", lines);
    }

    private void renderExplorationTree(ExplorationNode node, List<String> lines, String indent, boolean isLast, int maxDepth) {
        if (node == null) return;
        String linePrefix = indent + (isLast ? "└── " : "├── ");
        if (indent.isEmpty() && isLast) linePrefix = "";
        lines.add(linePrefix + node.getFormAsString());
        String childrenIndent = indent + (isLast ? "    " : "│   ");
        List<ExplorationNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            renderExplorationTree(children.get(i), lines, childrenIndent, (i == children.size() - 1), maxDepth);
        }
    }

}
