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
        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S", "A", "B"));
        String startSymbol = "S";

        ArrayList<GProduction> GProductions = new ArrayList<>();
        GProductions.add(new GProduction("S", Arrays.asList("a", "S", "b")));
        GProductions.add(new GProduction("S", Arrays.asList("A")));
        GProductions.add(new GProduction("A", Arrays.asList("a")));

        Grammar grammar = new Grammar(terminals, nonTerminals, startSymbol, GProductions);
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
        Grammar grammar = new Grammar();
        grammar.setTerminals(view.getTerminals());
        grammar.setNonTerminals(view.getNonTerminals());
        grammar.setStartSymbol(view.getStartSymbol());
        grammar.setGProductions(view.getProductions());
        grammar.indexProductions();
    }

    private void checkWord() {
        String wordToCheck = view.getCheckWord();
        if (grammarChecker.checkWord(wordToCheck)) {
            //La palabra pertenece
            //Muestra arbol de derivación particular de la palabra en horizontal
            //Muestra arbol de derivación general de la gramatica
            //Muestra relación de pertenencia o no pertenencia de la palabra
        } else {
            //La palabra no pertenece
        }
    }

}
