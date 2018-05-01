package core;

import java.util.ArrayList;
import java.util.List;
import gui.MainGUI;
import rpn.RPNConverter;
import rpn.RPNEvaluator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Processor {
    private int numAtoms;
    private int numInterpretations;
    private String rpnExpr;
    private String theExpr;
    private List<Character> atomsList;
    RPNConverter rpnConverter;
    int[] dist;
    MainGUI mainGUI;

    public Processor(String theExpr, MainGUI mainGUI) {
        this.theExpr = theExpr;
        this.rpnConverter = new RPNConverter(theExpr);
        this.rpnExpr = this.rpnConverter.process();
        this.atomsList = new AtomsList().parseAtoms(this.rpnExpr);
        this.numAtoms = this.atomsList.size();
        this.numInterpretations = (int)Math.pow(2.0, this.numAtoms);
        this.mainGUI = mainGUI;
    }

    public String process(boolean printThruthTable) {
        String resultString = "";
        TruthTable t = new TruthTable(this.numAtoms);
        boolean[][] truthTable = t.getTruthTable();
        if (this.rpnConverter.isDeduction()) {
            this.processDeduction(truthTable, printThruthTable);
        } else {
            this.processLogic(truthTable, printThruthTable);
        }
        return resultString;
    }

    public void process(boolean printThruthTable, int initRank, int endRank) {
        TruthTable t = new TruthTable(this.numAtoms);
        boolean[][] truthTable = t.getTruthTable();
        this.processLogic(truthTable, printThruthTable, initRank, endRank);
    }

    public void processWithDistances() {
        int i;
        TruthTable t = new TruthTable(this.numAtoms);
        boolean[][] truthTable = t.getTruthTable();
        RPNEvaluator rpnEvaluator = new RPNEvaluator(this.rpnExpr, this.atomsList, truthTable, this.theExpr, this.mainGUI);
        boolean[] result = rpnEvaluator.processWithDistances(0, truthTable.length);
        boolean[][] models = rpnEvaluator.getModelsList();
        this.dist = new int[this.numInterpretations];
        for (i = 0; i < this.numInterpretations; ++i) {
            if (!result[i]) {
                int menor = this.numAtoms;
                for (int j = 0; j < models.length; ++j) {
                    int diff = 0;
                    for (int a = 0; a < this.numAtoms; ++a) {
                        if (truthTable[i][a] == models[j][a]) continue;
                        ++diff;
                    }
                    if (diff == 1) {
                        menor = 1;
                        break;
                    }
                    if (diff >= menor) continue;
                    menor = diff;
                }
                this.dist[i] = menor;
                continue;
            }
            this.dist[i] = 0;
        }
        for (i = 0; i < this.numInterpretations; ++i) {
            for (int j = 0; j < this.numAtoms; ++j) {
                System.out.print("" + truthTable[i][j] + " ");
            }
            System.out.print(" | " + result[i] + " | ");
            System.out.println("dist()" + this.dist[i]);
        }
    }

    public int[] getDistList() {
        return this.dist;
    }

    public void processLogic(boolean[][] truthTable, boolean printThruthTable) {
        RPNEvaluator rpnEvaluator = new RPNEvaluator(this.rpnExpr, this.atomsList, truthTable, this.theExpr, this.mainGUI);
        rpnEvaluator.process(printThruthTable, 0, truthTable.length);
    }

    private void processLogic(boolean[][] truthTable, boolean printThruthTable, int initRank, int endRank) {
        RPNEvaluator rpnEvaluator = new RPNEvaluator(this.rpnExpr, this.atomsList, truthTable, this.theExpr, this.mainGUI);
        rpnEvaluator.process(printThruthTable, initRank - 1, endRank);
    }

    public void processDeduction(boolean[][] truthTable, boolean printThruthTable) {
        List<String> premisesList = this.rpnConverter.getPremises();
        List<String> conclusionsList = this.rpnConverter.getConclusions();
        int numPremises = premisesList.size();
        boolean[][] premisesTruthTable = new boolean[this.numInterpretations][numPremises];
        int j = 0;
        int p = 1;
        for (String s : premisesList) {
            Constants.println("\nPremise #" + p++ + ":");
            Constants.println("~~~~~~~~~~~~");
            RPNConverter converter = new RPNConverter(s);
            String posfixS = converter.process();
            RPNEvaluator rpnEvaluator = new RPNEvaluator(posfixS, this.atomsList, truthTable, s, this.mainGUI);
            boolean[] result = rpnEvaluator.process(printThruthTable, 0, truthTable.length);
            premisesTruthTable[j] = result;
            ++j;
        }
        int numConclusions = conclusionsList.size();
        boolean[][] conclusionsTruthTable = new boolean[this.numInterpretations][numConclusions];
        j = 0;
        p = 1;
        for (String s : conclusionsList) {
            Constants.println("\nConclusion #" + p + ":");
            Constants.println("~~~~~~~~~~~~~~~");
            RPNConverter converter = new RPNConverter(s);
            String posfixS = converter.process();
            RPNEvaluator rpnEvaluator = new RPNEvaluator(posfixS, this.atomsList, truthTable, s, this.mainGUI);
            boolean[] result = rpnEvaluator.process(printThruthTable, 0, truthTable.length);
            conclusionsTruthTable[j] = result;
            ++j;
        }
        Constants.println();
        Constants.println();
        Constants.println("Logical Entailment truth table:");
        Constants.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String blanks = Constants.getBlanksLine(this.numInterpretations);
        int trimSpace = blanks.length() - 1;
        Constants.print(blanks + "   ");
        for (j = 0; j < this.numAtoms; ++j) {
            Constants.print(this.atomsList.get(j) + " ");
        }
        int lenP = 0;
        ArrayList<String> blanksList = new ArrayList<String>();
        for (String s : premisesList) {
            lenP += s.length() + 1;
            blanksList.add(Constants.getBlanks(s.length() - 1));
        }
        Constants.print("| ");
        for (String s : premisesList) {
            Constants.print(s + " ");
        }
        Constants.print("| ");
        for (String s : conclusionsList) {
            Constants.print(s + " ");
        }
        Constants.println();
        Constants.print(blanks + "   ");
        Constants.printLineSeparator(this.numAtoms, lenP);
        Constants.print("| ");
        Constants.printlnSeparator(conclusionsList.get(0).length());
        boolean isModel = true;
        int models = 0;
        for (int i = 0; i < this.numInterpretations; ++i) {
            if (i == 9) {
                blanks = blanks.substring(0, trimSpace--);
            } else if (i == 99) {
                blanks = blanks.substring(0, trimSpace--);
            } else if (i == 999) {
                blanks = blanks.substring(0, trimSpace--);
            } else if (i == 9999) {
                blanks = blanks.substring(0, trimSpace--);
            }
            Constants.printLineNumber(blanks, i + 1);
            for (j = 0; j < this.numAtoms; ++j) {
                Constants.printBoolean(truthTable[i][j]);
            }
            Constants.print("| ");
            boolean premisasVerdaderas = true;
            for (j = 0; j < numPremises; ++j) {
                if (premisesTruthTable[j][i]) {
                    Constants.printBoolean(true);
                } else {
                    Constants.printBoolean(false);
                    premisasVerdaderas = false;
                }
                Constants.print((String)blanksList.get(j));
            }
            Constants.print("| ");
            for (j = 0; j < numConclusions; ++j) {
                boolean conclusionesVerdaderas = true;
                if (conclusionsTruthTable[j][i]) {
                    Constants.printBoolean(true);
                } else {
                    Constants.printBoolean(false);
                    conclusionesVerdaderas = false;
                }
                if (premisasVerdaderas && conclusionesVerdaderas) {
                    Constants.print(Constants.VISUAL_OK);
                    ++models;
                    continue;
                }
                if (!premisasVerdaderas || conclusionesVerdaderas) continue;
                isModel = false;
                Constants.print(Constants.VISUAL_ERR);
            }
            Constants.println();
        }
        Constants.println();
        if (isModel) {
            Constants.println("LOGICAL ENTAILMENT HOLDS.");
            if (models == 1) {
                Constants.println("There is only 1 model (See " + Constants.VISUAL_OK + ")");
            } else if (models > 1) {
                Constants.println("There are " + models + " models (See " + Constants.VISUAL_OK + ")");
            } else {
                Constants.println("... But no models! Use Belief Merging approach");
            }
        } else {
            Constants.println("LOGICAL ENTAILMENT DOES NOT HOLD (See " + Constants.VISUAL_ERR + ")");
        }
    }

    public int getNumAtoms() {
        return this.numAtoms;
    }

    public int getNumInterpretations() {
        return this.numInterpretations;
    }

    public List<Character> getAtomsList() {
        return this.atomsList;
    }
}

