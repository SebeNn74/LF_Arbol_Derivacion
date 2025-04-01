package com.formales.view;

import com.formales.model.GProduction;
import com.formales.model.Grammar;
import com.formales.model.GrammarChecker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class View extends Application {

    private GrammarChecker grammarChecker;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewFrame.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Gestión de Gramática");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View() {
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

    private void createGrammar() {
        Grammar grammar = new Grammar();
    }

    private void checkWord() {
        String wordToCheck = "view.getCheckWord()";
        if (grammarChecker.checkWord(wordToCheck)) {
            //La palabra pertenece
            //Muestra arbol de derivación particular de la palabra en horizontal
            //Muestra arbol de derivación general de la gramatica
            //Muestra relación de pertenencia o no pertenencia de la palabra
        } else {
            //La palabra no pertenece
        }
    }

    public String getWordToCheck() {
        return null;
    }

    public void showDerivationTree(String tree) {

    }

    public Set<String> getTerminals(){
        return null;
    }

    public Set<String> getNonTerminals(){
        return null;
    }

    public String getStartSymbol(){
        return null;
    }

    public ArrayList<GProduction> getProductions(){
        return null;
    }

    public String getCheckWord(){
        return null;
    }

    public void setCheckWord(String word){

    }

    public void setTerminals(Set<String> terminals){

    }

    public void setNonTerminals(Set<String> nonTerminals){

    }

    public void setStartSymbol(String startSymbol){

    }

    public void setProductions(ArrayList<GProduction> GProductions){

    }

    public static void main(String[] args) {
        launch(args);
    }

}
