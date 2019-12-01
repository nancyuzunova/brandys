public class Grandma extends Thread{

    @Override
    public void run() {
       while(true){
           try {
               Thread.sleep(3000);
           } catch (InterruptedException e) {
               System.out.println("oops");
           }
           Person.brandyPlace.writeStatistics();
       }
    }
}
