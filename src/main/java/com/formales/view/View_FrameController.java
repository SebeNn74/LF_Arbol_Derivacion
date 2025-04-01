package com.formales.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class View_FrameController implements Initializable {

    @FXML
    private TextField textFieldSymbolT, textFieldSymbolN,textFieldSymbolP1,textFieldSymbolP2, textFieldSymbolS, textFieldCheckWord ;
    @FXML
    private Button  buttonT, buttonN,buttonP,buttonS, buttonSaveGrammar, buttonCheckWord;
    @FXML
    private TextArea textAreaT,textAreaN,textAreaP,textAreaRelacionPertenecia,textAreaArbolDerPar,textAreaArbolDerGen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Inicializando");
    }
}
