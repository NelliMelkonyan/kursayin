package rpn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import core.Constants;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RPNConverter {
    private String expr;
    public List<String> premises;
    public List<String> conclusions;
    private boolean deduction = false;

    public RPNConverter(String expr) {
        this.expr = expr;
        int pos = expr.indexOf(Constants.VISUAL_DEDUCTION);
        if (pos > 0) {
            this.deduction = true;
            String first = expr.substring(0, pos);
            String last = expr.substring(pos + 1);
            StringTokenizer st = new StringTokenizer(first, ",");
            this.premises = new ArrayList<String>();
            while (st.hasMoreTokens()) {
                this.premises.add(st.nextToken());
            }
            st = new StringTokenizer(last, ",");
            this.conclusions = new ArrayList<String>();
            while (st.hasMoreTokens()) {
                this.conclusions.add(st.nextToken());
            }
        }
    }

    public boolean isDeduction() {
        return this.deduction;
    }

    public List<String> getPremises() {
        return this.premises;
    }

    public List<String> getConclusions() {
        return this.conclusions;
    }

    public String process() {
        Stack<Character> stack = new Stack<Character>();
        String result = "";
        for (int i = 0; i < this.expr.length(); ++i) {
            char token = this.expr.charAt(i);
            if (Constants.isVoid(token)) continue;
            if (token == '(') {
                stack.push(Character.valueOf(token));
                continue;
            }
            if (token == ')') {
                result = this.processParenthesis(result, stack);
                continue;
            }
            if (Constants.isOperator(token)) {
                while (!stack.isEmpty()) {
                    char top = stack.peek().charValue();
                    if (Constants.isDEDUCTION(token) || Constants.isSEPARATOR(token)) {
                        stack.pop();
                        continue;
                    }
                    if (Constants.isNOT(token)) {
                        if (top != Constants.NOT) break;
                        top = stack.pop().charValue();
                        result = result + top;
                        continue;
                    }
                    if (Constants.isAND(token)) {
                        if (!Constants.isAND(top) && !Constants.isNOT(top)) break;
                        top = stack.pop().charValue();
                        result = result + top;
                        break;
                    }
                    if (Constants.isOR(token)) {
                        if (!Constants.isOR(top) && !Constants.isAND(top) && !Constants.isNOT(top)) break;
                        top = stack.pop().charValue();
                        result = result + top;
                        continue;
                    }
                    if (Constants.isIMPLIES(token)) {
                        if (!Constants.isIMPLIES(top) && !Constants.isOR(top) && !Constants.isAND(top) && !Constants.isNOT(top)) break;
                        top = stack.pop().charValue();
                        result = result + top;
                        continue;
                    }
                    if (!Constants.isBIMPLIES(token)) continue;
                    if (!Constants.isBIMPLIES(top) && !Constants.isIMPLIES(top) && !Constants.isOR(top) && !Constants.isAND(top) && !Constants.isNOT(top)) break;
                    top = stack.pop().charValue();
                    result = result + top;
                }
                stack.push(Character.valueOf(token));
                continue;
            }
            result = result + token;
        }
        while (!stack.isEmpty()) {
            result = result + stack.pop();
        }
        Constants.debugln("RPN Expression: " + result);
        Constants.debugln();
        return result;
    }

    private String processParenthesis(String result, Stack<Character> stack) {
        while (!stack.isEmpty()) {
            char top = stack.pop().charValue();
            if (top == '(') {
                return result;
            }
            result = result + top;
        }
        return result;
    }
}

