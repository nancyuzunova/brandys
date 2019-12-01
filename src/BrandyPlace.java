import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BrandyPlace {

    private ArrayList<PotStill> potStills;
    private HashMap<Fruit, Integer> brandyCollection;
    private HashMap<Fruit, Integer> harvested;

    public BrandyPlace(){
        this.potStills = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            potStills.add(new PotStill((i+1)));
        }
        this.brandyCollection = new HashMap<>();
        this.harvested = new HashMap<>();
    }

    public synchronized void addFruit(Fruit fruit) {
        PotStill pot  = null;
        do{
            if(isDone()){
                return;
            }
            pot = getPotStillForFilling(fruit);
            if(pot == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Someone interrupted me");
                }
            }
            else{
                pot.fill(fruit);
                notifyAll();
                addToHarvested(fruit);
            }
        }while(pot == null);
    }

    private void addToHarvested(Fruit fruit) {
        if(!this.harvested.containsKey(fruit)){
            this.harvested.put(fruit, 0);
        }
        this.harvested.put(fruit, this.harvested.get(fruit) + 1);
    }

    private PotStill getPotStillForFilling(Fruit fruit) {
        for(PotStill k : this.potStills){
            if(k.isEmpty() || (k.getFruit() == fruit && !k.full())){
                return k;
            }
        }
        return null;
    }

    public synchronized void makeBrandy(Fruit fruit) {
        PotStill pot = null;
        do{
            if(isDone()){
                return;
            }
            pot = getPotStillForEmptying(fruit);
            if(pot == null){
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Someone interrupted me!");
                }
            }
            else{
                pot.empty();
                int litres = new Random().nextInt(3) + 1;
//                System.out.println(litres + " litres");
                addBrandy(fruit, litres);
                notifyAll();
            }
        }while(pot == null);
    }

    private void addBrandy(Fruit fruit, int litres) {
        if(!this.brandyCollection.containsKey(fruit)){
            this.brandyCollection.put(fruit, 0);
        }
        this.brandyCollection.put(fruit, this.brandyCollection.get(fruit) + litres);
        if(isDone()){
            notifyAll();
        }
    }

    private PotStill getPotStillForEmptying(Fruit fruit) {
        for(PotStill pot : this.potStills){
            if(pot.full() && pot.getFruit() == fruit){
                return pot;
            }
        }
        return null;
    }

    public boolean isDone() {
        int totalLitresBrandy = 0;
        for(Integer litre : this.brandyCollection.values()){
            totalLitresBrandy += litre;
        }
        return totalLitresBrandy >= 10;
    }

    public void writeStatistics(){
        int max = 0;
        Fruit fruit = null;
        for(Map.Entry<Fruit, Integer> entry : harvested.entrySet()){
            if(max < entry.getValue()){
                max = entry.getValue();
                fruit = entry.getKey();
            }
        }
        int maxLitres = 0;
        Fruit brandy = null;
        for(Map.Entry<Fruit, Integer> entry : brandyCollection.entrySet()){
            if(maxLitres < entry.getValue()){
                maxLitres = entry.getValue();
                brandy = entry.getKey();
            }
        }
        Integer grapes = brandyCollection.get(Fruit.GRAPES);
        Integer apricot = brandyCollection.get(Fruit.APRICOT);
        String fileName = "statistics_" + LocalDateTime.now() + ".txt";
        fileName = fileName.replace(':', '_');
        File file = new File("C:/Users/user/Desktop/" + fileName);
            try (Writer writer = new FileWriter(file)){
                writer.write("===========STATISTICS============\n");
                writer.write("Most harvested fruit : " + fruit + "\n");
                writer.write(brandy + " - " + maxLitres + " litres\n");
                writer.write("GRAPES " + (grapes == null? 0 : grapes) + " / " + (apricot == null ? 0 : apricot) + " APRICOT\n");
                writer.write("============END OF STATISCTICS============");
            } catch (IOException e) {
                System.out.println("Cannot create file!");
            }
    }
}

