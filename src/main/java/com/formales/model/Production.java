package com.formales.model;

import java.util.List;

public class Production {
    String nonTerminal; // Lado izquierdo
    List<String> expansion; // Lado derecho (lista de terminales y no terminales)

    public Production(String nonTerminal, List<String> expansion) {
        this.nonTerminal = nonTerminal;
        this.expansion = expansion;
    }

    @Override
    public String toString() {
        return nonTerminal + " -> " + String.join("", expansion);
    }

    public List<String> getExpansion() {
        return expansion;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }
}
