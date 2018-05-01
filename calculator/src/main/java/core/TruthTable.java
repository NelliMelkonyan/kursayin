package core;

public class TruthTable {
    int columns;
    int rows;
    boolean[][] truthTable;

    public TruthTable(int numAtoms) {
        this.columns = numAtoms;
        this.truthTable = this.createTruthTable();
    }

    public boolean[][] getTruthTable() {
        return this.truthTable;
    }

    private boolean[][] createTruthTable() {
        if (this.columns == 1) {
            this.rows = 2;
            boolean[][] interpretMatrix = new boolean[this.rows][this.columns];
            interpretMatrix[0][0] = true;
            interpretMatrix[1][0] = false;
            return interpretMatrix;
        }
        this.rows = 4;
        boolean[][] interpretMatrix = new boolean[this.rows][this.columns];
        interpretMatrix[0][0] = true;
        interpretMatrix[1][0] = true;
        interpretMatrix[2][0] = false;
        interpretMatrix[3][0] = false;
        interpretMatrix[0][1] = true;
        interpretMatrix[1][1] = false;
        interpretMatrix[2][1] = true;
        interpretMatrix[3][1] = false;
        if (this.columns == 2) {
            return interpretMatrix;
        }
        for (int i = 3; i <= this.columns; ++i) {
            interpretMatrix = this.createNewTable(interpretMatrix, i);
        }
        this.rows = interpretMatrix.length;
        return interpretMatrix;
    }

    private boolean[][] createNewTable(boolean[][] tempMatrix, int cols) {
        int i;
        int j;
        int size = (int)Math.pow(2.0, cols);
        int half = size / 2;
        boolean[][] matrix = new boolean[size][cols];
        for (i = 0; i < half; ++i) {
            matrix[i][0] = true;
        }
        for (i = half; i < size; ++i) {
            matrix[i][0] = false;
        }
        for (i = 0; i < half; ++i) {
            for (j = 0; j < cols - 1; ++j) {
                matrix[i][j + 1] = tempMatrix[i][j];
            }
        }
        for (i = 0; i < half; ++i) {
            for (j = 0; j < cols - 1; ++j) {
                matrix[i + half][j + 1] = tempMatrix[i][j];
            }
        }
        return matrix;
    }
}

