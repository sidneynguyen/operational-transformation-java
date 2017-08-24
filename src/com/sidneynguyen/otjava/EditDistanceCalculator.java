package com.sidneynguyen.otjava;

import java.util.ArrayList;
import java.util.LinkedList;

public class EditDistanceCalculator {
    public int calculateLevenshteinDistance(String x, String y) {
        int n = x.length() + 1;
        int m = y.length() + 1;
        int[][] e = new int[n][m];
        for (int i = 0; i < n; i++) {
            e[i][0] = i;
        }
        for (int j = 0; j < m; j++) {
            e[0][j] = j;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int value = e[i - 1][j - 1];
                if (x.charAt(i - 1) != y.charAt(j - 1)) {
                    value += 2;
                }
                e[i][j] = Math.min(Math.min(1 + e[i - 1][j], 1 + e[i][j - 1]), value);
            }
        }

        return e[n - 1][m - 1];
    }

    public int[][] getLevenshteinMatrix(String x, String y) {
        int n = x.length() + 1;
        int m = y.length() + 1;
        int[][] e = new int[n][m];
        for (int i = 0; i < n; i++) {
            e[i][0] = i;
        }
        for (int j = 0; j < m; j++) {
            e[0][j] = j;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int value = e[i - 1][j - 1];
                if (x.charAt(i - 1) != y.charAt(j - 1)) {
                    value += 2;
                }
                e[i][j] = Math.min(Math.min(1 + e[i - 1][j], 1 + e[i][j - 1]), value);
            }
        }

        return e;
    }

    public Operation getEdits(int[][] e, String x, String y) {
        int i = x.length();
        int j = y.length();
        LinkedList<OperationComponent> components = new LinkedList<>();
        if (i == 0 && j > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_INSERT,
                    0,
                    y,
                    j
            ));
            Operation operation = new Operation(new ArrayList<>(components));
            return operation;
        }
        if (j == 0 && i > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_DELETE,
                    0,
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

            if (diagonal == current && diagonal < left && diagonal < up) {
                components.addFirst(new OperationComponent(
                        OperationComponent.OP_COMP_RETAIN,
                        0,
                        null,
                        1
                ));
                i--;
                j--;
            } else if (left <= up) {
                components.addFirst(new OperationComponent(
                        OperationComponent.OP_COMP_INSERT,
                        0,
                        "" + y.charAt(j - 1),
                        1
                ));
                j--;
            } else {
                components.addFirst(new OperationComponent(
                        OperationComponent.OP_COMP_DELETE,
                        0,
                        "" + x.charAt(i - 1),
                        1
                ));
                i--;
            }
        }
        while (i > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_DELETE,
                    0,
                    "" + x.charAt(i - 1),
                    1
            ));
            i--;
        }
        while (j > 0) {
            components.addFirst(new OperationComponent(
                    OperationComponent.OP_COMP_INSERT,
                    0,
                    "" + y.charAt(j - 1),
                    1
            ));
            j--;
        }
        Operation operation = new Operation(new ArrayList<>(components));
        return operation;
    }
}
