package com.ontariotechu.svcr;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MulticlassEvaluator {

    public static void evaluateModel(String csvFile) {
        System.out.println("\nEvaluating Multiclass Model: " + csvFile);

        int numClasses = 5; // Assuming 5 classes
        int[][] confusionMatrix = new int[numClasses][numClasses];
        double crossEntropy = 0;

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] line;
            reader.readNext(); // Skip header

            while ((line = reader.readNext()) != null) {
                int actualClass = Integer.parseInt(line[0]) - 1;  // Convert 1-based to 0-based
                double[] probabilities = new double[numClasses];

                for (int i = 0; i < numClasses; i++) {
                    probabilities[i] = Double.parseDouble(line[i + 1]);
                }

                int predictedClass = argmax(probabilities);

                confusionMatrix[actualClass][predictedClass]++;

                crossEntropy += -Math.log(probabilities[actualClass] + 1e-10);
            }

            crossEntropy /= confusionMatrix.length;

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + csvFile);
            e.printStackTrace();
            return;
        }

        // Print results
        System.out.printf("  Cross-Entropy = %.6f%n", crossEntropy);
        printConfusionMatrix(confusionMatrix);
    }

    private static int argmax(double[] probabilities) {
        int maxIndex = 0;
        double maxProb = probabilities[0];
        for (int i = 1; i < probabilities.length; i++) {
            if (probabilities[i] > maxProb) {
                maxProb = probabilities[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static void printConfusionMatrix(int[][] matrix) {
        System.out.println("  Confusion Matrix:");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.printf("%5d", value);
            }
            System.out.println();
        }
    }
}
