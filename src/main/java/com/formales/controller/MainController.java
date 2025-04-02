package com.formales.controller;

import com.formales.model.Production;
import com.formales.model.Grammar;
import com.formales.model.GrammarChecker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;


public class MainController implements Initializable {

    @FXML
    private TextField tfTerminalSym, tfNonTerminalSym, tfProductionNT, tfProExpansion, tfInitialSym, tfWordToCheck;
    @FXML
    private Button bTerminal, bNonTerminal, bProduction, bInitialSymbol, bSaveGrammar, bCheckWord;
    @FXML
    private TextArea taTerminals, taNonTerminals, taProductions, taRelationshipBelonging, taParticularDerTree, taGeneralDerTree;

    private GrammarChecker grammarChecker;
    private Grammar grammar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando");
    }

    public MainController() {
        grammar = new Grammar();
        grammarChecker = new GrammarChecker();
    }

    public void createGrammar() {
        if(grammar.isValid()){
            grammar.indexProductions();
            grammarChecker.setGrammar(grammar);
            //Muestra arbol de derivación general de la gramatica
            taGeneralDerTree.setText(grammarChecker.getGeneralDerivationTree());
        }
    }

    public void addTerminal(){
        if(grammar.addTerminal(tfTerminalSym.getText())) {
            taTerminals.setText(grammar.terminalsToString());
        }
    }

    public void addNonTerminal(){
        if(grammar.addNonTerminal(tfNonTerminalSym.getText())){
            taNonTerminals.setText(grammar.nonTerminalsToString());
        }
    }

    public void setStartSymbol(){
        if(!tfInitialSym.getText().isBlank()) {
            if (grammar.setStartSymbol(tfInitialSym.getText())) {
                System.out.println("Inicial: " + tfInitialSym.getText());
            } else {
                System.out.println("No se encuentra en los simbolos terminales");
            }
        }
    }

    public void addProduction(){
        if(!tfProductionNT.getText().isBlank() && !tfProExpansion.getText().isBlank()){
            List<String> expansion = Arrays.asList(tfProExpansion.getText().split(""));
            if(grammar.addProduction(new Production(tfProductionNT.getText(), expansion))){
                taProductions.setText(grammar.productionsToString());
            }
        }
    }


    public void checkWord() {
        if(grammarChecker.getGrammar() != null){
            if (grammarChecker.checkWord(tfWordToCheck.getText())) {
                //La palabra pertenece
                //Muestra relación de pertenencia
                taRelationshipBelonging.setText(grammarChecker.getRelationshipBelonging());
                //Muestra arbol de derivación particular de la palabra en horizontal
                taParticularDerTree.setText(grammarChecker.getParticularDerivationTree());
            } else {
                //La palabra no pertenece
                //Muestra realación de no pertenencia
                taRelationshipBelonging.setText(grammarChecker.getRelationshipBelonging());
            }
        }
    }

}
