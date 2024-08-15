import java.lang.StringBuilder;

public class Heapsort {
    public static void main(String[] args) {
        int[] arr = {15, 23, 9, 44, 57, 13, 40, 24, 3, 1, 28, 53, 49, 25, 76, 37, 87};
        heapsort(arr);
    }

    private static void heapsort(int[] arr) {
        heapify(arr);

        System.out.println("\n===== Heap completed:");
        System.out.println(getArrayAsString(arr, arr.length));
        System.out.println("\n===== Begin sorting process");

        for (int i = arr.length - 1; i >= 1; i--) {
            System.out.printf("\nSwapping root %d with last element, and detaching %d from the heap%n", arr[0], arr[0]);
            swap(arr, 0, i, arr.length);

            System.out.println("\n\t\t\t" + getArrayAsString(arr, arr.length));
            if (i > 1) {
                System.out.println("\t\t\t[Heap"
                        + String.format("%1$" + ((i - 1) * 5) + "s", "") + "|"
                        + String.format("%1$" + (((arr.length - i) * 5) - 1) + "s", "") + "]");
            } else {
                System.out.println("\t\t\t[Heap|"
                        + String.format("%1$" + (((arr.length - 1) * 5) - 1) + "s", "")
                        + "]");
            }

            System.out.println("Element: " + arr[i] + " is in the correct position.\n");

            siftdown(arr, i, 0);
            System.out.println("Heap restored");
        }
    }

    private static void heapify(int[] arr) {
        System.out.println("Turning array into heap structure using makeheap():");
        for (int i = (int) Math.floor(arr.length / 2) - 1; i >= 0; i--) {
            System.out.println();
            siftdown(arr, arr.length, i);
        }
    }

    private static void siftdown(int[] arr, int last, int idx) {
        System.out.println("Siftdown " + arr[idx] + ":");
        boolean swapped = false;
        int k = idx;
        int j;
        do {
            j = k;
            if (2 * j + 2 < last) {
                System.out.println("\t\tWith parent node " + arr[j] + ", compare values of child nodes "
                        + arr[2 * j + 1] + " and " + arr[2 * j + 2]);
                System.out.println("\t\t" + getArrayAsString(arr, last));
                System.out.println("\t\t" + getHeapChildLocationAsString(j, last));
            } else if (2 * j + 1 < last) {
                System.out.println("\t\tWith parent node " + arr[j] + ", compare values of child nodes "
                        + arr[2 * j + 1]);
                System.out.println("\t\t" + getArrayAsString(arr, last));
                System.out.println("\t\t" + getHeapChildLocationAsString(j, last));
            }

            if (2 * j + 1 < last && arr[2 * j + 1] > arr[k]) {
                k = 2 * j + 1;
            }
            if (2 * j + 2 < last && arr[2 * j + 2] > arr[k]) {
                k = 2 * j + 2;
            }
            if (j != k) {
                System.out.println("\tSince " + arr[k] + " > " + arr[j] + ", swap node " + arr[j]
                        + " with it's greater child node");
                swap(arr, j, k, last);
                swapped = true;
            }
        } while (j != k);

        if (!swapped && 2 * j + 2 < last) {
            System.out.println("\tSince " + arr[j] + " > " + arr[2 * j + 1] + " and " + arr[2 * j + 2]
                    + ", no swap is required");
        } else if (!swapped) {
            System.out.println("\tSince " + arr[j] + " > " + arr[2 * j + 1] + ", no swap is required");
        }
    }

    private static void swap(int[] arr, int i, int j, int last) {
        if (i == j) {
            return;
        }

        System.out.println("\n\t\tArray before swap:    " + getArrayAsString(arr, last));
        String swapMarker;
        {
            boolean startSwap = false;
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < arr.length; k++) {
                if (k == i) {
                    sb.append("    ^");
                    startSwap = true;
                } else if (k == j) {
                    sb.append("----^");
                    startSwap = false;
                } else if (startSwap) {
                    sb.append("-----");
                } else {
                    sb.append("     ");
                }
            }
            swapMarker = sb.toString();
        }
        System.out.println("\t\tSwapping: "
                + String.format("%4s", String.valueOf(arr[i])) + ", "
                + String.format("%4s", String.valueOf(arr[j])) + " |"
                + swapMarker);
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        System.out.println("\t\tArray after swap:     " + getArrayAsString(arr, last));
    }

    private static String getArrayAsString(int[] arr, int last) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < last; i++) {
            sb.append(String.format("%4s", String.valueOf(arr[i])));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    private static String getHeapChildLocationAsString(int idx, int heapSize) {
        int leftChild = 2 * idx + 1;
        int rightChild = 2 * idx + 2;

        StringBuilder sb = new StringBuilder();
        boolean reachedParent = false;
        for (int i = 0; i < heapSize; i++) {
            if (i == idx) {
                sb.append("    |");
                reachedParent = true;
            } else if (i == leftChild) {
                sb.append("----^");
            } else if (i == rightChild) {
                sb.append("----^");
                reachedParent = false;
            } else if (reachedParent) {
                sb.append("-----");
            } else {
                sb.append("     ");
            }
        }
        return sb.toString();
    }
}