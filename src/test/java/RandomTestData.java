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

    public static int randomRows() {
        return random.nextInt(50) + 1;
    }

    public static int randomFeatures() {
        return random.nextInt(50) + 1; 
    }
}