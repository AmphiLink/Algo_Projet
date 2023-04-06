import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Naive{

    public static void main(String[] args) throws IOException{

        // Get the data from the file and get the number of test cases
        List<List<Object>> data = interpretFile(args[0]);

        // For each test case, we calculate the best path
        for (List<Object> datum : data) {

            // Get all possible path in this matrix
            List<List<int[]>> allPaths = allPaths((int[][]) datum.get(0), 0, 0, new ArrayList<>());

            // Calculate the number of beers in each path and get the best one
            int nBeer;
            int bestNBeer = -1;
            for (List<int[]> path : allPaths) {
                nBeer = calculatePaths(path, (int[][]) datum.get(0), (int) datum.get(1));
                if (nBeer > bestNBeer) {
                    bestNBeer = nBeer;
                }
            }

            // Print the best number of beers or -1 if there is no possible path
            System.out.println(bestNBeer);
        }
    }

    public static List<List<Object>> interpretFile(String path) throws IOException{

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
            int nRows = Integer.parseInt(config[0]);
            int nColumns = Integer.parseInt(config[1]);
            int nMaxBeers = Integer.parseInt(config[2]);

            // Filling a matrix with a representation of the map
            int[][] matrix = new int[nRows][nColumns];
            for (int j = 0; j < nRows; j++) {
                line = scanner.nextLine();
                String[] row = line.split(" ");
                for (int k = 0; k < nColumns; k++) {
                    matrix[j][k] = Integer.parseInt(row[k]);
                }
            }

            // Adding the matrix and the number max of beers to the data
            List<Object> toAdd = new ArrayList<>();
            toAdd.add(matrix);
            toAdd.add(nMaxBeers);
            data.add(toAdd);
        }

        // Closing the file
        scanner.close();

        // Return the data
        return data;
    }

    public static List<List<int[]>> allPaths(int[][] matrix, int row, int column, List<int[]> path) {

        // Get the number of rows and columns and create a list of all paths
        int nRows = matrix.length;
        int nColumns = matrix[0].length;
        List<List<int[]>> allPaths = new ArrayList<>();

        // Add the current point to the path
        path.add(new int[]{row, column});

        if(column == nColumns - 1) {

            // if we are at the end of the row, we add the path to the list of all paths
            allPaths.add(new ArrayList<>(path));
        } else {
            if(row < nRows - 1 && column < nColumns - 1) {

                // if we are not at the end of the row and the column, we test the path in diagonal
                allPaths.addAll(allPaths(matrix, row + 1, column + 1, new ArrayList<>(path)));
            }
            if(row < nRows - 1) {

                // if we are not at the end of the row, we test the path in the next row
                allPaths.addAll(allPaths(matrix, row + 1, column, new ArrayList<>(path)));
            }
            if(column < nColumns - 1) {

                // if we are not at the end of the column, we test the path in the next column
                allPaths.addAll(allPaths(matrix, row, column + 1, new ArrayList<>(path)));
            }
        }

        // return the list of all paths
        return allPaths;
    }

    public static int calculatePaths(List<int[]> path, int[][] matrix, int nMaxBeers) {

        // Calculate the number of beers in the path
        int nBeer = 0;
        for (int[] point : path) {
            int totalBeers = matrix[point[0]][point[1]];
            nBeer += totalBeers;
            if (nBeer > nMaxBeers) {

                // If the number of beers is greater than the number max of beers, we return -1
                return -1;
            }
        }

        // Return the number of beers (under the number max of beers in this case)
        return nBeer;
    }
}