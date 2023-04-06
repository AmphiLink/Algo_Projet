package Exo3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Naive {

    public static void main(String[] args) throws IOException {

        // Get the data from the file and get the number of test cases
        List<List<Object>> data = interpretFile(args[0]);

        // For each test case, we calculate the best price and weight
        for (List<Object> testCase : data) {
            int[][] matrix = (int[][]) testCase.get(0);
            int maxWeight = (int) testCase.get(1);
            int[] results = bagOptimisation(matrix, maxWeight);
            System.out.println("Price : " + results[0] + " Weight : " + results[1]);
        }
    }

    public static List<List<Object>> interpretFile(String path) throws IOException {

        // Open the file given as parameter and extract the first line
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine();
        List<List<Object>> data = new ArrayList<>();

        // Getting the number of test cases
        int nTestCases;
        nTestCases = Integer.parseInt(line);

        for (int i = 0; i < nTestCases; i++) {

            // Go to the next line
            line = scanner.nextLine();

            // Getting the config of the test case
            String[] config = line.split(" ");
            int nProducts = Integer.parseInt(config[0]);
            int maxWeight = Integer.parseInt(config[1]);

            // Filling a matrix with a representation of the items
            int[][] matrix = new int[nProducts][2];
            for (int j = 0; j < nProducts; j++) {
                line = scanner.nextLine();
                String[] row = line.split(" ");
                for (int k = 0; k < 2; k++) {
                    matrix[j][k] = Integer.parseInt(row[k]);
                }
            }

            // Adding the matrix and the number max of weight to the data
            List<Object> toAdd = new ArrayList<>();
            toAdd.add(matrix);
            toAdd.add(maxWeight);
            data.add(toAdd);
        }

        // Closing the file
        scanner.close();

        // Return the data
        return data;
    }

    public static int[] bagOptimisation(int[][] items, int maxWeight) {

        // Sorting the items by decreasing ratio of price/weight
        Arrays.sort(items, (int[] a, int[] b) -> {
            float aRatio = (float) a[0] / a[1];
            float bRatio = (float) b[0] / b[1];
            return Float.compare(bRatio, aRatio);
        });

        // We add the items to the bag until it is full, by ratio of price/weight
        int weight = 0;
        int value = 0;
        for (int[] item : items) {
            if (weight + item[1] <= maxWeight) {
                weight += item[1];
                value += item[0];
            }
        }

        // Return the price and the weight
        return new int[]{value, weight};
    }
}
