import java.util.Arrays;

public class Quicksort {
    public static void main(String[] args) {
        int[] arr = {12, 86, 25, 86, 23, 3, 78, 18, 37, 57, 5, 96, 39, 67, 25, 79, 8, 42, 59, 22, 89, 24};
        quicksort(arr);
    }

    private static void quicksort(int[] arr) {
        boolean[] sorted = new boolean[arr.length];
        int[] step = {0};
        quicksort(arr, 0, arr.length - 1, step, sorted);
        System.out.println("\nArray after Iteration " + arr.length + " is " + getArrayAsString(arr));
        System.out.println("Sorted elements are        " + getSortedElementsListAsString(sorted));
    }

    private static void quicksort(int[] arr, int first, int last, int[] step, boolean[] sortedElem) {
        if (step[0] == 0) {
            System.out.println("Starting sequence is " + getArrayAsString(arr) + "\n");
            System.out.println("===== Iteration " + (step[0] + 1) + " =====");
        } else {
            System.out.printf("\nArray after Iteration %2d is %s%n", step[0], getArrayAsString(arr));
            System.out.println("Sorted elements are         " + getSortedElementsListAsString(sortedElem));
            System.out.println("\n===== Iteration " + (step[0] + 1) + " =====");
        }

        step[0]++;

        System.out.println("Performing quicksort on the following subarray:");
        System.out.println(getSubarrayAsString(arr, first, last) + "\n");

        if (first == last) {
            sortedElem[first] = true;
            System.out.println("    As subsequence has 1 element, element: "
                    + arr[first] + " is already sorted");
            return;
        }

        int idx = partition(arr, first, last);
        sortedElem[idx] = true;

        // Quicksort left part
        if (idx > first) { // Left subsequence have > 1 element
            quicksort(arr, first, idx - 1, step, sortedElem);
        }

        // Quicksort right part
        if (idx < last) { // Right subsequence have > 1 element
            quicksort(arr, idx + 1, last, step, sortedElem);
        }
    }

    private static int partition(int[] arr, int first, int last) {
        selectAndSwapPivot(arr, first, last);

        int pivot = arr[first];
        int head = first + 1;
        int tail = last;
        boolean met = false;

        System.out.println("\n    Partitioning using pivot: " + pivot
                + " | head: " + arr[head]
                + " | tail: " + arr[tail]);
        System.out.println("    " + getSubarrayAsString(arr, first, last));
        System.out.println("    " + getPivotHeadTailMarkerAsString(first, head, tail));

        while (!met) {
            while ((pivot > arr[head]) && (head < last)) {
                head++;
            }
            while ((pivot <= arr[tail]) && (tail > first)) {
                tail--;
            }
            if (head >= tail) {
                if (first == tail) {
                    System.out.println("\n    Pivot: " + pivot + " is already in the correct sorted position");

                } else {
                    System.out.println("\n    Moving pivot: " + pivot + " to the final sorted position");
                    System.out.print(
                            "                              " + getPtrLocationAsString(arr.length, head, tail));
                }
                swap(arr, first, tail, first, last);
                met = true;
            } else {
                System.out
                        .print("                              " + getPtrLocationAsString(arr.length, head, tail));
                swap(arr, head, tail, first, last);
                head++;
                tail--;
            }
        }
        return tail;
    }

    private static void selectAndSwapPivot(int[] arr, int first, int last) {
        int mid = (int) Math.floor((first + last) / 2);

        System.out.println("    Looking at pivots P1, P2, P3 and swapping to correct position");
        System.out.println("    " + getSubarrayAsString(arr, first, last));
        System.out.println("    " + getPivotLocationMarkerAsString(first, mid, last));
        int[] pivotCandidates = {arr[first], arr[mid], arr[last]};
        Arrays.sort(pivotCandidates);
        System.out.printf("    Median of %d, %d, %d is %d. ", arr[first], arr[mid], arr[last], pivotCandidates[1]);
        System.out.printf("Selecting %d as the pivot", pivotCandidates[1]);

        int firstVal = arr[first];
        int midVal = arr[mid];
        int lastVal = arr[last];

        int medianVal;
        if ((firstVal <= midVal && midVal <= lastVal) || (lastVal <= midVal && midVal <= firstVal)) {
            medianVal = midVal;
        } else if ((midVal <= firstVal && firstVal <= lastVal) || (lastVal <= firstVal && firstVal <= midVal)) {
            medianVal = firstVal;
        } else {
            medianVal = lastVal;
        }

        if (medianVal == firstVal) {
            System.out.println("\n    Pivot is already in the correct position.");
        } else if (medianVal == midVal) {
            swap(arr, first, mid, first, last);
        } else {
            swap(arr, first, last, first, last);
        }
    }

    private static void swap(int[] arr, int i, int j, int first, int last) {
        if (i == j) {
            return;
        }

        System.out.println("\n        Array before swap:    " + getSubarrayAsString(arr, first, last));
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
        System.out.println("        Swapping: "
                + String.format("%4s", String.valueOf(arr[i])) + ", "
                + String.format("%4s", String.valueOf(arr[j])) + " |"
                + swapMarker);
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        System.out.println("        Array after swap:     " + getSubarrayAsString(arr, first, last));
    }

    private static String getPtrLocationAsString(int length, int head, int tail) {
        char[] ch = new char[(length + 1) * 5];
        for (int i = 0; i < ch.length; i++) {
            ch[i] = ' ';
        }
        StringBuilder sb = new StringBuilder(new String(ch));
        sb.replace(head * 5 - 2, head * 5, "LG|");
        sb.replace((tail + 1) * 5, (tail + 1) * 5 + 2, "|RG");
        return sb.toString();
    }

    private static String getArrayAsString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < arr.length; i++) {
            sb.append(String.format("%4s", String.valueOf(arr[i])));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    private static String getSortedElementsListAsString(boolean[] sorted) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i]) {
                sb.append("____");
            } else {
                sb.append("    ");
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    private static String getSubarrayAsString(int[] arr, int first, int last) {
        StringBuilder sb = new StringBuilder(getArrayAsString(arr));
        for (int i = 0; i < first * 5; i++) {
            sb.setCharAt(i, ' ');
        }
        for (int i = (last + 1) * 5; i < (arr.length) * 5 + 1; i++) {
            sb.setCharAt(i, ' ');
        }
        sb.setCharAt(first * 5, '[');
        sb.setCharAt((last + 1) * 5, ']');
        return sb.toString();
    }

    private static String getPivotLocationMarkerAsString(int first, int mid, int last) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i <= last; i++) {

            if (i == first) {
                if (first != mid) {
                    sb.append(" P1^");
                } else {
                    sb.append("P12^");
                }
            } else if (i == mid) {
                if (mid != last) {
                    sb.append(" P2^");
                } else {
                    sb.append("P23^");
                }
            } else if (i == last) {
                sb.append(" P3^");
            } else {
                sb.append("    ");
            }
            sb.append(" ");
        }
        sb.setCharAt(first * 5, '[');
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    private static String getPivotHeadTailMarkerAsString(int pivot, int head, int tail) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i <= tail; i++) {
            if (i == pivot) {
                sb.append("  P^");
            } else if (i == head) {
                if (head != tail) {
                    sb.append("  H^");
                } else {
                    sb.append(" HT^");
                }
            } else if (i == tail) {
                sb.append("  T^");
            } else {
                sb.append("    ");
            }
            sb.append(" ");
        }
        sb.setCharAt(pivot * 5, '[');
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }
}
