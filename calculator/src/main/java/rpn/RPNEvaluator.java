package rpn;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import core.Constants;
import gui.MainGUI;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RPNEvaluator {
    private String rpnExpr;
    private String theExpr;
    private List<Character> atomsList;
    private boolean[][] truthTable;
    private int numAtoms;
    private int numInterpretations;
    private String toString = "";
    private int numModels;
    MainGUI mainGUI;
    private boolean[][] modelsList;

    public RPNEvaluator(String rpnExpr, List<Character> atomsList, boolean[][] truthTable, String theExpr, MainGUI mainGUI) {
        this.rpnExpr = rpnExpr;
        this.atomsList = atomsList;
        this.truthTable = truthTable;
        this.theExpr = theExpr;
        this.numAtoms = atomsList.size();
        this.numInterpretations = truthTable.length;
        this.mainGUI = mainGUI;
    }

    public boolean processInterpretation(boolean[] interpretation) {
        Stack<Boolean> stack = new Stack<Boolean>();
        for (int i = 0; i < this.rpnExpr.length(); ++i) {
            char token = this.rpnExpr.charAt(i);
            if (Constants.isVoid(token)) continue;
            if (Constants.isOperator(token)) {
                boolean result;
                if (token == Constants.NOT) {
                    boolean atom = (Boolean)stack.pop();
                    boolean result2 = !atom;
                    stack.push(result2);
                    continue;
                }
                boolean atom2 = (Boolean)stack.pop();
                boolean atom1 = (Boolean)stack.pop();
                if (token == Constants.AND) {
                    result = atom1 && atom2;
                    stack.push(result);
                    continue;
                }
                if (token == Constants.OR) {
                    result = atom1 || atom2;
                    stack.push(result);
                    continue;
                }
                if (token == Constants.IMPLIES) {
                    if (atom1 && !atom2) {
                        stack.push(false);
                        continue;
                    }
                    stack.push(true);
                    continue;
                }
                if (token != Constants.BIMPLIES) continue;
                if (atom1 == atom2) {
                    stack.push(true);
                    continue;
                }
                stack.push(false);
                continue;
            }
            int pos = this.findAtom(token);
            boolean atom = interpretation[pos];
            stack.push(atom);
        }
        boolean result = (Boolean)stack.pop();
        return result;
    }

    private int findAtom(char atom) {
        int pos = 0;
        Iterator<Character> i$ = this.atomsList.iterator();
        while (i$.hasNext()) {
            char c = i$.next().charValue();
            if (c == atom) {
                return pos;
            }
            ++pos;
        }
        return -1;
    }

    public boolean[] process(boolean printTruthTable, int initRank, int endRank) {
        this.numModels = 0;
        boolean[] result = new boolean[this.numInterpretations];
        boolean[][] tempModelsList = new boolean[this.numInterpretations][];
        this.mainGUI.progressBar.setMaximum(this.numInterpretations);
        if (printTruthTable) {
            String blanks = Constants.getBlanksLine(this.numInterpretations);
            int trimSpace = blanks.length() - 1;
            Constants.print(blanks + "   ");
            for (int j = 0; j < this.numAtoms; ++j) {
                Constants.print(this.atomsList.get(j) + " ");
            }
            Constants.print("| ");
            Constants.println(this.theExpr);
            Constants.print(blanks + "   ");
            Constants.printlnLineSeparator(this.numAtoms, this.theExpr.length());
            for (int i = initRank; i < endRank; ++i) {
                if (i == 9) {
                    blanks = blanks.substring(0, trimSpace--);
                } else if (i == 99) {
                    blanks = blanks.substring(0, trimSpace--);
                } else if (i == 999) {
                    blanks = blanks.substring(0, trimSpace--);
                } else if (i == 9999) {
                    blanks = blanks.substring(0, trimSpace--);
                } else if (i == 99999) {
                    blanks = blanks.substring(0, trimSpace--);
                }
                Constants.printLineNumber(blanks, i + 1);
                for (int j = 0; j < this.numAtoms; ++j) {
                    Constants.printBoolean(this.truthTable[i][j]);
                }
                Constants.print("| ");
                result[i] = this.processInterpretation(this.truthTable[i]);
                Constants.printBoolean(result[i]);
                if (result[i]) {
                    Constants.print(Constants.VISUAL_OK);
                    tempModelsList[this.numModels] = this.truthTable[i];
                    ++this.numModels;
                }
                Constants.println();
                this.mainGUI.progressBar.setValue(i);
            }
        } else {
            for (int i = 0; i < this.numInterpretations; ++i) {
                result[i] = this.processInterpretation(this.truthTable[i]);
                if (result[i]) {
                    tempModelsList[this.numModels] = this.truthTable[i];
                    ++this.numModels;
                }
                this.mainGUI.progressBar.setValue(i);
            }
        }
        Constants.println("------------");
        Constants.println("Models: " + this.numModels);
        this.modelsList = new boolean[this.numModels][];
        System.arraycopy(tempModelsList, 0, this.modelsList, 0, this.numModels);
        return result;
    }

    public boolean[] processWithDistances(int initRank, int endRank) {
        this.numModels = 0;
        this.toString = "";
        boolean[] result = new boolean[this.numInterpretations];
        boolean[][] tempModelsList = new boolean[this.numInterpretations][];
        this.mainGUI.progressBar.setMaximum(this.numInterpretations);
        for (int i = initRank; i < endRank; ++i) {
            result[i] = this.processInterpretation(this.truthTable[i]);
            if (result[i]) {
                tempModelsList[this.numModels] = this.truthTable[i];
                ++this.numModels;
            }
            this.mainGUI.progressBar.setValue(i);
        }
        this.modelsList = new boolean[this.numModels][];
        System.arraycopy(tempModelsList, 0, this.modelsList, 0, this.numModels);
        return result;
    }

    public boolean[][] getModelsList() {
        return this.modelsList;
    }

    public int getNumModels() {
        return this.numModels;
    }

    public String toString() {
        return this.toString;
    }
}

