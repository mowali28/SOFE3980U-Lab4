package com.ontariotechu.svcr;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelEvaluator {
    
    public static void evaluateModels(String[] csvFiles) {
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
                    actual.add
