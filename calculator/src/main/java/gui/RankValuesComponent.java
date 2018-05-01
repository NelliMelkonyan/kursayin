package gui;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RankValuesComponent {
    JSpinner txtInitRank;
    JSpinner txtEndRank;
    JButton btnOk;
    JButton btnCancel;
    private int endRank;

    public RankValuesComponent(int endRank) {
        this.endRank = endRank;
    }

    public JComponent[] getComponents() {
        SpinnerNumberModel modelInit = new SpinnerNumberModel(1, 1, this.endRank, 1);
        this.txtInitRank = new JSpinner(modelInit);
        SpinnerNumberModel modelEnd = new SpinnerNumberModel(this.endRank, 1, this.endRank, 1);
        this.txtEndRank = new JSpinner(modelEnd);
        JComponent[] inputs = new JComponent[]{new JLabel("Init:"), this.txtInitRank, new JLabel("End:"), this.txtEndRank};
        return inputs;
    }
}

