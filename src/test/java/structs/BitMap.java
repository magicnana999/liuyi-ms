package structs;

/**
 * 数据源从 0~max BitMap的 byte[]实现.
 *
 * @author magicnana
 * @date 2021/6/17 18:26
 */
public class BitMap {

  private int bitmap[];
  private int max;
  private int min = 0;

  public BitMap(int max) {
    this.max = max;
    this.bitmap = new int[(max >>> 5) + 1];
  }

  /**
   * 在bitmap中加入元素n
   *
   * @param n 范围为[0,MAX_VALUE]
   */
  public void addValue(int n) {
    if (n < 0 || n > max) {
      throw new RuntimeException("不再0到" + max + "的范围内，不能加入");
    }
    // n对应数组的哪个元素，是n/32
    int row = n >> 5;
    // n对应的int中的位置，是n mod 32
    int index = n & 0x1F;
    // 在n对应的int，对应的位置，置1
    bitmap[row] |= 1 << index;
  }

  /** 展示第row行的情况，元素的二进制情况，和有的元素 */
  public void displayRow(int row) {
    System.out.print("bitmap展示第" + row + "行:" + Integer.toBinaryString(bitmap[row]) + " 有：");
    // 对应row：32*row到32*row+31
    int now = row << 5;
    // temp为与对应位进行与运算的数字
    int temp = 1;
    for (int i = 0; i < 32; i++) {
      int result = bitmap[row] & temp;
      if (result != 0) {
        System.out.print("  " + now + "  ");
      }
      now++;
      temp = temp << 1;
    }
    System.out.println();
  }

  /**
   * 查找bitmap中是否有元素n
   *
   * @return 如果存在，返回true 不存在，返回false
   */
  public boolean existValue(int n) {
    if (n < 0 || n > max) {
      System.out.println("不再0到" + max + "的范围内，一定没有");
      return false;
    }
    // n对应数组的哪个元素，是n/32
    int row = n >> 5;
    // n对应的int中的位置，是n mod 32
    int index = n & 0x1F;
    // result为哪个位置上现在保存的值（为10000(index个0)或者0）
    int result = bitmap[row] & (1 << index);
    // 如果不为0，则那个位置一定为1
    return result != 0;
  }

  /** 在bitmap中删除元素n */
  public void removeValue(int n) {
    if (n < 0 || n > max) {
      System.out.println("不再0到" + max + "的范围内，一定没有");
      return;
    }
    // n对应数组的哪个元素，是n/32
    int row = n >> 5;
    // n对应的int中的位置，是n mod 32
    int index = n & 0x1F;
    // 对应位置0，与 111101111进行与运算，那位一定变0
    bitmap[row] &= ~(1 << index);
  }
}
