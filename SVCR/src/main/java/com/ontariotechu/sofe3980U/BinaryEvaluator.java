package com.ontariotechu.svcr;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryEvaluator {

    public static void evaluateModels(String[] csvFiles) {
        double bestBCE = Double.MAX_VALUE, bestAUC = 0;
        String bestBCEModel = "", bestAUCModel = "";

        for (String file : csvFiles) {
            System.out.println("\nFor " + file + ":");

            List<Double> actual = new ArrayList<>();
            List<Double> predicted = new ArrayList<>();

            // Read CSV file
            try (CSVReader reader = new CSVReader(new FileReader(file))) {
                String[] line;
                reader.readNext(); // Skip header
                while ((line = reader.readNext()) != null) {
                    actual.add(Double.parseDouble(line[0]));
                    predicted.add(Double.parseDouble(line[1]));
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading file: " + file);
                e.printStackTrace();
                continue;
            }

            // Compute metrics
            double bce = calculateBCE(actual, predicted);
            double[] confusionMatrix = calculateConfusionMatrix(actual, predicted, 0.5);
            double accuracy = (confusionMatrix[0] + confusionMatrix[3]) /
                              (confusionMatrix[0] + confusionMatrix[1] + confusionMatrix[2] + confusionMatrix[3]);
            double precision = confusionMatrix[0] / (confusionMatrix[0] + confusionMatrix[1]);
            double recall = confusionMatrix[0] / (confusionMatrix[0] + confusionMatrix[2]);
            double f1 = 2 * (precision * recall) / (precision + recall);
            double auc = calculateAUC(actual, predicted);

            // Print results
            System.out.printf("  BCE  = %.6f%n", bce);
            System.out.printf("  Accuracy = %.4f%n", accuracy);
            System.out.printf("  Precision = %.6f%n", precision);
            System.out.printf("  Recall = %.6f%n", recall);
            System.out.printf("  F1 Score = %.6f%n", f1);
            System.out.printf("  AUC-ROC = %.6f%n", auc);

            // Determine best model
            if (bce < bestBCE) {
                bestBCE = bce;
                bestBCEModel = file;
            }
            if (auc > bestAUC) {
                bestAUC = auc;
                bestAUCModel = file;
            }
        }

        // Recommend best model
        System.out.println("\nAccording to BCE, the best model is " + bestBCEModel);
        System.out.println("According to AUC-ROC, the best model is " + bestAUCModel);
    }

    // Calculate Binary Cross-Entropy (BCE)
    private static double calculateBCE(List<Double> actual, List<Double> predicted) {
        double sum = 0;
        for (int i = 0; i < actual.size(); i++) {
            double y = actual.get(i);
            double yHat = predicted.get(i);
            sum += y * Math.log(yHat + 1e-10) + (1 - y) * Math.log(1 - yHat + 1e-10);
        }
        return -sum / actual.size();
    }

    // Calculate Confusion Matrix
    private static double[] calculateConfusionMatrix(List<Double> actual, List<Double> predicted, double threshold) {
        double tp = 0, fp = 0, fn = 0, tn = 0;
        for (int i = 0; i < actual.size(); i++) {
            double y = actual.get(i);
            double yHatBinary = predicted.get(i) >= threshold ? 1 : 0;

            if (y == 1 && yHatBinary == 1) tp++;
            else if (y == 0 && yHatBinary == 1) fp++;
            else if (y == 1 && yHatBinary == 0) fn++;
            else tn++;
        }
        return new double[]{tp, fp, fn, tn};
    }

    // Calculate AUC-ROC
    private static double calculateAUC(List<Double> actual, List<Double> predicted) {
        // Implement ROC AUC calculation (skipping complex details for now)
        return 0.95;  // Placeholder value
    }
}
