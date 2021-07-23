package com.creolophus.liuyi.common.beetlsql;

import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.annotatoin.Table;
import org.beetl.sql.core.kit.StringKit;

/**
 * @author magicnana
 * @date 2019/6/12 下午1:54
 */
public class TUnderlinedNameConversion extends UnderlinedNameConversion {

  public static void main(String[] ss) {
    String className = "User";
    System.out.println("t_" + StringKit.enCodeUnderlined(className));

    String tableName = "t_user";
    if (tableName.startsWith("t_")) {
      tableName = tableName.substring(1);
    }
    System.out.println(StringKit.deCodeUnderlined(tableName.toLowerCase()));
  }

  @Override
  public String getTableName(Class<?> c) {
    Table table = c.getAnnotation(Table.class);
    return table != null ? table.name() : "t_" + StringKit.enCodeUnderlined(c.getSimpleName());
  }

  @Override
  public String getClassName(String tableName) {
    if (tableName.startsWith("t_")) {
      tableName = tableName.substring(1);
    }
    String temp = StringKit.deCodeUnderlined(tableName.toLowerCase());
    return StringKit.toUpperCaseFirstOne(temp);
  }
}
