package com.creolophus.liuyi.common.json;

import com.creolophus.liuyi.common.api.Api;
import com.creolophus.liuyi.common.api.ApiContext;
import java.lang.reflect.Type;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * @author magicnana
 * @date 2020/12/16 5:43 PM
 */
public class LiuyiGsonHttpMessageConverter extends GsonHttpMessageConverter {

  @Override
  public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
    String apiScope = ApiContext.getContext().getApiScope();
    if(Api.SCOPE_INTER.equalsIgnoreCase(apiScope)){
      return super.canWrite(type, clazz, mediaType);
    }else{
      return false;
    }
  }

  @Override
  public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
    String apiScope = ApiContext.getContext().getApiScope();
    if(Api.SCOPE_INTER.equalsIgnoreCase(apiScope)){
      return super.canRead(type, contextClass, mediaType);
    } else {
      return false;
    }
  }
}
