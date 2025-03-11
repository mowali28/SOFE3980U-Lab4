package com.ontariotechu.svcr;

public class App {
    public static void main(String[] args) {
        // Task 1: Regression (MSE, MAE, MARE)
        System.out.println("\n--- Running Regression Task ---");
        String[] regressionFiles = {"model_1.csv", "model_2.csv", "model_3.csv"};
        ModelEvaluator.evaluateModels(regressionFiles);

        // Task 2: Binary Classification (BCE, Confusion Matrix, Accuracy, Precision, Recall, F1-score, AUC-ROC)
        System.out.println("\n--- Running Binary Classification Task ---");
        BinaryEvaluator.evaluateModels(regressionFiles); // Same CSVs used

        // Task 3: Multiclass Classification (Cross-Entropy, Confusion Matrix)
        System.out.println("\n--- Running Multiclass Classification Task ---");
        String multiclassFile = "model.csv"; // Ensure model.csv is present
        MulticlassEvaluator.evaluateModel(multiclassFile);
    }
}
