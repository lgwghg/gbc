package com.webside.user.share.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import com.webside.jc.model.UserJc;
import com.webside.jc.service.IUserJcService;
import com.webside.shiro.cache.redis.VCache;
import com.webside.system.dict.model.Dict;
import com.webside.system.dict.service.DictService;
import com.webside.user.share.entities.UserJcShare;
import com.webside.util.SpringBeanUtil;

/**
 * 红包生成工具
 * 
 * @author tianguifang
 * 
 */
public class RedPurseUtil {

	private static IUserJcService userJcService;
	private static DictService dictService;
	static {
		userJcService = (IUserJcService) SpringBeanUtil.getBean("userJcService");
		dictService = (DictService) SpringBeanUtil.getBean("dictService");
	}
	
	/**
	 * 生成竞猜红包,只在比赛第一下下注时生成红包，有过下注的不再生成红包
	 */
	public static void generateJcRedPurse(String gbId, String userId, String userCode) {
		// 缓存有的，不生成了
		if (isExistRedPurse(gbId, userCode)) {
			return;
		}
		// 竞猜不存在的不分享
		Map<String, Object> param = new HashMap<>();
		param.put("gbId", gbId);
		param.put("userId", userId);
		List<UserJc> jcList = userJcService.findByMap(param);// 根据比赛id，查询竞猜记录
		if (CollectionUtils.isEmpty(jcList) || jcList.size() > 1) {
			return;
		}
		UserJc userJc = jcList.get(0);
		// 取当前该竞猜已经下注的总金额
		int jcGold = ((Long) userJc.getJcGold()).intValue();
		// 红包数
		int number = 10;
		// 红包总额,取竞猜G币的10%
		float total = Math.round(jcGold * 0.1f);
		if (total > 2000f) {
			total = 2000f;
		}
		float money;
		// 最小红包，取红包总额的1%
		int min = 1;
		if (total > 100) {
			min = Math.round(total * 0.01f);
		}
		int max;
		int i = 1;
		List<Integer> math = new ArrayList<Integer>();
		DecimalFormat df = new DecimalFormat("###");
		if (total > 10) {
			while (i < number) {
				// 保证即使一个红包是最大的了,后面剩下的红包,每个红包也不会小于最小值
				max = (int) (total - min * (number - i));
				int k = (int) (number - i) / 2;
				// 保证最后两个人拿的红包不超出剩余红包
				if (number - i <= 2) {
					k = number - i;
				}
				// 最大的红包限定的平均线上下
				max = max / k;
				// 保证每个红包大于最小值,又不会大于最大值
				money = (int) (min * 100 + Math.random() * (max * 100 - min * 100 + 1));
				money = (float) money / 100;
				// 保留两位小数
				money = Float.parseFloat(df.format(money));
				if (money < 1) {
					money = 1;
				}
				total = (int) (total * 100 - money * 100);
				total = total / 100;
				math.add((int) money);
				System.out.println("第" + i + "个人拿到" + (int) money + "剩下" + total);
				i++;
				// 最后一个人拿走剩下的红包
				if (i == number) {
					math.add((int) total);
					System.out.println("第" + i + "个人拿到" + (int) total + "剩下0");
				}
			}
		} else {
			for (int j = 0; j < 10; j++) {
				math.add(1);
			}
		}
		// 取数组中最大的一个值的索引
		System.out.println("本轮发红包中第" + (math.indexOf(Collections.max(math)) + 1) + "个人手气最佳");
		// 将生成好的红包放redis缓存中
		//String jcRedPurseKey = "jc_red_purse" + ":" + jcId + ":" + shareUserId;
		String jcRedPurseKey = "jc_red_purse:" + gbId + "_" + userCode;
		if (CollectionUtils.isNotEmpty(math)) {
			VCache.set(jcRedPurseKey, math, 60 * 60 * 24);// 红包24小时内有效
		}
	}

	/**
	 * 抢红包
	 * 
	 * @param peId
	 * @param jcId
	 * @param userId 分享人的用户id
	 * @param num
	 * @return
	 */
	public static UserJcShare getJcRedPurse(String gbId, String userCode, Integer num) {
		//String jcRedPurseKey = "jc_red_purse" + ":" + jcId + ":" + shareUserId;
		String jcRedPurseKey = "jc_red_purse:" + gbId + "_" + userCode;
		Object redPurseObj = VCache.get(jcRedPurseKey);
		if (redPurseObj != null) {
			List<Integer> redPurseList = (List<Integer>) redPurseObj;
			if (CollectionUtils.isNotEmpty(redPurseList)) {
				Integer redPurse = redPurseList.get(num);
				UserJcShare jcShare = new UserJcShare();
				jcShare.setGold(Long.valueOf(redPurse));
				if (redPurseList.indexOf(Collections.max(redPurseList)) == num) {
					jcShare.setLucky(1);
				} else {
					jcShare.setLucky(0);
				}
				return jcShare;
			}
		}
		return null;
	}

	public static boolean isExistRedPurse(String gbId, String userCode) {
		String jcRedPurseKey = "jc_red_purse:" + gbId + "_" + userCode;
		Object redPurseObj = VCache.get(jcRedPurseKey);
		if (redPurseObj != null) {
			return true;
		}
		return false;
	}
	/**
	 * 随机获取一个红包内容
	 * 
	 * @return
	 */
	public static String getRandomUserDesc() {
		List<Dict> dictList = dictService.getDictType("jc_share");
		if (CollectionUtils.isNotEmpty(dictList)) {
			Random random = new Random();
			int num = (int) (random.nextFloat() * dictList.size());
			if (dictList.get(num) != null) {
				return dictList.get(num).getLabel();
			}
		}
		return null;
	}
}
