public class PotStill {

    private static final int MAX_CAPACITY = 10;

    private int number;
    private int kg = 0;
    private Fruit fruit = null;

    public PotStill(int number){
        this.number = number;
    }

    public boolean isEmpty() {
        return this.kg == 0;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public boolean full() {
        return this.kg == MAX_CAPACITY;
    }

    public void fill(Fruit fruit) {
        this.fruit = fruit;
        this.kg++;
        System.out.println(Thread.currentThread().getName() + " added 1 kg of " + this.fruit + " in pot-still " + this.number);
    }

    public void empty() {
        System.out.println(Thread.currentThread().getName() + " made brandy of " + this.fruit + " from pot-still " + this.number);
        this.kg = 0;
        this.fruit = null;
    }
}
