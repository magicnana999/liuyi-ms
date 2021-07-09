package com.creolophus.liuyi.common.json;

import com.creolophus.liuyi.common.api.Api;
import com.creolophus.liuyi.common.api.ApiContext;
import java.lang.reflect.Type;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;

/**
 * @author magicnana
 * @date 2020/12/16 5:39 PM
 */
public class LiuyiMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

  @Override
  public boolean canWrite(Class<?> clazz, MediaType mediaType) {
    String apiScope = ApiContext.getContext().getApiScope();
    if (Api.SCOPE_INTER.equalsIgnoreCase(apiScope)) {
      return false;
    }else{
      return super.canWrite(clazz, mediaType);
    }
  }

  @Override
  public boolean canRead(Type type, @Nullable Class<?> contextClass, @Nullable MediaType mediaType) {
    String apiScope = ApiContext.getContext().getApiScope();
    if (Api.SCOPE_INTER.equalsIgnoreCase(apiScope)) {
      return false;
    } else {
      return super.canRead(type, contextClass, mediaType);
    }
  }
}
