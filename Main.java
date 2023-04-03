import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void print(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<String>> flowers = read_file(args[0]);
        for (ArrayList<String> liste_fleurs : flowers) {
            String too_much = Analyse(liste_fleurs);
            print(too_much);
        }
    }

    public static ArrayList<ArrayList<String>> read_file(String path) throws IOException {
       
        File file = new File(path);
        Scanner obj = new Scanner(file);

        ArrayList<ArrayList<String>> globList = new ArrayList<ArrayList<String>>();
        
        int i = 0;
        while (obj.hasNextLine()) {
            String line = obj.nextLine();
            
            if (i == 0 || i % 2 == 1) {                
                i++; 
                continue;}
            else {
                String[] tab_values = line.split(" ");
                ArrayList<String> liste_fleurs = new ArrayList<String>();
                for (String value : tab_values) {
                    liste_fleurs.add(value);  
                }
                globList.add(liste_fleurs);  
            }
            i++;
        }
        obj.close();
        return globList;
    }

    public static String Analyse(ArrayList<String> liste_fleurs) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (String plant : liste_fleurs) {
            counts.put(plant, counts.getOrDefault(plant, 0) + 1);
        }

        String too_much = null;
        for (String plant : counts.keySet()) {
            if (counts.get(plant) > liste_fleurs.size() / 2) {
                too_much = plant;
                break;
            }
        }  
        return too_much;
    }
}
