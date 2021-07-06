import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainTask12 {
    private static int minValue(int[] values) {
        int[] valuesUnique = Arrays.stream(values).distinct().sorted().toArray();

        int size = valuesUnique.length;
        int temp = 1;
        double res = 0;
        for (int i = 0; i < size; i++) {
            temp = temp * 10;
            res += (double)valuesUnique[i] / (double)temp;
        }

        int result = Integer.valueOf((int) (res*temp));
        return 1;
    }

    public static void main(String[] args) {
        int[] values = {2,7,5,5,6,2,6,1,2,8,1};

        minValue(values);
    }
}
