package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import core.Constants;
import core.Processor;

public class MainGUI
extends JFrame
implements ActionListener,
PropertyChangeListener {
    private int mode = Constants.LOGIC_MODE;
    JTextArea txtInput;
    JTabbedPane tabOutput;
    JTextArea txtOutput;
    JTextArea txtDebug;
    JButton btnGo;
    DropDownButton ddbGo;
    JButton btnBackspace;
    JButton btnClear;
    JButton btnP;
    JButton btnQ;
    JButton btnR;
    JButton btnS;
    JButton btnT;
    JButton btnU;
    JButton btnW;
    JButton btnNOT;
    JButton btnAND;
    JButton btnOR;
    JButton btnIMPLIES;
    JButton btnBIMPLIES;
    JButton btnDEDUCTION;
    JButton btnSEPARATOR;
    JButton btnLeftPar;
    JButton btnRightPar;
    JButton btnA;
    JButton btnB;
    JButton btnC;
    JButton btnD;
    JButton btnE;
    JButton btnF;
    JButton btnG;
    JButton btnH;
    JButton btnI;
    JButton btnJ;
    JButton btnK;
    JButton btnL;
    JButton btnM;
    JButton btnN;
    JButton btnO;
    JButton btnX;
    JButton btnY;
    JButton btnZ;
    JLabel lblStatus;
    public JProgressBar progressBar;
    private JPanel pnlStatusBar;
    private JRadioButton rbLogic;
    private JRadioButton rbDeduction;
    private JRadioButton rbNormalForm;
    private ThreadWorker threadWorker;
    private JMenuItem miExp2;
    private JMenuItem miExp5;
    private JMenuItem miExp10;
    private JMenuItem miExp20;
    private JMenuItem miExp21;
    private JMenuItem miExp22;
    private JMenuItem miExp23;
    private JMenuItem miExp24;
    private JMenuItem miExit;
    private JMenuItem miHelp;
    private JMenuItem miAbout;

    public MainGUI() {
        super("Logic Calculator");
    }

    public void createGUI() {
        JPanel pnlInput = this.createInputPanel();
        this.btnP = new JButton("p");
        this.btnQ = new JButton("q");
        this.btnR = new JButton("r");
        this.btnS = new JButton("s");
        this.btnT = new JButton("t");
        this.btnU = new JButton("u");
        this.btnW = new JButton("w");
        this.btnNOT = new JButton(Constants.VISUAL_NOT);
        this.btnAND = new JButton(Constants.VISUAL_AND);
        this.btnOR = new JButton(Constants.VISUAL_OR);
        this.btnIMPLIES = new JButton(Constants.VISUAL_IMPLIES);
        this.btnBIMPLIES = new JButton(Constants.VISUAL_BIMPLIES);
        this.btnDEDUCTION = new JButton(Constants.VISUAL_DEDUCTION);
        this.btnSEPARATOR = new JButton(Constants.VISUAL_SEPARATOR);
        this.btnLeftPar = new JButton(Constants.VISUAL_LEFT_PAR);
        this.btnRightPar = new JButton(Constants.VISUAL_RIGHT_PAR);
        this.btnP.addActionListener(this);
        this.btnQ.addActionListener(this);
        this.btnR.addActionListener(this);
        this.btnS.addActionListener(this);
        this.btnT.addActionListener(this);
        this.btnU.addActionListener(this);
        this.btnW.addActionListener(this);
        this.btnLeftPar.addActionListener(this);
        this.btnRightPar.addActionListener(this);
        this.btnNOT.addActionListener(this);
        this.btnNOT.setToolTipText("Negation (not)");
        this.btnAND.addActionListener(this);
        this.btnAND.setToolTipText("Conjuntion (and)");
        this.btnOR.addActionListener(this);
        this.btnOR.setToolTipText("Disjunction (or)");
        this.btnIMPLIES.addActionListener(this);
        this.btnIMPLIES.setToolTipText("Implication (conditional)");
        this.btnBIMPLIES.addActionListener(this);
        this.btnBIMPLIES.setToolTipText("Equality (biconditional)");
        this.btnDEDUCTION.addActionListener(this);
        this.btnDEDUCTION.setToolTipText("Deduction");
        this.btnSEPARATOR.addActionListener(this);
        this.btnSEPARATOR.setToolTipText("formula separator");
        this.btnLeftPar.setToolTipText("Left par");
        this.btnRightPar.setToolTipText("Right par");
        JPanel pnlOperators = new JPanel(new GridLayout(3, 0, 5, 5));
        pnlOperators.setBorder(BorderFactory.createTitledBorder("Operators"));
        pnlOperators.add(this.btnNOT);
        pnlOperators.add(this.btnAND);
        pnlOperators.add(this.btnOR);
        pnlOperators.add(this.btnLeftPar);
        pnlOperators.add(this.btnRightPar);
        pnlOperators.add(this.btnIMPLIES);
        pnlOperators.add(this.btnBIMPLIES);
        pnlOperators.add(this.btnDEDUCTION);
        pnlOperators.add(this.btnSEPARATOR);
        this.btnA = new JButton("a");
        this.btnB = new JButton("b");
        this.btnC = new JButton("c");
        this.btnD = new JButton("d");
        this.btnE = new JButton("e");
        this.btnF = new JButton("f");
        this.btnG = new JButton("g");
        this.btnH = new JButton("h");
        this.btnI = new JButton("i");
        this.btnJ = new JButton("j");
        this.btnK = new JButton("k");
        this.btnL = new JButton("l");
        this.btnM = new JButton("m");
        this.btnN = new JButton("n");
        this.btnO = new JButton("o");
        this.btnX = new JButton("x");
        this.btnY = new JButton("y");
        this.btnZ = new JButton("z");
        this.btnA.addActionListener(this);
        this.btnB.addActionListener(this);
        this.btnC.addActionListener(this);
        this.btnD.addActionListener(this);
        this.btnE.addActionListener(this);
        this.btnF.addActionListener(this);
        this.btnG.addActionListener(this);
        this.btnH.addActionListener(this);
        this.btnI.addActionListener(this);
        this.btnJ.addActionListener(this);
        this.btnK.addActionListener(this);
        this.btnL.addActionListener(this);
        this.btnM.addActionListener(this);
        this.btnN.addActionListener(this);
        this.btnO.addActionListener(this);
        this.btnX.addActionListener(this);
        this.btnY.addActionListener(this);
        this.btnZ.addActionListener(this);
        JPanel pnlVariables = new JPanel(new GridLayout(3, 0, 5, 5));
        pnlVariables.setBorder(BorderFactory.createTitledBorder("Variables"));
        pnlVariables.add(this.btnP);
        pnlVariables.add(this.btnQ);
        pnlVariables.add(this.btnR);
        pnlVariables.add(this.btnS);
        pnlVariables.add(this.btnT);
        pnlVariables.add(this.btnA);
        pnlVariables.add(this.btnB);
        pnlVariables.add(this.btnC);
        pnlVariables.add(this.btnD);
        pnlVariables.add(this.btnE);
        pnlVariables.add(this.btnF);
        pnlVariables.add(this.btnG);
        pnlVariables.add(this.btnH);
        pnlVariables.add(this.btnI);
        pnlVariables.add(this.btnJ);
        pnlVariables.add(this.btnK);
        pnlVariables.add(this.btnL);
        pnlVariables.add(this.btnM);
        pnlVariables.add(this.btnN);
        pnlVariables.add(this.btnO);
        pnlVariables.add(this.btnW);
        pnlVariables.add(this.btnX);
        pnlVariables.add(this.btnY);
        pnlVariables.add(this.btnZ);
        JPanel pnlButtons = new JPanel(new BorderLayout());
        pnlButtons.add((Component)pnlInput, "North");
        pnlButtons.add((Component)pnlOperators, "West");
        pnlButtons.add((Component)pnlVariables, "East");
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlNorth.add(pnlButtons);
        this.tabOutput = this.createOutputPanel();
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlCenter.add(this.tabOutput);
        JMenuBar mnuBar = this.createMenu();
        this.setJMenuBar(mnuBar);
        this.btnDEDUCTION.setEnabled(false);
        this.btnSEPARATOR.setEnabled(false);
        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setStringPainted(true);
        this.lblStatus = new JLabel("", 11);
        this.lblStatus.setFont(new Font("", 0, 12));
        this.pnlStatusBar = new JPanel(new BorderLayout(10, 10));
        this.pnlStatusBar.add((Component)this.progressBar, "West");
        this.pnlStatusBar.add((Component)this.lblStatus, "East");
        this.pnlStatusBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.add((Component)pnlNorth, "North");
        this.add((Component)pnlCenter, "Center");
        this.add((Component)this.pnlStatusBar, "South");
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocation(200, 100);
    }

    private JMenuBar createMenu() {
        JMenu menuCalculator = new JMenu("Calculator");
        menuCalculator.setMnemonic(67);
        this.miExit = new JMenuItem("Exit", 69);
        this.miExit.addActionListener(this);
        menuCalculator.add(this.miExit);
        JMenu menuExamples = new JMenu("Examples");
        menuExamples.setMnemonic(69);
        this.miExp2 = new JMenuItem("2 variables formula", 50);
        this.miExp2.addActionListener(this);
        menuExamples.add(this.miExp2);
        this.miExp5 = new JMenuItem("5 variables formula", 53);
        this.miExp5.addActionListener(this);
        menuExamples.add(this.miExp5);
        this.miExp10 = new JMenuItem("10 variables formula", 49);
        this.miExp10.addActionListener(this);
        menuExamples.add(this.miExp10);
        this.miExp20 = new JMenuItem("20 variables formula", 48);
        this.miExp20.addActionListener(this);
        menuExamples.add(this.miExp20);
        menuExamples.addSeparator();
        this.miExp21 = new JMenuItem("21 variables formula");
        this.miExp21.addActionListener(this);
        menuExamples.add(this.miExp21);
        this.miExp22 = new JMenuItem("22 variables formula");
        this.miExp22.addActionListener(this);
        menuExamples.add(this.miExp22);
        this.miExp23 = new JMenuItem("23 variables formula");
        this.miExp23.addActionListener(this);
        menuExamples.add(this.miExp23);
        this.miExp24 = new JMenuItem("24 variables formula");
        this.miExp24.addActionListener(this);
        menuExamples.add(this.miExp24);
        JMenu menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(72);
        this.miHelp = new JMenuItem("Contents", 67);
        this.miHelp.addActionListener(this);
        this.miAbout = new JMenuItem("About", 65);
        this.miAbout.addActionListener(this);
        menuHelp.add(this.miHelp);
        menuHelp.add(this.miAbout);
        JMenuBar theMenuBar = new JMenuBar();
        theMenuBar.add(menuCalculator);
        theMenuBar.add(menuExamples);
        theMenuBar.add(menuHelp);
        return theMenuBar;
    }

    private JPanel createInputPanel() {
        this.txtInput = new JTextArea("", 2, 2);
        this.txtInput.setFont(new Font("", 1, 16));
        this.txtInput.setEditable(false);
        JScrollPane scrollInput = new JScrollPane(this.txtInput);
        scrollInput.setVerticalScrollBarPolicy(21);
        this.btnGo = new JButton(" \u21b5 ");
        this.btnGo.addActionListener(this);
        this.btnGo.setToolTipText("Go!");
        this.ddbGo = new DropDownButton(" \u21b5 ", this);
        this.btnBackspace = new JButton(" \u2190 ");
        this.btnBackspace.addActionListener(this);
        this.btnBackspace.setToolTipText("Backspace");
        this.btnClear = new JButton(" CE ");
        this.btnClear.addActionListener(this);
        this.btnClear.setToolTipText("Clear");
        JPanel pnlSub = new JPanel();
        pnlSub.add(this.btnBackspace);
        pnlSub.add(this.btnClear);
        JPanel pnlGo = new JPanel();
        pnlGo.add(this.btnGo);
        this.btnGo.setVisible(false);
        pnlGo.add(this.ddbGo);
        pnlGo.add(this.btnBackspace);
        pnlGo.add(this.btnClear);
        this.rbLogic = new JRadioButton("<html>Logic Formula Evaluator<html>");
        this.rbLogic.addActionListener(this);
        this.rbLogic.setSelected(true);
        this.rbLogic.setMnemonic(66);
        this.rbDeduction = new JRadioButton("<html>Logical Entailment<html>");
        this.rbDeduction.setHorizontalAlignment(0);
        this.rbDeduction.addActionListener(this);
        this.rbDeduction.setMnemonic(68);
        this.rbNormalForm = new JRadioButton("<html>FNC/FND Converter<html>");
        this.rbNormalForm.addActionListener(this);
        this.rbNormalForm.setMnemonic(70);
        ButtonGroup rbGroup = new ButtonGroup();
        rbGroup.add(this.rbLogic);
        rbGroup.add(this.rbDeduction);
        rbGroup.add(this.rbNormalForm);
        JPanel pnlMode = new JPanel(new BorderLayout(10, 10));
        TitledBorder borde = BorderFactory.createTitledBorder(" ");
        borde.setTitleJustification(2);
        borde.setTitlePosition(0);
        pnlMode.setBorder(borde);
        pnlMode.add((Component)this.rbLogic, "West");
        pnlMode.add(this.rbDeduction);
        pnlMode.add((Component)this.rbNormalForm, "East");
        JPanel thePanel = new JPanel(new BorderLayout());
        thePanel.add(scrollInput);
        thePanel.add((Component)pnlGo, "East");
        thePanel.add((Component)pnlMode, "South");
        return thePanel;
    }

    private JTabbedPane createOutputPanel() {
        this.txtOutput = Constants.TXT_OUTPUT;
        JScrollPane scrollOutput = new JScrollPane(this.txtOutput);
        this.txtDebug = Constants.TXT_DEBUG;
        JScrollPane scrollDebug = new JScrollPane(this.txtDebug);
        JTabbedPane theTabbedPane = new JTabbedPane();
        theTabbedPane.addTab("Output", scrollOutput);
        theTabbedPane.addTab("Debug", scrollDebug);
        return theTabbedPane;
    }

    private void clearAll() {
        this.txtInput.setText("");
        this.clearOutput();
    }

    private void clearOutput() {
        this.txtOutput.setText("");
        this.txtDebug.setText("");
        this.progressBar.setValue(0);
        this.lblStatus.setText("");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnGo) {
            this.go(true);
        } else if (e.getSource() == this.miHelp) {
            new HelpDialog(this).setVisible(true);
        } else if (e.getSource() == this.miAbout) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String today = formatter.format(new Date());
            JOptionPane.showMessageDialog(this, "<html><strong>Logic Calculator</strong> version <strong>1.0</strong><br /><br /><pre style='font-size: small'>Requires JRE 5 or above</pre><br />Last compilation: <strong>" + today + "</strong><br /><br />" + "Contact: <pre style='color: blue'>chavez@programador.com</pre>" + "<pre  style='color: blue'>maripozos@gmail.com</pre></html>", "About Logic Calculator", 1);
        } else if (e.getSource() == this.miExp2) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b ");
        } else if (e.getSource() == this.miExp5) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b \u2227 c \u2228 d \u2192 e ");
        } else if (e.getSource() == this.miExp10) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b \u2227 c \u2228 d \u2192 e \u2192 ( f \u2227 g \u2192 h \u2227 ( i \u2228 j ) ) ");
        } else if (e.getSource() == this.miExp20) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b \u2227 c \u2228 d \u2192 e \u2192 ( f \u2227 g \u2192 h \u2227 ( i \u2228 j ) ) \u2192 k \u2227 l \u2228 \u00acm \u2228 n \u2227 o \u2192 p \u2228 q \u2192 r \u2227 s \u2192 t ");
        } else if (e.getSource() == this.miExp21) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b \u2227 c \u2228 d \u2192 e \u2192 ( f \u2227 g \u2192 h \u2227 ( i \u2228 j ) ) \u2192 k \u2227 l \u2228 \u00acm \u2228 n \u2227 o \u2192 p \u2228 q \u2192 r \u2227 s \u2192 ( t \u2227 w ) ");
        } else if (e.getSource() == this.miExp22) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b \u2227 c \u2228 d \u2192 e \u2192 ( f \u2227 g \u2192 h \u2227 ( i \u2228 j ) ) \u2192 k \u2227 l \u2228 \u00acm \u2228 n \u2227 o \u2192 p \u2228 q \u2192 r \u2227 s \u2192 ( t \u2227 w \u2227 x ) ");
        } else if (e.getSource() == this.miExp23) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b \u2227 c \u2228 d \u2192 e \u2192 ( f \u2227 g \u2192 h \u2227 ( i \u2228 j ) ) \u2192 k \u2227 l \u2228 \u00acm \u2228 n \u2227 o \u2192 p \u2228 q \u2192 r \u2227 s \u2192 ( t \u2227 w \u2227 x \u2192 y ) ");
        } else if (e.getSource() == this.miExp24) {
            this.clearAll();
            this.txtInput.setText("a \u2192 b \u2227 c \u2228 d \u2192 e \u2192 ( f \u2227 g \u2192 h \u2227 ( i \u2228 j ) ) \u2192 k \u2227 l \u2228 \u00acm \u2228 n \u2227 o \u2192 p \u2228 q \u2192 r \u2227 s \u2192 ( t \u2227 w \u2227 x \u2192 y \u2227 \u00acz ) ");
        } else if (e.getSource() == this.miExit) {
            System.exit(0);
        } else if (e.getSource() == this.rbLogic) {
            this.changeToLogicMode();
        } else if (e.getSource() == this.rbDeduction) {
            this.changeToDeductionMode();
        } else if (e.getSource() == this.rbNormalForm) {
            this.changeToNormalFormMode();
        } else if (e.getSource() == this.btnBackspace) {
            this.backspace();
        } else if (e.getSource() == this.btnClear) {
            this.clearAll();
        } else if (e.getSource() == this.btnSEPARATOR) {
            this.txtInput.append(Constants.VISUAL_SEPARATOR);
        } else if (e.getSource() == this.btnNOT) {
            this.txtInput.append(Constants.VISUAL_NOT);
        } else if (e.getSource() == this.btnAND) {
            this.txtInput.append(Constants.VISUAL_AND + " ");
        } else if (e.getSource() == this.btnOR) {
            this.txtInput.append(Constants.VISUAL_OR + " ");
        } else if (e.getSource() == this.btnIMPLIES) {
            this.txtInput.append(Constants.VISUAL_IMPLIES + " ");
        } else if (e.getSource() == this.btnBIMPLIES) {
            this.txtInput.append(Constants.VISUAL_BIMPLIES + " ");
        } else if (e.getSource() == this.btnDEDUCTION) {
            this.txtInput.append(Constants.VISUAL_DEDUCTION + " ");
        } else if (e.getSource() == this.btnLeftPar) {
            this.txtInput.append(Constants.VISUAL_LEFT_PAR + " ");
        } else if (e.getSource() == this.btnRightPar) {
            this.txtInput.append(Constants.VISUAL_RIGHT_PAR + " ");
        } else if (e.getSource() == this.btnP) {
            this.txtInput.append("p ");
        } else if (e.getSource() == this.btnQ) {
            this.txtInput.append("q ");
        } else if (e.getSource() == this.btnR) {
            this.txtInput.append("r ");
        } else if (e.getSource() == this.btnS) {
            this.txtInput.append("s ");
        } else if (e.getSource() == this.btnT) {
            this.txtInput.append("t ");
        } else if (e.getSource() == this.btnU) {
            this.txtInput.append("u ");
        } else if (e.getSource() == this.btnW) {
            this.txtInput.append("w ");
        } else if (e.getSource() == this.btnX) {
            this.txtInput.append("x ");
        } else if (e.getSource() == this.btnY) {
            this.txtInput.append("y ");
        } else if (e.getSource() == this.btnZ) {
            this.txtInput.append("z ");
        } else if (e.getSource() == this.btnA) {
            this.txtInput.append("a ");
        } else if (e.getSource() == this.btnB) {
            this.txtInput.append("b ");
        } else if (e.getSource() == this.btnC) {
            this.txtInput.append("c ");
        } else if (e.getSource() == this.btnD) {
            this.txtInput.append("d ");
        } else if (e.getSource() == this.btnE) {
            this.txtInput.append("e ");
        } else if (e.getSource() == this.btnF) {
            this.txtInput.append("f ");
        } else if (e.getSource() == this.btnG) {
            this.txtInput.append("g ");
        } else if (e.getSource() == this.btnH) {
            this.txtInput.append("h ");
        } else if (e.getSource() == this.btnI) {
            this.txtInput.append("i ");
        } else if (e.getSource() == this.btnJ) {
            this.txtInput.append("j ");
        } else if (e.getSource() == this.btnK) {
            this.txtInput.append("k ");
        } else if (e.getSource() == this.btnL) {
            this.txtInput.append("l ");
        } else if (e.getSource() == this.btnM) {
            this.txtInput.append("m ");
        } else if (e.getSource() == this.btnN) {
            this.txtInput.append("n ");
        } else if (e.getSource() == this.btnO) {
            this.txtInput.append("o ");
        }
    }

    public void go(boolean printTruthTable) {
        if (this.getTheExpression().length() == 0) {
            return;
        }
        this.clearOutput();
        this.threadWorker = new ThreadWorker(this, printTruthTable);
        this.threadWorker.addPropertyChangeListener(this);
        this.threadWorker.execute();
        this.setSize(this.getWidth(), 600);
    }

    public void goWithRank(int init, int end) {
        if (init > end) {
            JOptionPane.showMessageDialog(this, "Wrong final value", "Error", 0);
            return;
        }
        this.clearOutput();
        this.threadWorker = new ThreadWorker(this, init, end);
        this.threadWorker.addPropertyChangeListener(this);
        this.threadWorker.execute();
        this.setSize(this.getWidth(), 600);
    }

    public void goOneInterpretation(Processor p, boolean[][] truthTable) {
        this.clearOutput();
        this.progressBar.setValue(0);
        p.processLogic(truthTable, true);
        this.setSize(this.getWidth(), 550);
        this.progressBar.setValue(this.progressBar.getMaximum());
        this.progressBar.setStringPainted(true);
    }

    public void goWithModels(int init, int end) {
        this.clearOutput();
        this.threadWorker = new ThreadWorker(this, init, end);
        this.threadWorker.addPropertyChangeListener(this);
        this.threadWorker.execute();
    }

    public void propertyChange(PropertyChangeEvent pce) {
    }

    private void backspace() {
        if (this.txtInput.getText().length() == 0) {
            return;
        }
        String tempInput = this.txtInput.getText().substring(0, this.txtInput.getText().length() - 2).trim();
        this.txtInput.setText(tempInput + " ");
    }

    private void cleanInput(String wrongSymbol) {
        int pos = this.txtInput.getText().indexOf(wrongSymbol);
        if (pos > 0) {
            this.txtInput.setText(this.txtInput.getText().substring(0, pos));
        }
    }

    private void changeToLogicMode() {
        this.btnBIMPLIES.setEnabled(true);
        this.btnDEDUCTION.setEnabled(false);
        this.btnSEPARATOR.setEnabled(false);
        this.ddbGo.activeDropdownAll(true);
        this.mode = Constants.LOGIC_MODE;
        this.cleanInput(Constants.VISUAL_SEPARATOR);
        this.cleanInput(Constants.VISUAL_DEDUCTION);
        this.clearOutput();
    }

    private void changeToNormalFormMode() {
        this.btnBIMPLIES.setEnabled(false);
        this.btnDEDUCTION.setEnabled(false);
        this.btnSEPARATOR.setEnabled(false);
        this.ddbGo.activeDropdownAll(false);
        this.mode = Constants.NORMAL_FORM_MODE;
        this.cleanInput(Constants.VISUAL_BIMPLIES);
        this.cleanInput(Constants.VISUAL_SEPARATOR);
        this.cleanInput(Constants.VISUAL_DEDUCTION);
        this.clearOutput();
    }

    private void changeToDeductionMode() {
        this.btnBIMPLIES.setEnabled(true);
        this.btnDEDUCTION.setEnabled(true);
        this.btnSEPARATOR.setEnabled(true);
        this.ddbGo.activeDropdownAll(true);
        this.ddbGo.activeDropdown2(false);
        this.mode = Constants.LOGICAL_ENTAILMENT_MODE;
        this.clearOutput();
    }

    public String getTheExpression() {
        return this.txtInput.getText().replaceAll("\\s", "");
    }

    public int getMode() {
        return this.mode;
    }
}

