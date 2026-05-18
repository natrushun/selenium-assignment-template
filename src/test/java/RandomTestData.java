import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;

public class RandomTestData {

    private static final Random random = new Random();

    public static String randomDatasetName() {
        return "Dataset_" + RandomStringUtils.randomAlphanumeric(8);
    }

    public static String randomAbstract() {
        return RandomStringUtils.randomAlphabetic(50);
    }

    public static int randomNumber(int max) {
        return random.nextInt(max) + 1;
    }

}