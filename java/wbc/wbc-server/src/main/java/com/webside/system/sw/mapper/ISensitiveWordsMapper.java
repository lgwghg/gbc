/*******************************************************************************
 * All rights reserved. 
 * 
 * @author zengxn
 *
 * Contributors:
 *     Info Corporation - POC Team
 *******************************************************************************/
package com.webside.system.sw.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.system.sw.entities.SensitiveWords;

/**
 * 敏感词数据访问接口
 *
 * @author zengxn
 * @date 2016-11-24 16:21:45
 */
@Repository
public interface ISensitiveWordsMapper extends BaseMapper<SensitiveWords, String> 
{
	
}
