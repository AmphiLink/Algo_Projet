package Exo3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Naive {

    public static void main(String[] args) throws IOException {

        // Get the data from the file and get the number of test cases
        List<List<Object>> data = interpretFile(args[0]);

        // For each test case, we calculate the best price
        for (List<Object> testCase : data) {
            int[][] matrix = (int[][]) testCase.get(0);
            int maxWeight = (int) testCase.get(1);
            int result = maxValue(0, maxWeight, matrix);
            System.out.println("Price : " + result);
        }
    }

    public static List<List<Object>> interpretFile(String path) throws IOException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        List<List<Object>> data = new ArrayList<>();

        int nTestCases = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < nTestCases; i++) {
            String[] config = scanner.nextLine().split(" ");
            int nProducts = Integer.parseInt(config[0]);
            int maxWeight = Integer.parseInt(config[1]);

            int[][] matrix = new int[nProducts][2];
            for (int j = 0; j < nProducts; j++) {
                String[] row = scanner.nextLine().split(" ");
                matrix[j][0] = Integer.parseInt(row[0]);
                matrix[j][1] = Integer.parseInt(row[1]);
            }

            List<Object> toAdd = new ArrayList<>();
            toAdd.add(matrix);
            toAdd.add(maxWeight);
            data.add(toAdd);
        }

        scanner.close();

        return data;
    }

    /**
     * @param currentItem     : the current item we are looking at
     * @param remainingWeight : the remaining weight we can use
     * @param items           : the matrix of items
     * @requires currentItem >= 0 && currentItem < items.length
     * @requires remainingWeight >= 0
     * @ensures \result > 0
     * @ensures \forall int i; i >= 0 && i < items.length; \result >= items[i][0]
     **/
    public static int maxValue(int currentItem, int remainingWeight, int[][] items) {
        if (currentItem == items.length) {
            return 0;
        }

        int valueWithoutCurrentItem = maxValue(currentItem + 1, remainingWeight, items);

        if (remainingWeight >= items[currentItem][1]) {
            int valueWithCurrentItem = items[currentItem][0]
                    + maxValue(currentItem + 1, remainingWeight - items[currentItem][1], items);
            return Math.max(valueWithoutCurrentItem, valueWithCurrentItem);
        }

        return valueWithoutCurrentItem;
    }
}