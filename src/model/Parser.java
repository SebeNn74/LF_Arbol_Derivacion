package model;

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

    public DerivationNode getDerivationTree() {
        return rootNode;
    }
}
