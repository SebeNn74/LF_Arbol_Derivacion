package presenter;

import model.BranchTree;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter implements ActionListener {

    private View view;
    private BranchTree branchTree;

    public Presenter(){
        run();
    }

    public void run(){
        branchTree = new BranchTree();
        view = new View();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "boton1":
                break;
        }
    }
}
