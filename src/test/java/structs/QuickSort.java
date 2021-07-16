package structs;

/**
 * @author magicnana
 * @date 2021/6/17 11:20
 */
public class QuickSort {

  public static void main(String[] args) {
    int[] arr = new int[]{5, 1, 7, 6, 2};

    quickSort(arr, 0, arr.length - 1);
    for (int i : arr) {
      System.out.println(i);
    }
  }

  //从小到大,如果从大到小,下边的>= <= 改成相反的即可.
  public static void quickSort(int[] arr, int leftIndex, int rightIndex) {
    if (rightIndex < leftIndex) {
      return;
    }

    int left = leftIndex;
    int right = rightIndex;
    int key = arr[left];

    while (left < right) {
      while (right > left && arr[right] >= key) {  //右边比 key 大的跳过,找到小的放左边
        right--;
      }
      arr[left] = arr[right];

      while (left < right && arr[left] <= key) {   //左边比 key 小的跳过,找到大的放右边.
        left++;
      }
      arr[right] = arr[left];

      arr[left] = key;
      quickSort(arr, leftIndex, left - 1);
      quickSort(arr, right + 1, rightIndex);

    }
  }
}
