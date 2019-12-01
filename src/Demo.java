import java.util.Random;

public class Demo {

    public static void main(String[] args) {
        for (int i = 0; i < 7; i++) {
            FruitPicker picker = new FruitPicker("Picker " + (i+1), 32,
                    Fruit.values()[new Random().nextInt(Fruit.values().length)]);
            picker.start();
        }
        BrandyMan man1 = new BrandyMan("Mr. Smith", 43, Fruit.GRAPES);
        BrandyMan man2 = new BrandyMan("Mr. Johnson", 48, Fruit.PLUM);
        BrandyMan man3 = new BrandyMan("Mr. Bean", 52, Fruit.APRICOT);
        man1.start();
        man2.start();
        man3.start();
        Grandma granny = new Grandma();
        granny.setDaemon(true);
        granny.start();
    }
}
