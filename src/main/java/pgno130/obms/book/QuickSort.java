package pgno130.obms.book;

public final class QuickSort {
    public static void sort(Book[] arr, int low, int high, boolean sortByPrice) {
        if (low < high) {
            int pivot = partition(arr, low, high, sortByPrice);
            sort(arr, low, pivot - 1, sortByPrice);
            sort(arr, pivot + 1, high, sortByPrice);
        }
    }

    private static int partition(Book[] arr, int low, int high, boolean sortByPrice) {
        Book pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            boolean condition = sortByPrice
                    ? arr[j].getPrice() <= pivot.getPrice()
                    : arr[j].getRating() <= pivot.getRating();

            if (condition) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(Book[] arr, int i, int j) {
        Book temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

