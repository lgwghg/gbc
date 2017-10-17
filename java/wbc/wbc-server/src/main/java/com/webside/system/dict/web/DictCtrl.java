package com.webside.system.dict.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webside.base.basecontroller.BaseController;
import com.webside.system.dict.model.Dict;
import com.webside.system.dict.service.DictService;

@Controller
@RequestMapping("/dictCtrl/")
public class DictCtrl extends BaseController {
 
	@Autowired
	private DictService dictService;
	
	@RequestMapping(value="getDictType.json", method = RequestMethod.POST)
	@ResponseBody
	public Object getDict_type(@RequestParam(required=true) String type){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try{
			List<Dict> list = dictService.getDictType(type);
			jsonMap.put("isSuccess", Boolean.TRUE);
			jsonMap.put("list", list);
		}catch(Exception e){
			logger.error("获取字典集合出错：", e);
			jsonMap.put("isSuccess", Boolean.FALSE);
		}
		return jsonMap;
	}
}
