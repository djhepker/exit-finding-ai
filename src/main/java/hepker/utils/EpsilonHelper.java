package hepker.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class EpsilonHelper {
    private static final String FILE_PATH = "src/main/resources/data/epsilon.txt";

    private EpsilonHelper() {

    }

    public static void saveEpsilon(double value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(String.valueOf(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double loadEpsilon() {
        try {
            String line = Files.readString(Paths.get(FILE_PATH)).trim();
            return Double.parseDouble(line);
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException();
        }
    }

    public static double subtractClean(double epsilon, double amount) {
        return BigDecimal.valueOf(epsilon)
                .subtract(BigDecimal.valueOf(amount))
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
