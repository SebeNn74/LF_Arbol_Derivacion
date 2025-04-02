package com.formales.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    private Grammar grammar;
    private List<String> wordTokens;
    private int currentTokenIndex;
    private DerivationNode rootNode; // Raíz del árbol de derivación particular

    public boolean belongsToLanguage(Grammar grammar, String word) {
        this.grammar = grammar;
        // Tokenizar la palabra
        this.wordTokens = word.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        this.currentTokenIndex = 0;
        this.rootNode = new DerivationNode(grammar.startSymbol);

        // Parcing / Derivación de la palabra
        boolean success = derive(grammar.startSymbol, rootNode);

        // Verificar si se consumió toda la palabra
        return success && currentTokenIndex == wordTokens.size();
    }

    private boolean derive(String symbol, DerivationNode currentNode) {
        //Si es terminal
        if (grammar.isTerminal(symbol)) {
            // Debe coincidir con el token actual de la palabra y debe igual al simbolo inicial de la gramatica
            if (currentTokenIndex < wordTokens.size() && symbol.equals(wordTokens.get(currentTokenIndex))) {
                currentTokenIndex++; // Consumir token
                return true;
            } else {
                return false; // No coincide
            }
            //Si No es terminal
        } else if (grammar.isNonTerminal(symbol)) {
            // Probar cada una de sus producciones
            List<Production> possibleProductions = grammar.getProductionsFor(symbol);
            int originalTokenIndex = currentTokenIndex; // Guardar estado para backtracking

            for (Production p : possibleProductions) {
                currentNode.appliedProduction = p; // Tentativamente aplica esta producción
                currentNode.children.clear(); // Limpiar hijos de intentos anteriores
                boolean productionSuccess = true;
                currentTokenIndex = originalTokenIndex; // Resetear índice para esta producción

                // Intentar derivar cada símbolo en el lado derecho de la producción
                List<DerivationNode> potentialChildren = new ArrayList<>();
                for (String expandedSymbol : p.expansion) {
                    DerivationNode childNode = new DerivationNode(expandedSymbol);
                    potentialChildren.add(childNode); // Añadir antes de la llamada recursiva

                    if (!derive(expandedSymbol, childNode)) {
                        productionSuccess = false; // Falla si algún símbolo no puede derivarse
                        break; // No continuar con esta producción
                    }
                }

                if (productionSuccess) {
                    // Producción encontrada
                    currentNode.children.addAll(potentialChildren);
                    return true;
                }
                currentNode.appliedProduction = null;
                currentNode.children.clear();
            }

            // Si ninguna producción corresponde
            currentTokenIndex = originalTokenIndex;
            return false;
        }
        return false; // Símbolo desconocido
    }

    public List<Production> getAppliedProductionSequence() {
        if (this.rootNode != null) {
            return this.rootNode.getAppliedProductionSequence();
        } else {
            return new ArrayList<>();
        }
    }

    public List<List<String>> getLeftmostDerivationSequenceSteps() {
        if (this.rootNode == null) {
            return new ArrayList<>();
        }

        List<List<String>> derivationSteps = new ArrayList<>();
        List<Production> productionSequence = this.getAppliedProductionSequence();

        // 1. Estado inicial: El símbolo inicial de la gramática
        List<String> currentForm = new ArrayList<>();
        currentForm.add(this.grammar.startSymbol);
        derivationSteps.add(new ArrayList<>(currentForm));

        // 2. Simular la aplicación de cada producción de la secuencia
        for (Production prodToApply : productionSequence) {
            List<String> nextForm = new ArrayList<>();
            String nonTerminalToReplace = prodToApply.nonTerminal;
            boolean replaced = false;

            // 3. Encontrar el no-terminal más a la izquierda para reemplazar
            int i = 0;
            while (i < currentForm.size()) {
                String symbol = currentForm.get(i);
                if (grammar.isNonTerminal(symbol) && symbol.equals(nonTerminalToReplace)) {
                    // Si lo Encontra Reemplazar: añadir la expansión de la producción
                    nextForm.addAll(prodToApply.expansion); // Añadir los símbolos de la derecha de la prod.
                    replaced = true;
                    i++; // Avanzar el índice después del reemplazo
                } else {
                    // Si no es el símbolo a reemplazar, simplemente copiarlo
                    nextForm.add(symbol);
                    i++;
                }
                // Si ya reemplazamos, simplemente copiamos el resto
                if(replaced && i < currentForm.size()) {
                    nextForm.addAll(currentForm.subList(i, currentForm.size()));
                    break; // Salir del while, ya copiamos el resto
                }

            } // Fin del while para encontrar y reemplazar

            if (!replaced) {
                return new ArrayList<>();
            }

            // 5. Guardar el nuevo paso y actualizar la forma actual
            derivationSteps.add(nextForm);
            currentForm = nextForm;

        }
        return derivationSteps;
    }

    public static String formatDerivationSequence(List<List<String>> sequenceSteps) {
        if (sequenceSteps == null || sequenceSteps.isEmpty()) {
            return "";
        }
        return sequenceSteps.stream()
                .map(step -> String.join("", step))
                .collect(Collectors.joining(" -> "));
    }

}
