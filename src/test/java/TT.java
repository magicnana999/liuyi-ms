import com.creolophus.liuyi.common.codec.MD5Util;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author magicnana
 * @date 2020/8/25 4:32 PM
 */
public class TT {
    @Test
    public void foo(){
        String phoneSign = MD5Util.md5Hex("13718170534" );
        String timeSign = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String sign = MD5Util.md5Hex(phoneSign+timeSign+"rongzi.com_8763!");
        System.out.println(phoneSign);
        System.out.println(timeSign);
        System.out.println(sign);
    }
}
