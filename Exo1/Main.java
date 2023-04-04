import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

//Spécifie chaque procédure en JML
public class Main {

    /**
     * Print the object
     * @param o
     * @requires o != null;
     * @ensures true;
     * @assignable System.out;
     * @pure;
     */
    public static void print(Object o) {
        // @ requires o != null;
        // @ ensures true;
        // @ assignable System.out;

        System.out.println(o);
    }

    /**
     * Main method
     * 
     *@param args
     *@throws IOException
     *@requires args.length() == 1;
     *@ensures true;
     *@assignable System.out;
     *@throws IOException;
     */
    public static void main(String[] args) throws IOException {

        HashMap<String, Integer> counts;
        ArrayList<ArrayList<String>> flowers = readFile(args[0]);

        print("=== Methode Naive ===");
        for (ArrayList<String> listFlowers : flowers) {
            counts = new HashMap<>();
            print(naive(listFlowers, counts));
        }

        print("\n=== Methode Recursive ===");
        for (ArrayList<String> listFlowers : flowers) {
            counts = new HashMap<>();
            print(recursive(listFlowers, counts, 0, null));
        }
        
        print("\n=== Methode Diviser Pour Regner ===");
        for (ArrayList<String> listeFleurs : flowers) {
            String tooMuch = diviserPourRegner(listeFleurs, 0, listeFleurs.size() - 1);
            print(tooMuch);
        }

    }

    /**
     * Read the file and return a list of list of plants
     * 
     *@param path
     *@return a list of list of plants
     *@throws IOException
     *@requires path != null;
     *@ensures \result != null;
     *@assignable System.out;
     *@pure;
     */
    public static ArrayList<ArrayList<String>> readFile(String path) throws IOException {
        
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        ArrayList<ArrayList<String>> globList = new ArrayList<ArrayList<String>>();

        //read the file
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            
            //if number we ignore
            if (i == 0 || i % 2 == 1) {
                i++;
                continue;
            } 
            //If String we add into a list
            else {
                String[] tabValues = line.split(" ");
                ArrayList<String> listFlowers = new ArrayList<String>();
                for (String value : tabValues) {
                    listFlowers.add(value);
                }
                globList.add(listFlowers);
            }
            i++;
        }
        scanner.close();
        return globList;
    }
    /**
     * Naive method
     * 
     *@param listFlowers
     *@param counts
     *@return the invasive plant
     *@requires listFlowers != null;
     *@ensures \result != null;
     *@assignable System.out;
     *@pure;
     */
    public static String naive(ArrayList<String> listFlowers, HashMap<String, Integer> counts) {
        
        //Fill the HashMap
        for (String plant : listFlowers) {
            counts.put(plant, counts.getOrDefault(plant, 0) + 1);
        }
        
        //If plant is present more than numberOfPlants/2 the plant is invasive
        String tooMuch = null;
        for (String plant : counts.keySet()) {
            if (counts.get(plant) > listFlowers.size() / 2) {
                tooMuch = plant;
                break;
            }
        }
        return tooMuch;
    }

    /**
     * Recursive method
     * 
     *@requires listFlowers != null;
     *@requires counts != null;
     *@requires currentPlant >= 0;
     *@ensures \result != null;
     *@assignable System.out;
     *@pure;
     */
    public static String recursive(ArrayList<String> listFlowers, HashMap<String, Integer> counts, int currentPlant,
            String invasivePlant) {
        String plant = listFlowers.get(currentPlant);
        counts.put(plant, counts.getOrDefault(plant, 0) + 1);
        if (counts.get(plant) > listFlowers.size() / 2) {
            invasivePlant = plant;
        }
        //We increment currentPlant before calling recursive function
        if (++currentPlant < listFlowers.size()) {
            invasivePlant = recursive(listFlowers, counts, currentPlant, invasivePlant);
        }
        return invasivePlant;
    }
    
    /**
     * Divid and conquer method
     * 
     * @param listeFleurs The list of plants.
     * @param fleur The plant to count.
     * @param left The leftmost index of the sublist to count.
     * @param right The rightmost index of the sublist to count.
     * 
     * @requires listeFleurs != null && fleur != null && left >= 0 && right < listeFleurs.size() && left <= right
     * @ensures The method returns the number of occurrences of the plant in the sublist.
     * @assignable None.
     * @throws None.
     * @pure.
     */
    public static String diviserPourRegner(ArrayList<String> listeFleurs, int left, int right) {
        // If the sublist is empty, return null.
        if (left == right) {
            return null;
        }
        int mid = (left + right) / 2;
        // Divide the list into two sublists.
        String leftResult = diviserPourRegner(listeFleurs, left, mid);
        String rightResult = diviserPourRegner(listeFleurs, mid + 1, right);
        // Count the occurrences of the plant in the sublist.
        int count = counter(listeFleurs, listeFleurs.get(mid), left, right);
        // totalCount is the total number of elements in the sublist.
        int totalCount = right - left + 1;
        // If the plant occurs more than half the time, return it.
        if (leftResult != null) { // If at the left, return the left result.
            return leftResult;
        } else if (rightResult != null) { // If at the right, return the right result.
            return rightResult;
        } else if (count > totalCount / 2) { // In other cases, return the plant.
            return listeFleurs.get(mid);
        } else { // Otherwise, return null.
            return null;
        }
    }
    /**
     * This method counts the number of occurrences of a plant in a sublist.
     * 
     * @param listeFleurs The list of plants.
     * @param fleur The plant to count.
     * @param left The leftmost index of the sublist to count.
     * @param right The rightmost index of the sublist to count.
     * 
     * @requires listeFleurs != null && fleur != null && left >= 0 && right < listeFleurs.size() && left <= right
     * @ensures The method returns the number of occurrences of the plant in the sublist.
     * @assignable None.
     * @throws None.
     * @pure.
     */
    public static int counter(ArrayList<String> listeFleurs, String fleur, int left, int right) {
        // We count the number of occurrences of the plant in the sublist.
        int count = 0;
        // We iterate over the sublist.
        for (int i = left; i <= right; i++) {
            // If the plant is found, we increment the counter. 
            if (listeFleurs.get(i).equals(fleur)) {
                count++;
            }
        }
        return count;
    }
}
