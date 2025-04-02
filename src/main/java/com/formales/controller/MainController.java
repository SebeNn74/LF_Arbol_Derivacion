package com.formales.controller;

import com.formales.model.Production;
import com.formales.model.Grammar;
import com.formales.model.GrammarChecker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;


public class MainController implements Initializable {

    @FXML
    private TextField tfTerminalSym, tfNonTerminalSym, tfProductionNT, tfProExpansion, tfInitialSym, tfWordToCheck;
    @FXML
    private Button bTerminal, bNonTerminal, bProduction, bInitialSymbol, bSaveGrammar, bCheckWord, bRebootGrammar;
    @FXML
    private TextArea taTerminals, taNonTerminals, taProductions, taRelationshipBelonging, taParticularDerTree, taGeneralDerTree;

    private GrammarChecker grammarChecker;
    private Grammar grammar;
    private boolean fixedGrammar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando");
    }

    public MainController() {
        fixedGrammar = false;
        grammar = new Grammar();
        grammarChecker = new GrammarChecker();
    }

    public void createGrammar() {
        if(grammar.isValid()){
            fixedGrammar = true;
            grammar.indexProductions();
            grammarChecker.setGrammar(grammar);
            //Muestra arbol de derivación general de la gramatica
            taGeneralDerTree.setText(grammarChecker.getGeneralDerivationTree());
            showAlert("Exito","La gramática se guardo con exito", Alert.AlertType.INFORMATION);
        }else{
            showAlert("Error","La gramática aún no esta completa", Alert.AlertType.ERROR);
        }
    }

    public void rebootGrammar(){
        if(fixedGrammar){
            grammar = new Grammar();
            taTerminals.setText("");
            taNonTerminals.setText("");
            tfInitialSym.setText("");
            taProductions.setText("");
            tfWordToCheck.setText("");
            taRelationshipBelonging.setText("");
            taParticularDerTree.setText("");
            taGeneralDerTree.setText("");
            showAlert("Exito","Se reinició la gramática", Alert.AlertType.INFORMATION);
        }
    }

    public void addTerminal(){
        if(!tfTerminalSym.getText().isBlank()) {
            if (grammar.addTerminal(tfTerminalSym.getText())) {
                taTerminals.setText(grammar.terminalsToString());
            }else{
                showAlert("Aviso","El simbolo ingresado ya pertence al conjunto de simbolos NO TERMINALES", Alert.AlertType.ERROR);
            }
        }else{
            showAlert("Aviso","El campo no puede estar vacio", Alert.AlertType.WARNING);
        }
        tfTerminalSym.setText("");
    }

    public void addNonTerminal(){
        if(!tfNonTerminalSym.getText().isBlank()) {
            if (grammar.addNonTerminal(tfNonTerminalSym.getText())) {
                taNonTerminals.setText(grammar.nonTerminalsToString());
            }else{
                showAlert("Aviso","El simbolo ingresado ya pertenece al conjunto de simbolos TERMINALES", Alert.AlertType.ERROR);
            }
        }else{
            showAlert("Aviso","El campo no puede estar vacio", Alert.AlertType.WARNING);
        }
        tfNonTerminalSym.setText("");
    }

    public void setStartSymbol(){
        if(!tfInitialSym.getText().isBlank()) {
            if (grammar.setStartSymbol(tfInitialSym.getText())) {
                showAlert("Exito","El simbolo inicial se añadió", Alert.AlertType.INFORMATION);
            } else {
                tfInitialSym.setText("");
                showAlert("Aviso","El simbolo ingresado debe pertenecer a los simbolos NO TERMINALES", Alert.AlertType.ERROR);
            }
        }else{
            showAlert("Aviso","El campo no puede estar vacio", Alert.AlertType.WARNING);
        }
    }

    public void addProduction(){
        if(!tfProductionNT.getText().isBlank() && !tfProExpansion.getText().isBlank()){
            List<String> expansion = Arrays.asList(tfProExpansion.getText().split(""));
            if(grammar.isNonTerminal(tfProductionNT.getText())){
                if(grammar.addProduction(new Production(tfProductionNT.getText(), expansion))){
                    taProductions.setText(grammar.productionsToString());
                    tfProExpansion.setText("");
                    tfProductionNT.setText("");
                }else{
                    tfProExpansion.setText("");
                    showAlert("Aviso","Todos los simbolos del lado derecho de la producción deben pertenecer al conjunto de simbolos TERMINALES" +
                            " o al conjunto de simbolos NO TERMINALES", Alert.AlertType.ERROR);
                }
            }else{
                tfProductionNT.setText("");
                showAlert("Aviso","El simbolo izquierdo de la producción debe pertenecer a los simbolos NO TERMINALES", Alert.AlertType.ERROR);
            }
        }else{
            showAlert("Aviso","Ningun campo de la producción puede estar vacio", Alert.AlertType.WARNING);
        }
    }


    public void checkWord() {
        if(fixedGrammar) {
            if (grammarChecker.getGrammar() != null) {
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
                    //No muestra arbol de derivación particular
                    taParticularDerTree.setText("");
                }
            }
        }else{
            showAlert("Aviso","No se ha guardado aún una gramática", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getButtonTypes().clear();

        ButtonType customButton = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().add(customButton);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                alert.close();
            }
        });
        Toolkit.getDefaultToolkit().beep();
        alert.showAndWait();
    }

}
