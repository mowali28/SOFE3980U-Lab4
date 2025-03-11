package com.ontariotechu.svcr;

public class App {
    public static void main(String[] args) {
        // Define the CSV files that will be evaluated
        String[] csvFiles = {"model_1.csv", "model_2.csv", "model_3.csv"};

        // Call the ModelEvaluator class to analyze the models
        ModelEvaluator.evaluateModels(csvFiles);
    }
}
