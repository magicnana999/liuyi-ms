package org.beetl.sql;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2019/6/26 下午4:54
 */
public class GenPojo {

  @Test
  public void gen() {
    ConnectionSource cs = ConnectionSourceHelper.getSimple(
        "com.mysql.jdbc.Driver",
        "jdbc:mysql://127.0.0.1:3306/liuyi-im?verifyServerCertificate=false&useSSL=false&requireSSL=false",
        "root",
        "root");
    SQLLoader loader = new ClasspathLoader("/sql");
    //SQLManager sqlManager = new SQLManager(new MySqlStyle(),loader,cs,new TUnderlinedNameConversion(), new Interceptor[]{new DebugInterceptor()});
    SQLManager sqlManager = new SQLManager(new MySqlStyle(), loader, cs,
        new UnderlinedNameConversion(), new Interceptor[]{new DebugInterceptor()});
    //sql.genPojoCodeToConsole("userRole"); 快速生成，显示到控制台

    // 或者直接生成java文件
    GenConfig config = new GenConfig("entity.btl");
    config.setBaseClass("AbstractEntity");
    config.setOutputPackage("com.creolophus.liuyi.common.entity");
    config.preferBigDecimal(true);
    try {
      sqlManager.genPojoCode("user", config.getOutputPackage(), config);


    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
