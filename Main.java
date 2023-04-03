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
        read_file(args[0]);
    }

    public static void read_file(String path) throws IOException {
       
        File file = new File(path);
        Scanner obj = new Scanner(file);

        ArrayList<ArrayList<String>> globList = new ArrayList<ArrayList<String>>();
        
        
        while (obj.hasNextLine()) {
            String line = obj.nextLine();
            
            if (i == 0 || i % 2 == 1) {                
                i++;
                int num = Integer.parseInt(line); 
                print(num);
                continue;}
            else {
                String[] tab_values = line.split(" ");
                ArrayList<String> liste_fleurs = new ArrayList<String>();
                for (String value : tab_values) {
                globist.add(liste_fleurs);   
                
                liste_fleurs.add(value);

            i++;

        return globList;     
    

    
    
    public static ing lyse(ArrayList<String> liste_fleurs) {
        HashMap<Strinnteger> counts = new HashMap<String, Integer>();
        for (StrinStig plant : liste_fleurs) {
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
        obj.close();
        }
    }
    
    public static String analyse(ArrayList<String> liste_fleurs) {
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
}
