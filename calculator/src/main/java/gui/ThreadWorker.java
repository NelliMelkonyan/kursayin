package gui;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.util.EmptyStackException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import core.Constants;
import core.Processor;
import gui.MainGUI;
import normalform.FormalConverter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ThreadWorker
extends SwingWorker<Void, Void> {
    private MainGUI mainGUI;
    private long initTime;
    private long finalTime;
    private boolean error;
    private int initRank;
    private int endRank;
    private boolean printTruthTable;

    public ThreadWorker(MainGUI mainGUI, boolean printTruthTable) {
        this.mainGUI = mainGUI;
        this.initRank = -1;
        this.endRank = -1;
        this.printTruthTable = printTruthTable;
    }

    ThreadWorker(MainGUI mainGUI, int initRank, int endRank) {
        this.mainGUI = mainGUI;
        this.initRank = initRank;
        this.endRank = endRank;
        this.printTruthTable = true;
    }

    @Override
    protected Void doInBackground() {
        try {
            if (this.mainGUI.getMode() == Constants.NORMAL_FORM_MODE) {
                this.initComponents();
                this.mainGUI.progressBar.setIndeterminate(true);
                this.mainGUI.progressBar.setStringPainted(false);
                FormalConverter formalConverter = new FormalConverter(this.mainGUI.getTheExpression());
                formalConverter.convert();
            } else {
                Processor p = new Processor(this.mainGUI.getTheExpression(), this.mainGUI);
                this.initComponents();
                Constants.debugln("" + p.getNumAtoms() + " atoms: " + p.getAtomsList());
                Constants.debugln();
                Constants.debugln("Interpretations: 2^" + p.getNumAtoms() + " = " + p.getNumInterpretations());
                Constants.debugln();
                if (this.initRank > 0) {
                    p.process(this.printTruthTable, this.initRank, this.endRank);
                } else {
                    p.process(this.printTruthTable);
                }
            }
            this.error = false;
        }
        catch (OutOfMemoryError e) {
            this.error = true;
            this.finalTime = System.currentTimeMillis();
            Constants.println();
            Constants.println();
            Constants.println(e.toString());
            JOptionPane.showMessageDialog(this.mainGUI, e);
        }
        catch (StringIndexOutOfBoundsException e) {
            this.error = true;
            this.finalTime = System.currentTimeMillis();
            Constants.println();
            Constants.println();
            Constants.println(e.toString());
            JOptionPane.showMessageDialog(this.mainGUI, e);
        }
        catch (EmptyStackException e) {
            this.error = true;
            this.finalTime = System.currentTimeMillis();
            Constants.println();
            Constants.println();
            Constants.println(e.toString());
            JOptionPane.showMessageDialog(this.mainGUI, e);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            this.error = true;
            this.finalTime = System.currentTimeMillis();
            Constants.println();
            Constants.println();
            Constants.println(e.toString());
            JOptionPane.showMessageDialog(this.mainGUI, e);
        }
        catch (Exception e2) {
            this.error = true;
            this.finalTime = System.currentTimeMillis();
            JOptionPane.showMessageDialog(this.mainGUI, e2);
        }
        return null;
    }

    private void initComponents() {
        this.mainGUI.lblStatus.setText("Computing...");
        this.mainGUI.progressBar.setValue(0);
        this.mainGUI.setCursor(Cursor.getPredefinedCursor(3));
        this.initTime = System.currentTimeMillis();
        Constants.debugln("Init time: " + this.initTime);
        Constants.debugln();
        this.mainGUI.ddbGo.setEnabled(false);
    }

    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        this.mainGUI.ddbGo.setEnabled(true);
        this.mainGUI.setCursor(null);
        this.mainGUI.progressBar.setIndeterminate(false);
        this.mainGUI.progressBar.setValue(this.mainGUI.progressBar.getMaximum());
        this.mainGUI.progressBar.setStringPainted(true);
        if (!this.error) {
            this.finalTime = System.currentTimeMillis();
        }
        long totalTime = this.finalTime - this.initTime;
        String text = "Completed in ";
        text = totalTime < 100 ? text + totalTime + " milliseconds" : (totalTime < 1000 ? text + "0." + totalTime + " seconds" : text + (float)totalTime / 1000.0f + " seconds");
        this.mainGUI.lblStatus.setText(text);
        Constants.debugln("End time: " + this.finalTime);
    }
}

