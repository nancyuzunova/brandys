public class Person extends Thread{

    public static BrandyPlace brandyPlace = new BrandyPlace();

    private String name;
    private int age;
    protected Fruit fruit;

    public Person(String name, int age, Fruit fruit){
        super(name);
        this.age = age;
        this.fruit = fruit;
    }
}
