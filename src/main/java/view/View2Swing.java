package view;


import model.GProduction;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;

public class View2Swing extends JFrame {

    private AppFrame frame;

    public View2Swing(){
        frame = new AppFrame();
    }

    public Set<String> getTerminals(){
        return frame.getTerminals();
    }

    public Set<String> getNonTerminals(){
        return frame.getNonTerminals();
    }

    public String getStartSymbol(){
        return frame.getStartSymbol();
    }

    public ArrayList<GProduction> getProductions(){
        return frame.getProductions();
    }

    public String getCheckWord(){
        return frame.getCheckWord();
    }

    public void setCheckWord(String word){
        frame.setCheckWord(word);
    }

    public void setTerminals(Set<String> terminals) {
        frame.setTerminals(terminals);
    }

    public void setNonTerminals(Set<String> nonTerminals) {
        frame.setNonTerminals(nonTerminals);
    }

    public void setStartSymbol(String startSymbol) {
        frame.setStartSymbol(startSymbol);
    }

    public void setGProductions(ArrayList<GProduction> GProductions) {
        frame.setProductions(GProductions);
    }

    public void setParticularDerivationTree(String tree) {
        frame.showDerivationTree(tree);
    }

    public AppFrame getFrame() {
        return frame;
    }
}
