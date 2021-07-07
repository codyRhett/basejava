import java.util.Arrays;

public class MainTask12 {
    private static int minValue(int[] values) {
        int[] valuesUnique = Arrays.stream(values).distinct().sorted().toArray();
        int coeff = 10;
        float res = 0;
        for (int val : valuesUnique) {
            res += (float) val/ (float) coeff;
            coeff *= 10;
        }
        return (int) (res * coeff / 10);
    }

    public static void main(String[] args) {
        //int[] values = {2,7,5,5,6,2,6,1,2,8,1};
        //int[] values = {9,8};
        int[] values = {1,2,3,3,2,3};

        System.out.println(minValue(values));
    }
}
