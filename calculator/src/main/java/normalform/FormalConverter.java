package normalform;

import core.Constants;

public class FormalConverter {
    private char tokenActual;
    private String resultExpression;
    private String theExpression;
    private int contador = 0;

    public FormalConverter(String theExpression) {
        this.theExpression = theExpression;
        this.resultExpression = "";
    }

    private void nextToken() {
        if (this.contador < this.theExpression.length()) {
            char ch = this.theExpression.charAt(this.contador);
            if (Constants.isVoid(ch)) {
                return;
            }
            if (Constants.isVariable(ch)) {
                this.tokenActual = ch;
            } else if (Constants.isAND(ch)) {
                this.tokenActual = Constants.AND;
            } else if (Constants.isOR(ch)) {
                this.tokenActual = Constants.OR;
            } else if (Constants.isNOT(ch)) {
                this.tokenActual = Constants.NOT;
            } else if (Constants.isLEFT_PAR(ch)) {
                this.tokenActual = Constants.LEFT_PAR;
            } else if (Constants.isRIGHT_PAR(ch)) {
                this.tokenActual = Constants.RIGHT_PAR;
            } else if (Constants.isIMPLIES(ch)) {
                this.tokenActual = Constants.IMPLIES;
            }
            ++this.contador;
        }
    }

    private TokenTreeNode procesaOperando() {
        TokenTreeNode t = null;
        if (Constants.isVariable(this.tokenActual)) {
            t = new TokenTreeNode(null, this.tokenActual, null);
            this.nextToken();
        } else if (this.tokenActual == Constants.NOT) {
            this.nextToken();
            t = new TokenTreeNode(Constants.NOT, this.procesaOperando());
        } else if (this.tokenActual == Constants.LEFT_PAR) {
            this.nextToken();
            t = this.startWFF();
            if (this.tokenActual == Constants.RIGHT_PAR) {
                this.nextToken();
            }
        }
        return t;
    }

    private TokenTreeNode startWFF() {
        return this.procesaOperador(Constants.IMPLIES);
    }

    private TokenTreeNode procesaOperador(char operador) {
        TokenTreeNode t = operador == Constants.AND ? this.procesaOperando() : (operador == Constants.OR ? this.procesaOperador(Constants.AND) : this.procesaOperador(Constants.OR));
        while (this.tokenActual == operador) {
            char tempActual = this.tokenActual;
            this.nextToken();
            if (operador == Constants.AND) {
                t = new TokenTreeNode(t, tempActual, this.procesaOperando());
                continue;
            }
            if (operador == Constants.OR) {
                t = new TokenTreeNode(t, tempActual, this.procesaOperador(Constants.AND));
                continue;
            }
            t = new TokenTreeNode(t, tempActual, this.procesaOperador(Constants.OR));
        }
        return t;
    }

    private TokenTreeNode removeImplies(TokenTreeNode t) {
        if (t.value == Constants.IMPLIES) {
            t = new TokenTreeNode(new TokenTreeNode(Constants.NOT, this.removeImplies(t.leftNode)), Constants.OR, this.removeImplies(t.rightNode));
        } else if (t.value == Constants.AND || t.value == Constants.OR) {
            t.leftNode = this.removeImplies(t.leftNode);
            t.rightNode = this.removeImplies(t.rightNode);
        } else if (t.value == Constants.NOT) {
            t.negatedExpression = this.removeImplies(t.negatedExpression);
        }
        return t;
    }

    private TokenTreeNode pushNots(TokenTreeNode t) {
        if (t.value == Constants.NOT) {
            if (t.negatedExpression.value == Constants.AND) {
                t = new TokenTreeNode(this.pushNots(new TokenTreeNode(Constants.NOT, t.negatedExpression.leftNode)), Constants.OR, this.pushNots(new TokenTreeNode(Constants.NOT, t.negatedExpression.rightNode)));
            } else if (t.negatedExpression.value == Constants.OR) {
                t = new TokenTreeNode(this.pushNots(new TokenTreeNode(Constants.NOT, t.negatedExpression.leftNode)), Constants.AND, this.pushNots(new TokenTreeNode(Constants.NOT, t.negatedExpression.rightNode)));
            } else if (t.negatedExpression.value == Constants.NOT) {
                t = this.pushNots(t.negatedExpression.negatedExpression);
            }
        } else if (t.value == Constants.AND || t.value == Constants.OR) {
            t.leftNode = this.pushNots(t.leftNode);
            t.rightNode = this.pushNots(t.rightNode);
        }
        return t;
    }

