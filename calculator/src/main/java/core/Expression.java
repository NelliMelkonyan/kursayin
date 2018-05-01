package core;

public class Expression {
    private String visualExpression = new String();
    private String internalExpression = new String();
    private String rpnExpression = new String();

    public String getVisualExpression() {
        return this.visualExpression;
    }

    public void setVisualExpression(String visualExpression) {
        this.visualExpression = visualExpression;
    }

    public String getInternalExpression() {
        return this.internalExpression;
    }

    public void setInternalExpression(String internalExpression) {
        this.internalExpression = internalExpression;
    }

    public String getRPNExpression() {
        return this.rpnExpression;
    }

    public void setRPNExpression(String rpnExpression) {
        this.rpnExpression = rpnExpression;
    }

    public void clear() {
        this.visualExpression = new String();
        this.internalExpression = new String();
        this.rpnExpression = new String();
    }
}

