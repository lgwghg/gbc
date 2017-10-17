package com.webside.common.sms.mapper;

import org.springframework.stereotype.Repository;

import com.webside.base.basemapper.BaseMapper;
import com.webside.common.sms.model.SmsLog;

@Repository
public interface SmsLogMapper extends BaseMapper<SmsLog, String>{
	
}
