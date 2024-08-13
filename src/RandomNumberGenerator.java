import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomNumberGenerator implements Callable<List<Integer>> {
    private static final List<Integer> numbers = new ArrayList<>();
    private static final int MAX_COUNT = 10000;


    private static AtomicInteger counter = new AtomicInteger(0);

    private static final Random random = new Random();

    @Override
    public List<Integer> call() throws Exception {
        while (counter.incrementAndGet() < MAX_COUNT) {
            System.out.println("counter = " + counter);
            int currentNumber = random.nextInt();
            numbers.add(currentNumber);
        }
        return numbers;
    }
}
