

class ArraySorting {
    /**
     * @param array unordered sequence of strings
     * @return ordered array of strings
     */

    public static String[] sortArray(String[] array) {
        // Volání metody sort z třídy Arrays na základě plného názvu balíčku
        java.util.Arrays.sort(array);
        // Vrácení zorazeného pole
        return array;
    }

    public static void main(String[] args) throws Exception {
        // Čeká na vstup od uživatele jako řetězec
        //System.out.print("Zadejte prvky pole oddělené mezerami: ");

        // Přečte vstup od uživatele
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String input = reader.readLine();

        // Rozdělí vstupní řetězec na pole podle mezer
        String[] inputArray = input.split(" ");

        // Zavolá sortArray metodu pro řazení pole
        String[] sortedArray = sortArray(inputArray);

        // Vytiskne seřazené pole oddělené mezerami
        //System.out.println("Seřazené pole:");
        for (String element : sortedArray) {
            System.out.print(element + " ");
        }
    }
}