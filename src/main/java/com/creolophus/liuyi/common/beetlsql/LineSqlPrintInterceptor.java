package com.creolophus.liuyi.common.beetlsql;

import com.alibaba.fastjson.JSON;
import org.beetl.sql.core.InterceptorContext;
import org.beetl.sql.ext.DebugInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author magicnana
 * @date 17/6/9 上午9:54
 */
public class LineSqlPrintInterceptor extends DebugInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(LineSqlPrintInterceptor.class);

    public class BeetlSqlLogger{
        private String formatSqlLine = "";
        private String sqlIdLine = "";
        private String parametersLine = "";
        private String position = "";
        private String timeLine = "";
        private String resultLine = "";
        private String exception = "";
    }

    @Override
    public void before(InterceptorContext ctx) {
        String sqlId = ctx.getSqlId();
        if (this.isDebugEanble(sqlId)) {
            ctx.put("debug.time", System.currentTimeMillis());
        }
        if (this.isSimple(sqlId)) {
            return;
        }

        String formatSqlLine = formatSql(ctx.getSql());
        String sqlIdLine = getSqlId(formatSql(sqlId));
        String parametersLine = JSON.toJSONString(formatParas(ctx.getParas()));

//        RuntimeException ex = new RuntimeException();
//        StackTraceElement[] traces = ex.getStackTrace();
//        int index = lookBusinessCodeInTrace(traces);
//        StackTraceElement businessCode = traces[index];
//        String className = businessCode.getClassName();
//        String methodName = businessCode.getMethodName();
//        int line = businessCode.getLineNumber();
//        String position = className + "." + methodName + "(" + businessCode.getFileName() + ":" + line + ")";

//        logger.info("SQL {}",sqlIdLine);
//        logger.info("SQL {}",formatSqlLine);
//        logger.info("SQL {}",parametersLine);
//        logger.info("SQL {}",position);

        BeetlSqlLogger log = new BeetlSqlLogger();
        log.formatSqlLine = formatSqlLine;
        log.sqlIdLine = sqlIdLine;
        log.parametersLine = parametersLine;
//        log.position = position;

        ctx.put("logs", log);
    }

    @Override
    public void after(InterceptorContext ctx) {
        String sqlId = ctx.getSqlId();
        if (this.isSimple(sqlId)) {
            this.simpleOut(ctx);
            return;
        }
        long time = System.currentTimeMillis();
        long start = (Long) ctx.get("debug.time");
        BeetlSqlLogger log = (BeetlSqlLogger) ctx.get("logs");

        String timeLine= (time - start)+"";
//        String resultLine = JSON.toJSONString(ctx.getResult(),valueFilter==null?new SerializeFilter[0]:new SerializeFilter[]{valueFilter});
        String resultLine = ctx.getResult()==null?"":ctx.getResult().toString();

        log.timeLine = timeLine;
        log.resultLine = resultLine;

//        logger.info("SQL {}",timeLine);
//        logger.info("SQL {}",resultLine);

        println(log);
    }

    @Override
    public void exception(InterceptorContext ctx, Exception ex) {
        String sqlId = ctx.getSqlId();
        if(this.isSimple(sqlId)) {
            this.simpleOutException(ctx, ex);
            return;
        }

        BeetlSqlLogger log = (BeetlSqlLogger) ctx.get("logs");

        String lineSeparator = System.getProperty("line.separator", "\n");
        log.exception = ex != null ? ex.getMessage().replace(lineSeparator, "") : "";
        println(log);
    }

    private void println(BeetlSqlLogger log) {

        if(logger.isDebugEnabled()){
            logger.debug("SQL-ID {}",log.sqlIdLine);
            logger.debug("SQL-SQL {}",log.formatSqlLine);
            logger.debug("SQL-PARAM {}",log.parametersLine);
//        logger.debug("SQL-POST {}",log.position);
            logger.debug("SQL-TIME {}",log.timeLine);
            logger.debug("SQL-RESULT {}",log.resultLine);
            logger.debug("SQL-ERROR {}",log.exception);
        }
    }
}
