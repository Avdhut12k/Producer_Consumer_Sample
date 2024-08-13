import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future<List<Integer>> submit = executorService.submit(new RandomNumberGenerator());

        try {
            List<Integer> integers = submit.get();
            for (Integer numbers : integers) {
                System.out.println("numbers = " + numbers);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }




}