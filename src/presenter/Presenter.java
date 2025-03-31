package presenter;

import model.DerivationNode;
import model.Grammar;
import model.GrammarChecker;
import model.Production;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Presenter implements ActionListener {

    private View view;
    private GrammarChecker grammarChecker;

    public Presenter(){
        run();
    }

    public void run(){
        view = new View();
        // --- 1. Definir la Gramática (Ejemplo) ---
        // G = ({a, b}, {S, A}, S, P)
        // P = { S -> a S b, S -> A, A -> a }

        Set<String> terminals = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> nonTerminals = new HashSet<>(Arrays.asList("S", "A", "B"));
        String startSymbol = "S";

        List<Production> productions = new ArrayList<>();
        // 3 Producciones mínimas:
        productions.add(new Production("S", Arrays.asList("a", "S", "b")));
        productions.add(new Production("S", Arrays.asList("A")));
        productions.add(new Production("A", Arrays.asList("a")));

        Grammar grammar = new Grammar(terminals, nonTerminals, startSymbol, productions);

        grammarChecker = new GrammarChecker(grammar);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la palabra a verificar: ");
        String wordToCheck = scanner.nextLine();
        scanner.close();

        System.out.println(grammarChecker.checkWord(wordToCheck)? "SI": "NO");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "createGrammar":
                createGrammar();
                break;
            case "checkWord":
                checkWord();
                break;
        }
    }

    private void createGrammar(){
        //Obtengo gramatica G = {T, N, S, P}
        //Minimo 2 T
        //Minimo 3 N
        //Minimo 3 P
    }

    private void checkWord(){
        //Obtengo la palabra
        String wordToCheck = "";
        if(grammarChecker.checkWord(wordToCheck)){
            //La palabra pertenece
            //Muestra arbol de derivación particular de la palabra en horizontal
            //Muestra arbol de derivación general de la gramatica
            //Muestra relación de pertenencia o no pertenencia de la palabra
        }else{
            //La palabra no pertenece
        }
    }

}
