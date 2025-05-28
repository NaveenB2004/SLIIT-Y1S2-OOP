package pgno130.obms.book;

public final class QuickSort {
    public static void sort(Book[] arr, int low, int high, boolean sortByPrice) {
        if (low < high) {
            int pivot = partition(arr, low, high, sortByPrice);
            sort(arr, low, pivot - 1, sortByPrice);
            sort(arr, pivot + 1, high, sortByPrice);
        }
    }

    public static int partition(Book[] arr, int low, int high, boolean sortByPrice) {
        int pivot = sortByPrice ? arr[high].getPrice() : arr[high].getRating();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if ((sortByPrice ? arr[high].getPrice() : arr[high].getRating()) <= pivot) {
                i++;
                Book temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Book temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
