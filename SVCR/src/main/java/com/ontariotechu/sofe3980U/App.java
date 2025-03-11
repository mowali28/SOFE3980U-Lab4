package com.svcr;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelEvaluator {
    
    public static void main(String[] args) {
        String[] csvFiles = {"model_1.csv", "model_2.csv", "model_3.csv"};
        double bestMSE = Double.MAX_VALUE, bestMAE = Double.MAX_VALUE, bestMARE = Double.MAX_VALUE;
        String bestMSEModel = "", bestMAEModel = "", bestMAREModel = "";

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
            double mse = calculateMSE(actual, predicted);
            double mae = calculateMAE(actual, predicted);
            double mare = calculateMARE(actual, predicted);

            // Print results
            System.out.printf("  MSE  = %.5f%n", mse);
            System.out.printf("  MAE  = %.5f%n", mae);
            System.out.printf("  MARE = %.8f%n", mare);

            // Determine best model
            if (mse < bestMSE) {
                bestMSE = mse;
                bestMSEModel = file;
            }
            if (mae < bestMAE) {
                bestMAE = mae;
                bestMAEModel = file;
            }
            if (mare < bestMARE) {
                bestMARE = mare;
                bestMAREModel = file;
            }
        }

        // Recommend the best model
        System.out.println("\nAccording to MSE, the best model is " + bestMSEModel);
        System.out.println("According to MAE, the best model is " + bestMAEModel);
        System.out.println("According to MARE, the best model is " + bestMAREModel);
    }

    // Calculate Mean Squared Error (MSE)
    private static double calculateMSE(List<Double> actual, List<Double> predicted) {
        double sum = 0;
        for (int i = 0; i < actual.size(); i++) {
            double error = actual.get(i) - predicted.get(i);
            sum += error * error;
        }
        return sum / actual.size();
    }

    // Calculate Mean Absolute Error (MAE)
    private static double calculateMAE(List<Double> actual, List<Double> predicted) {
        double sum = 0;
        for (int i = 0; i < actual.size(); i++) {
            sum += Math.abs(actual.get(i) - predicted.get(i));
        }
        return sum / actual.size();
    }

    // Calculate Mean Absolute Relative Error (MARE)
    private static double calculateMARE(List<Double> actual, List<Double> predicted) {
        double sum = 0;
        for (int i = 0; i < actual.size(); i++) {
            if (actual.get(i) != 0) { // Avoid division by zero
                sum += Math.abs((actual.get(i) - predicted.get(i)) / actual.get(i));
            }
        }
        return sum / actual.size();
    }
}
