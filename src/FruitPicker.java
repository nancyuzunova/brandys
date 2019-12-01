public class FruitPicker extends Person{

    public FruitPicker(String name, int age, Fruit fruit) {
        super(name, age, fruit);
    }

    @Override
    public void run() {
        while(true){
            if (brandyPlace.isDone()) {
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Someone interrupted my work...");
            }
            Person.brandyPlace.addFruit(this.fruit);
        }
    }
}
