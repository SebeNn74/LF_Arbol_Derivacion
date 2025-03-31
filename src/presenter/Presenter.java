package presenter;

import model.*;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Presenter implements ActionListener {

    private View view;
    private GrammarChecker grammarChecker;

    public Presenter() {
        view = new View();
        view.setPresenter(this);
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S", "A", "B"));
        String startSymbol = "S";

        List<Production> productions = new ArrayList<>();
        productions.add(new Production("S", Arrays.asList("a", "S", "b")));
        productions.add(new Production("S", Arrays.asList("A")));
        productions.add(new Production("A", Arrays.asList("a")));

        Grammar grammar = new Grammar(terminals, nonTerminals, startSymbol, productions);
        grammarChecker = new GrammarChecker(grammar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "createGrammar":
                createGrammar();
                break;
            case "checkWord":
                checkWord();
                break;
        }
    }

    private void createGrammar() {

    }

    private void checkWord() {
        String wordToCheck = view.getWordToCheck();
        System.out.println("Verificando la palabra: " + wordToCheck);

        if (grammarChecker.checkWord(wordToCheck)) {
            String result = "La palabra pertenece al lenguaje.\n";
            DerivationNode particularTree = grammarChecker.getParticularDerivationTree();
            if (particularTree != null) {
                result += "Árbol de derivación particular:\n" + printTree(particularTree) + "\n";
            }

            DerivationNode generalTree = grammarChecker.getGeneralDerivationTree();
            if (generalTree != null) {
                result += "Árbol de derivación general:\n" + printTree(generalTree);
            }

            view.showResult(result);

            if (particularTree != null) {
                view.showDerivationTree(printTree(particularTree));
            }
            if (generalTree != null) {
                view.showDerivationTree(printTree(generalTree));
            }

            String languageInfo = String.format("Lenguaje: %s\nSímbolos terminales: %s\nSímbolos no terminales: %s",
                    grammarChecker.getGrammar().getStartSymbol(),
                    grammarChecker.getGrammar().getTerminals(),
                    grammarChecker.getGrammar().getNonTerminals());
            view.setLanguageInfo(languageInfo);
        } else {
            view.showResult("La palabra NO pertenece al lenguaje.");
            view.setLanguageInfo("Lenguaje: G (Ejemplo)");
        }
    }

    private String printTree(DerivationNode node) {
        StringBuilder builder = new StringBuilder();
        printTreeRecursive(node, builder, 0);
        return builder.toString();
    }

    private void printTreeRecursive(DerivationNode node, StringBuilder builder, int depth) {
        builder.append("  ".repeat(depth)).append(node.getSymbol()).append("\n");
        for (DerivationNode child : node.getChildren()) {
            printTreeRecursive(child, builder, depth + 1);
        }
    }

}
