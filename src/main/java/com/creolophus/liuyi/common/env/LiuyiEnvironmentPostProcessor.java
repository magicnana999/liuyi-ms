package com.creolophus.liuyi.common.env;

import com.creolophus.liuyi.common.util.IPUtil;
import com.creolophus.liuyi.common.web.MdcUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * @author magicnana
 * @date 2019/9/26 下午2:56
 */
public class LiuyiEnvironmentPostProcessor implements EnvironmentPostProcessor {

  private static final String PROPERTY_SOURCE_NAME = "defaultProperties";

  private void addOrReplace(MutablePropertySources propertySources, Map<String, Object> map) {
    MapPropertySource target = null;
    if (propertySources.contains("defaultProperties")) {
      PropertySource<?> source = propertySources.get("defaultProperties");
      if (source instanceof MapPropertySource) {
        target = (MapPropertySource) source;
        Iterator var5 = map.keySet().iterator();

        while (var5.hasNext()) {
          String key = (String) var5.next();
          if (!target.containsProperty(key)) {
            ((Map) target.getSource()).put(key, map.get(key));
          }
        }
      }
    }

    if (target == null) {
      target = new MapPropertySource("defaultProperties", map);
    }

    if (!propertySources.contains("defaultProperties")) {
      propertySources.addLast(target);
    }
  }

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {
    Map<String, Object> map = new HashMap();
    map.put(MdcUtil.MDC_IP, IPUtil.getLocalIP());
    this.addOrReplace(environment.getPropertySources(), map);
  }
}
