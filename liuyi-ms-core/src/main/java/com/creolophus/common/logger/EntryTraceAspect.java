package com.creolophus.common.logger;

import brave.Span;
import brave.Tracer;
import com.creolophus.common.context.ApiContext;
import com.creolophus.common.web.MdcUtil;
import java.util.regex.Pattern;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.util.SpanNameUtil;

/**
 * 朝辞白帝彩云间 千行代码一日还 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/9/18 下午2:05
 */
@Aspect
public class EntryTraceAspect {

  protected static final String CLASS_KEY = "class";
  protected static final String METHOD_KEY = "method";

  protected Logger logger = LoggerFactory.getLogger(EntryTraceAspect.class);

  protected Tracer tracer;
  protected Pattern skipPattern;

  public EntryTraceAspect(Tracer tracer, Pattern skipPattern) {
    this.tracer = tracer;
    this.skipPattern = skipPattern;
  }

  protected Span startOrContinueRenamedSpan(String spanName) {
    Span currentSpan = this.tracer.currentSpan();
    if (currentSpan != null) {
      return currentSpan.name(spanName);
    }
    return this.tracer.nextSpan().name(spanName);
  }

  @Around("@annotation(com.creolophus.common.logger.Entry)")
  public Object traceBackgroundThread(final ProceedingJoinPoint pjp) throws Throwable {

    if (this.skipPattern.matcher(pjp.getTarget().getClass().getName()).matches()) {
      return pjp.proceed();
    }
    String spanName = SpanNameUtil.toLowerHyphen(pjp.getSignature().getName());
    Span span = startOrContinueRenamedSpan(spanName);
    try (Tracer.SpanInScope ws = this.tracer.withSpanInScope(span.start())) {
      span.tag(CLASS_KEY, pjp.getTarget().getClass().getSimpleName());
      span.tag(METHOD_KEY, pjp.getSignature().getName());

      MdcUtil.init();
      Object obj = pjp.proceed();
      MdcUtil.clear();
      ApiContext.release();
      return obj;
    } finally {
      span.finish();
    }
  }
}
