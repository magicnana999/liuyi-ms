package structs;

/**
 * @author magicnana
 * @date 2021/6/17 11:20
 */
public class BinarySort {

  public static void main(String[] args){
    int[] arr = new int[]{5,1,7,6,2};

    int[] ret = binaryInsertSort(arr);
    for(int i:ret){
      System.out.println(i);
    }
  }


  public static int[] binaryInsertSort(int[] array){
    for(int i = 0;i<array.length;i++){
      int temp = array[i];//待插入到前面有序序列的值
      int left = 0;//有序序列的左侧
      int right = i-1;//有序序列的右侧
      int middle = 0;//有序序列的中间
      while(left <= right){
        middle = (left + right)/2;//赋值
        if(temp<array[middle]){
          right = middle-1;
        }else{
          left = middle + 1;
        }
      }
      for(int j = i-1;j>=left;j--){
        //从i-1到left依次向后移动一位,等待temp值插入
        array[j+1] = array[j];
      }
      if(left != i ){
        array[left] = temp;
      }
    }
    return array;
  }

}
