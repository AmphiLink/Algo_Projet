import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

//Spécifie chaque procédure en JML
public class Main {

    public static void print(Object o) {
        // @ requires o != null;
        // @ ensures true;
        // @ assignable System.out;

        System.out.println(o);
    }

    public static void main(String[] args) throws IOException {
        // @ requires args.length == 1;
        // @ ensures true;
        // @ assignable System.out;
        // @ throws IOException;

        HashMap<String, Integer> counts;
        ArrayList<ArrayList<String>> flowers = readFile(args[0]);

        print("=== Méthode Naïve ===");
        for (ArrayList<String> listFlowers : flowers) {
            counts = new HashMap<>();
            print(naive(listFlowers, counts));
        }

        print("\n=== Méthode Récursive ===");
        for (ArrayList<String> listFlowers : flowers) {
            counts = new HashMap<>();
            print(recursive(listFlowers, counts, 0, null));
        }
        
        print("\n=== Méthode Diviser Pour Régner ===");
        for (ArrayList<String> listeFleurs : flowers) {
            String tooMuch = diviserPourRegner(listeFleurs, 0, listeFleurs.size() - 1);
            print(tooMuch);
        }

    }

    public static ArrayList<ArrayList<String>> readFile(String path) throws IOException {
        // @ requires path != null;
        // @ ensures \result != null;
        // @ assignable System.out;
        // @ throws IOException;
        // @ pure;

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

    public static String naive(ArrayList<String> listFlowers, HashMap<String, Integer> counts) {
        // @ requires listFlowers != null;
        // @ ensures \result != null;
        // @ assignable System.out;
        // @ pure;
        
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

    //Recursive method
    public static String recursive(ArrayList<String> listFlowers, HashMap<String, Integer> counts, int currentPlant,
            String invasivePlant) {
        // @ requires listFlowers != null;
        // @ requires counts != null;
        // @ requires currentPlant >= 0;
        // @ ensures \result != null;
        // @ assignable System.out;
        // @ pure;
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
    
    public static String diviserPourRegner(ArrayList<String> listeFleurs, int left, int right) {
        if (left == right) {
            return null;
        }
        int mid = (left + right) / 2;
        String leftResult = diviserPourRegner(listeFleurs, left, mid);
        String rightResult = diviserPourRegner(listeFleurs, mid + 1, right);
        int count = counter(listeFleurs, listeFleurs.get(mid), left, right);
        int totalCount = right - left + 1;
        if (leftResult != null) {
            return leftResult;
        } else if (rightResult != null) {
            return rightResult;
        } else if (count > totalCount / 2) {
            return listeFleurs.get(mid);
        } else {
            return null;
        }
    }
    

    public static int counter(ArrayList<String> listeFleurs, String fleur, int left, int right) {
        int count = 0;
        for (int i = left; i <= right; i++) {
            if (listeFleurs.get(i).equals(fleur)) {
                count++;
            }
        }
        return count;
    }
}
