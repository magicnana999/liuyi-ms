package com.creolophus.liuyi.common.base;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author magicnana
 * @date 2019/5/20 下午2:31
 */
@Transactional(rollbackFor = Exception.class)
public class AbstractService {

}
