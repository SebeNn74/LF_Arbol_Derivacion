package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JTextArea resultArea;
    private JTextArea treeArea;
    private JTextField wordInputField;
    private JButton verifyButton;
    private JLabel languageLabel;
    private JLabel wordLabel;

    public View(ActionListener listener) {
        setTitle("Verificador de Lenguaje");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        wordLabel = new JLabel("Ingrese la palabra a verificar:");
        wordInputField = new JTextField(20);
        verifyButton = new JButton("Verificar");
        topPanel.add(wordLabel);
        topPanel.add(wordInputField);
        topPanel.add(verifyButton);
        add(topPanel, BorderLayout.NORTH);

        treeArea = new JTextArea(15, 40);
        treeArea.setEditable(false);
        JScrollPane treeScrollPane = new JScrollPane(treeArea);
        treeArea.setLineWrap(true);
        treeArea.setWrapStyleWord(true);
        JPanel treePanel = new JPanel(new BorderLayout());
        treePanel.add(treeScrollPane, BorderLayout.CENTER);
        add(treePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        resultArea = new JTextArea(5, 40);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        bottomPanel.add(resultScrollPane, BorderLayout.CENTER);

        languageLabel = new JLabel("Lenguaje: L(G), Gramática S", JLabel.CENTER);
        bottomPanel.add(languageLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getWordToCheck() {
        return wordInputField.getText();
    }

    public void showResult(String result) {
        resultArea.setText(result);
    }

    public void showDerivationTree(String tree) {
        treeArea.setText(tree);
    }

    public void setLanguageInfo(String languageInfo) {
        languageLabel.setText(languageInfo);
    }

    public void setPresenter(ActionListener presenter) {
        verifyButton.addActionListener(presenter);
    }
}
