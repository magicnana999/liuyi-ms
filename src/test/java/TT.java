import com.creolophus.liuyi.common.codec.MD5Util;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2020/8/25 4:32 PM
 */
public class TT {

  @Test
  public void bitMap() {
    // 我们想要存放 0、2、4、5、10、11、12、14、15 这几个数字，应该怎么存储？可以用数组，也可以用 structs.BitMapByteSeries

    int[] array = new int[] {0, 2, 4, 5, 10, 11, 12, 14, 15};
    System.out.println(array.length);

    // 这玩意什么意思              0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15
    byte[] bitmap = new byte[] {1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1};
  }

  @Test
  public void foo() {
    String phoneSign = MD5Util.md5Hex("13718170534");
    String timeSign = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    String sign = MD5Util.md5Hex(phoneSign + timeSign + "rongzi.com_8763!");
    System.out.println(phoneSign);
    System.out.println(timeSign);
    System.out.println(sign);
  }
}