    private TokenTreeNode distribute(char op1, char op2, TokenTreeNode t) {
        if (t.value == op2) {
            t.leftNode = this.distribute(op1, op2, t.leftNode);
            t.rightNode = this.distribute(op1, op2, t.rightNode);
        } else if (t.value == op1) {
            TokenTreeNode p = this.distribute(op1, op2, t.leftNode);
            TokenTreeNode q = this.distribute(op1, op2, t.rightNode);
            if (p.value == op2) {
                t = new TokenTreeNode(this.distribute(op1, op2, new TokenTreeNode(p.leftNode, op1, q)), op2, this.distribute(op1, op2, new TokenTreeNode(p.rightNode, op1, q)));
            } else if (q.value == op2) {
                t = new TokenTreeNode(this.distribute(op1, op2, new TokenTreeNode(p, op1, q.leftNode)), op2, this.distribute(op1, op2, new TokenTreeNode(p, op1, q.rightNode)));
            } else {
                t.leftNode = p;
                t.rightNode = q;
            }
        }
        return t;
    }

    private TokenTreeNode convertToDNF(TokenTreeNode t) {
        return this.distribute(Constants.AND, Constants.OR, this.pushNots(this.removeImplies(t)));
    }

    private TokenTreeNode convertToCNF(TokenTreeNode t) {
        return this.distribute(Constants.OR, Constants.AND, this.pushNots(this.removeImplies(t)));
    }

    public void convert() {
        this.nextToken();
        TokenTreeNode wff = this.startWFF();
        Constants.println();
        Constants.println("Well formed formula: ");
        this.processConvert(wff);
        this.resultExpression = this.resultExpression.substring(1, this.resultExpression.length() - 1);
        Constants.println(this.resultExpression);
        Constants.println();
        this.resultExpression = "";
        Constants.println("Conjunctive Normal Form: ");
        TokenTreeNode cnf = this.convertToCNF(wff);
        this.processConvert(cnf);
        this.resultExpression = this.resultExpression.substring(1, this.resultExpression.length() - 1);
        Constants.println(this.resultExpression);
        Constants.println();
        this.resultExpression = "";
        Constants.println("Disyunctive Normal Form: ");
        TokenTreeNode dnf = this.convertToDNF(wff);
        this.processConvert(dnf);
        this.resultExpression = this.resultExpression.substring(1, this.resultExpression.length() - 1);
        Constants.print(this.resultExpression);
        if (BinaryTreePrinter.maxLevel(wff) < 8) {
            Constants.debugln("WFF binary tree:");
            BinaryTreePrinter.printNode(wff);
            Constants.debugln("CNF binary tree:");
            BinaryTreePrinter.printNode(cnf);
            Constants.debugln("DNF binary tree:");
            BinaryTreePrinter.printNode(dnf);
        }
        System.err.println("maxLevel:" + BinaryTreePrinter.maxLevel(wff));
    }

    private void processConvert(TokenTreeNode t) {
        if (t != null) {
            if (Constants.isVariable(t.value)) {
                this.resultExpression = this.resultExpression + t.value;
            } else if (Constants.isAND(t.value) || Constants.isOR(t.value) || Constants.isIMPLIES(t.value)) {
                this.resultExpression = this.resultExpression + "(";
                this.processConvert(t.leftNode);
                String s = Constants.isAND(t.value) ? " " + Constants.AND + " " : (Constants.isOR(t.value) ? " " + Constants.OR + " " : " " + Constants.IMPLIES + " ");
                this.resultExpression = this.resultExpression + s;
                this.processConvert(t.rightNode);
                this.resultExpression = this.resultExpression + ")";
            } else if (Constants.isNOT(t.value)) {
                this.resultExpression = this.resultExpression + "" + Constants.NOT;
                this.processConvert(t.negatedExpression);
            }
        }
    }
}

