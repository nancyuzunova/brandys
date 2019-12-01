public class BrandyMan extends Person {

    public BrandyMan(String name, int age, Fruit fruit) {
        super(name, age, fruit);
    }

    @Override
    public void run() {
        while(true){
            if (brandyPlace.isDone()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Someone interrupted me!");
            }
            Person.brandyPlace.makeBrandy(this.fruit);
        }
    }
}
