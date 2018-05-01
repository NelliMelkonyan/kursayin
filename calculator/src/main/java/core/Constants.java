package core;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;

public class Constants {
    public static final JTextArea TXT_OUTPUT = new JTextArea();
    public static final JTextArea TXT_DEBUG;
    public static char NOT;
    public static char AND;
    public static char OR;
    public static char IMPLIES;
    public static char BIMPLIES;
    public static char DEDUCTION;
    public static char SEPARATOR;
    public static char LEFT_PAR;
    public static char RIGHT_PAR;
    public static String VISUAL_NOT;
    public static String VISUAL_AND;
    public static String VISUAL_OR;
    public static String VISUAL_IMPLIES;
    public static String VISUAL_BIMPLIES;
    public static String VISUAL_DEDUCTION;
    public static String VISUAL_SEPARATOR;
    public static String VISUAL_LEFT_PAR;
    public static String VISUAL_RIGHT_PAR;
    public static String VISUAL_OK;
    public static String VISUAL_ERR;
    public static String VISUAL_BLANK;
    public static int LOGIC_MODE;
    public static int LOGICAL_ENTAILMENT_MODE;
    public static int NORMAL_FORM_MODE;
    public static int MODELS_MODE;

    public static boolean isVariable(char token) {
        return !Constants.isOperator(token) && !Constants.isPar(token);
    }

    public static boolean isOperator(char token) {
        return token == NOT || token == AND || token == OR || token == IMPLIES || token == BIMPLIES || token == DEDUCTION || token == SEPARATOR;
    }

    public static boolean isPar(char token) {
        return token == LEFT_PAR || token == RIGHT_PAR;
    }

    public static boolean isNOT(char operator) {
        return operator == NOT;
    }

    public static boolean isAND(char operator) {
        return operator == AND;
    }

    public static boolean isOR(char operator) {
        return operator == OR;
    }

    public static boolean isIMPLIES(char operator) {
        return operator == IMPLIES;
    }

    public static boolean isBIMPLIES(char operator) {
        return operator == BIMPLIES;
    }

    public static boolean isDEDUCTION(char operator) {
        return operator == DEDUCTION;
    }

    public static boolean isSEPARATOR(char operator) {
        return operator == SEPARATOR;
    }

    public static boolean isLEFT_PAR(char operator) {
        return operator == LEFT_PAR;
    }

    public static boolean isRIGHT_PAR(char operator) {
        return operator == RIGHT_PAR;
    }

    public static boolean isVoid(char c) {
        return c == ' ' || c == '\t';
    }

    public static String toString(char operator) {
        return "" + operator;
    }

    public static void print(String cad) {
        TXT_OUTPUT.append(cad);
    }

    public static void println(String cad) {
        TXT_OUTPUT.append(cad + "\n");
    }

    public static void println() {
        TXT_OUTPUT.append("\n");
    }

    public static void printBoolean(boolean value) {
        if (value) {
            TXT_OUTPUT.append("1 ");
        } else {
            TXT_OUTPUT.append("0 ");
        }
    }

    public static void printlnBoolean(boolean value) {
        if (value) {
            TXT_OUTPUT.append("1\n");
        } else {
            TXT_OUTPUT.append("0\n");
        }
    }

    public static void printLineSeparator(int numAtoms, int resultLenght) {
        int i;
        for (i = 0; i < numAtoms; ++i) {
            TXT_OUTPUT.append("--");
        }
        TXT_OUTPUT.append("|-");
        for (i = 0; i < resultLenght; ++i) {
            TXT_OUTPUT.append("-");
        }
    }

    public static void printlnLineSeparator(int numAtoms, int resultLength) {
        Constants.printLineSeparator(numAtoms, resultLength);
        TXT_OUTPUT.append("\n");
    }

    public static void printlnSeparator(int lenght) {
        for (int i = 0; i < lenght; ++i) {
            TXT_OUTPUT.append("-");
        }
        TXT_OUTPUT.append("\n");
    }

    public static void printLineNumber(String blanks, int lineNo) {
        TXT_OUTPUT.append(blanks + lineNo + "] ");
    }

    public static String getBlanksLine(int numLines) {
        String blanks = "";
        if (numLines > 999999) {
            blanks = "      ";
        } else if (numLines > 99999) {
            blanks = "     ";
        } else if (numLines > 9999) {
            blanks = "    ";
        } else if (numLines > 999) {
            blanks = "   ";
        } else if (numLines > 99) {
            blanks = "  ";
        } else if (numLines > 9) {
            blanks = " ";
        }
        return blanks;
    }

    public static String getBlanks(int spaces) {
        String blanks = "";
        for (int i = 1; i <= spaces; ++i) {
            blanks = blanks + " ";
        }
        return blanks;
    }

    public static void debug(String cad) {
        TXT_DEBUG.append(cad);
    }

    public static void debugln(String cad) {
        TXT_DEBUG.append(cad + "\n");
    }

    public static void debugln() {
        TXT_DEBUG.append("\n");
    }

    static {
        TXT_OUTPUT.setFont(new Font("Monospaced", 0, 14));
        TXT_DEBUG = new JTextArea();
        TXT_DEBUG.setFont(new Font("Monospaced", 0, 13));
        TXT_DEBUG.setForeground(Color.RED);
        NOT = 172;
        AND = 8743;
        OR = 8744;
        IMPLIES = 8594;
        BIMPLIES = 8596;
        DEDUCTION = 8871;
        SEPARATOR = 44;
        LEFT_PAR = 40;
        RIGHT_PAR = 41;
        VISUAL_NOT = "\u00ac";
        VISUAL_AND = "\u2227";
        VISUAL_OR = "\u2228";
        VISUAL_IMPLIES = "\u2192";
        VISUAL_BIMPLIES = "\u2194";
        VISUAL_DEDUCTION = "\u22a7";
        VISUAL_SEPARATOR = ",";
        VISUAL_LEFT_PAR = "(";
        VISUAL_RIGHT_PAR = ")";
        VISUAL_OK = "\u2713";
        VISUAL_ERR = "\u2717";
        VISUAL_BLANK = " ";
        LOGIC_MODE = 0;
        LOGICAL_ENTAILMENT_MODE = 1;
        NORMAL_FORM_MODE = 2;
        MODELS_MODE = 3;
    }
}

