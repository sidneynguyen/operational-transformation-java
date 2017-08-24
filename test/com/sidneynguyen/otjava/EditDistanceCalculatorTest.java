package com.sidneynguyen.otjava;

import org.junit.Test;

import static org.junit.Assert.*;

public class EditDistanceCalculatorTest {
    @Test
    public void calculateLevenshteinDistance() throws Exception {
        EditDistanceCalculator calculator = new EditDistanceCalculator();
        int dist = calculator.calculateLevenshteinDistance("PAPER", "PAGES");
        assertEquals(4, dist);
    }

    @Test
    public void getEdits1() throws Exception {
        EditDistanceCalculator calculator = new EditDistanceCalculator();
        int[][] e = calculator.getLevenshteinMatrix("PAPER", "PAGES");
        Operation operation = calculator.getEdits(e, "PAPER", "PAGES");
        operation.simplify();

        Document document = new Document("PAPER");
        document.applyOperation(operation);

        assertEquals("PAGES", document.getData());
    }

    @Test
    public void getEdits2() throws Exception {
        EditDistanceCalculator calculator = new EditDistanceCalculator();
        int[][] e = calculator.getLevenshteinMatrix("PELICAN", "POLITICIAN");
        Operation operation = calculator.getEdits(e, "PELICAN", "POLITICIAN");
        operation.simplify();

        Document document = new Document("PELICAN");
        document.applyOperation(operation);

        assertEquals("POLITICIAN", document.getData());
    }

}