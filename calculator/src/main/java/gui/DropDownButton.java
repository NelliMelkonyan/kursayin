package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import core.Processor;

public class DropDownButton
extends JToggleButton {
    private static JPopupMenu popup;
    JMenuItem miRun;
    JMenuItem miRunNoTruthTable;
    JMenuItem miRankValues;
    JMenuItem miInterpretationValues;
    MainGUI mainGUI;

    public DropDownButton(String text, MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.setText(text);
        this.setToolTipText("Truth table printing options");
        popup = new JPopupMenu();
        MyMenuItemListener menuItemListener = new MyMenuItemListener();
        this.miRun = new JMenuItem("Process");
        this.miRun.setToolTipText("Process formula printing the overall truth table");
        this.miRun.addActionListener(menuItemListener);
        popup.add(this.miRun);
        popup.addSeparator();
        this.miRunNoTruthTable = new JMenuItem("Process (Only display number of models)");
        this.miRunNoTruthTable.setToolTipText("Process formula or interpretation without printting\nthe corresponding truth table");
        this.miRunNoTruthTable.addActionListener(menuItemListener);
        popup.add(this.miRunNoTruthTable);
        this.miRankValues = new JMenuItem("Rank values for truth table...");
        this.miRankValues.setToolTipText("Introduce init and end values for the resulting truth table");
        this.miRankValues.addActionListener(menuItemListener);
        popup.add(this.miRankValues);
        this.miInterpretationValues = new JMenuItem("Process one interpretation...");
        this.miInterpretationValues.setToolTipText("Introduce values for all the variables in the formula");
        this.miInterpretationValues.addActionListener(menuItemListener);
        popup.add(this.miInterpretationValues);
        popup.addPopupMenuListener(new MyPopupmenuListener(this));
        this.addItemListener(new MyItemListener(this));
    }

    public void activeDropdownAll(boolean active) {
        this.miRunNoTruthTable.setEnabled(active);
        this.miInterpretationValues.setEnabled(active);
        this.miRankValues.setEnabled(active);
    }

    public void activeDropdown2(boolean active) {
        this.miInterpretationValues.setEnabled(active);
        this.miRankValues.setEnabled(active);
    }

    public String getName() {
        return "Truth table printing options";
    }

    private class MyMenuItemListener
    implements ActionListener {
        private MyMenuItemListener() {
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == DropDownButton.this.miRun) {
                DropDownButton.this.mainGUI.go(true);
            } else if (ae.getSource() == DropDownButton.this.miRunNoTruthTable) {
                DropDownButton.this.mainGUI.go(false);
            } else if (ae.getSource() == DropDownButton.this.miInterpretationValues) {
                if (DropDownButton.this.mainGUI.getTheExpression().length() == 0) {
                    return;
                }
                Processor p = new Processor(DropDownButton.this.mainGUI.getTheExpression(), DropDownButton.this.mainGUI);
                InterpretationComponent dlg = new InterpretationComponent(p.getAtomsList());
                int result = JOptionPane.showConfirmDialog(DropDownButton.this.mainGUI, dlg.getComponents(), "Process interpretation", 2, 3);
                if (result == 0) {
                    boolean[][] truthTable = dlg.getVariableValues();
                    DropDownButton.this.mainGUI.goOneInterpretation(p, truthTable);
                }
            } else if (ae.getSource() == DropDownButton.this.miRankValues) {
                if (DropDownButton.this.mainGUI.getTheExpression().length() == 0) {
                    return;
                }
                Processor p = new Processor(DropDownButton.this.mainGUI.getTheExpression(), DropDownButton.this.mainGUI);
                RankValuesComponent dlg = new RankValuesComponent(p.getNumInterpretations());
                int result = JOptionPane.showConfirmDialog(DropDownButton.this.mainGUI, dlg.getComponents(), "Rank values", 2, 3);
                if (result == 0) {
                    int init = (Integer)dlg.txtInitRank.getValue();
                    int end = (Integer)dlg.txtEndRank.getValue();
                    DropDownButton.this.mainGUI.goWithRank(init, end);
                }
            }
        }
    }

    private class MyItemListener
    implements ItemListener {
        DropDownButton btn;

        MyItemListener(DropDownButton btn) {
            this.btn = btn;
        }

        public void itemStateChanged(ItemEvent ie) {
            int state = ie.getStateChange();
            if (state == 1) {
                popup.show(this.btn, 0, this.btn.getHeight());
            }
        }
    }

    private class MyPopupmenuListener
    implements PopupMenuListener {
        DropDownButton btn;

        MyPopupmenuListener(DropDownButton btn) {
            this.btn = btn;
        }

        public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
            this.btn.setSelected(false);
        }

        public void popupMenuCanceled(PopupMenuEvent pme) {
            this.btn.setSelected(false);
        }
    }

}

