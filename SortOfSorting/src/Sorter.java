import java.util.Arrays;

class Sorter {

    public static void sortStrings(String[] arr) {
        // TODO: implement your sorting function here
        Arrays.sort(arr, (str1, str2) -> {
            String s1 = str1.length() < 2 ? str1 : str1.substring(0, 2);
            String s2 = str2.length() < 2 ? str2 : str2.substring(0, 2);
            return s1.compareTo(s2);
        });
    }
}
