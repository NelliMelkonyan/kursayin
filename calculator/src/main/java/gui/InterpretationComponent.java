package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class InterpretationComponent
implements ActionListener {
    JCheckBox[] chkArray;
    List<Character> lstVariables;
    JTable tblInterpretation;
    JButton btnAll;

    public InterpretationComponent(List<Character> variables) {
        this.lstVariables = variables;
    }

    public JComponent[] getComponents2() {
        this.tblInterpretation = new JTable(this.lstVariables.size(), 2);
        for (int i = 0; i < this.lstVariables.size(); ++i) {
            this.tblInterpretation.setValueAt(this.lstVariables.get(i), i, 0);
            this.tblInterpretation.setValueAt("", i, 1);
        }
        this.tblInterpretation.getColumnModel().getColumn(0).setHeaderValue("Variable");
        this.tblInterpretation.getColumnModel().getColumn(1).setHeaderValue("Value");
        this.tblInterpretation.setSelectionMode(0);
        this.tblInterpretation.setShowVerticalLines(true);
        JScrollPane scroll = new JScrollPane(this.tblInterpretation);
        scroll.setPreferredSize(new Dimension(100, 200));
        JComponent[] inputs = new JComponent[]{new JLabel("Variable values:"), scroll};
        return inputs;
    }

    public boolean[][] getVariableValues2() {
        boolean[][] truthTable = new boolean[1][this.lstVariables.size()];
        for (int i = 0; i < this.lstVariables.size(); ++i) {
            truthTable[0][i] = this.tblInterpretation.getValueAt(i, 1).toString().trim().startsWith("1");
        }
        return truthTable;
    }

    public JComponent[] getComponents() {
        JPanel panel = new JPanel(new GridLayout(this.lstVariables.size() + 3, 2));
        this.btnAll = new JButton("All True");
        this.btnAll.addActionListener(this);
        panel.add(new JLabel());
        panel.add(this.btnAll);
        Font f = new Font(null, 1, 12);
        JLabel lblVariable = new JLabel("Variable", 0);
        lblVariable.setFont(f);
        panel.add(lblVariable);
        JLabel lblValue = new JLabel("Value", 0);
        lblValue.setFont(f);
        panel.add(lblValue);
        panel.add(new JLabel("\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba", 0));
        panel.add(new JLabel("\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba\u23ba", 0));
        this.chkArray = new JCheckBox[this.lstVariables.size()];
        int i = 0;
        for (Character c : this.lstVariables) {
            panel.add(new JLabel(" " + c + " ", 0));
            this.chkArray[i] = new JCheckBox("True");
            panel.add(this.chkArray[i++]);
        }
        JComponent[] inputs = new JComponent[]{panel};
        return inputs;
    }

    public boolean[][] getVariableValues() {
        boolean[][] truthTable = new boolean[1][this.lstVariables.size()];
        for (int i = 0; i < this.lstVariables.size(); ++i) {
            truthTable[0][i] = this.chkArray[i].isSelected();
        }
        return truthTable;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.btnAll) {
            for (int i = 0; i < this.chkArray.length; ++i) {
                this.chkArray[i].setSelected(true);
            }
        }
    }
}

