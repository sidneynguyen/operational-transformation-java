import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implements a calculator for finding edits.
 */
public class EditDistanceCalculator {
    /**
     * Calcuates edit distance.
     * @param x     initial string
     * @param y     resulting string
     * @return      the edit distance
     */
    public int calculateLevenshteinDistance(String x, String y) {
        int n = x.length() + 1;
        int m = y.length() + 1;
        int[][] e = new int[n][m];

        // initialize first row and column to 0 1 2 3 ...
        for (int i = 0; i < n; i++) {
            e[i][0] = i;
        }
        for (int j = 0; j < m; j++) {
            e[0][j] = j;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int value = e[i - 1][j - 1];

                // diagonal operation is a delete and insert if not a retain
                if (x.charAt(i - 1) != y.charAt(j - 1)) {
                    value += 2;
                }
                e[i][j] = Math.min(Math.min(1 + e[i - 1][j], 1 + e[i][j - 1]), value);
            }
        }

        return e[n - 1][m - 1];
    }

    /**
     * Generates the edit distance matrix.
     * @param x     the initial string
     * @param y     the resulting string
     * @return      the edit distance matrix
     */
    public int[][] getLevenshteinMatrix(String x, String y) {
        int n = x.length() + 1;
        int m = y.length() + 1;
        int[][] e = new int[n][m];

        // initialize first row and column to 0 1 2 3 ...
        for (int i = 0; i < n; i++) {
            e[i][0] = i;
        }
        for (int j = 0; j < m; j++) {
            e[0][j] = j;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int value = e[i - 1][j - 1];

                // diagonal operation is a delete and insert if not a retain
                if (x.charAt(i - 1) != y.charAt(j - 1)) {
                    value += 2;
                }
                e[i][j] = Math.min(Math.min(1 + e[i - 1][j], 1 + e[i][j - 1]), value);
            }
        }

        return e;
    }

    /**
     * Generates a operation given an edit distance matrix.
     * @param e     the edit distance matrix
     * @param x     the initial string
     * @param y     the resulting string
     * @return      the operation
     */
    public Operation getEdits(int[][] e, String x, String y) {
        int i = x.length();
        int j = y.length();
        LinkedList<OperationComponent> components = new LinkedList<>();

        // if first string is empty, that entire string is inserted
        if (i == 0 && j > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_INSERT,
                    y,
                    j
            ));
            Operation operation = new Operation(new ArrayList<>(components));
            return operation;
        }

        // if second string is empty, then entire string is deleted
        if (j == 0 && i > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_DELETE,
                    x,
                    i
            ));
            Operation operation = new Operation(new ArrayList<>(components));
            return operation;
        }

        while (i != 0 && j != 0) {
            int current = e[i][j];
            int diagonal = e[i - 1][j - 1];
            int left = e[i][j - 1];
            int up = e[i - 1][j];

            // retain if diagonal is same and smaller than left and up
            if (diagonal == current && diagonal < left && diagonal < up) {
                components.addFirst(new OperationComponent(
                        OperationComponent.OP_COMP_RETAIN,
                        null,
                        1
                ));
                i--;
                j--;

            // insert if going left
            } else if (left <= up) {
                components.addFirst(new OperationComponent(
                        OperationComponent.OP_COMP_INSERT,
                        "" + y.charAt(j - 1),
                        1
                ));
                j--;

            // delete if going up
            } else {
                components.addFirst(new OperationComponent(
                        OperationComponent.OP_COMP_DELETE,
                        "" + x.charAt(i - 1),
                        1
                ));
                i--;
            }
        }

        // handle case if top left not reached
        while (i > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_DELETE,
                    "" + x.charAt(i - 1),
                    1
            ));
            i--;
        }
        while (j > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_INSERT,
                    "" + y.charAt(j - 1),
                    1
            ));
            j--;
        }
        Operation operation = new Operation(new ArrayList<>(components));
        return operation;
    }
}
