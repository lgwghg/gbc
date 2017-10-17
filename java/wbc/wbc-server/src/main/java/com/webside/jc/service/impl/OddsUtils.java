package com.webside.jc.service.impl;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @title : 比赛赔率算法
 * */
public class OddsUtils {
		//抽水阶梯
		//金额在1万以内的抽5 %  1万-5万的抽10%  5万 -10万 抽15%   10万 网上的抽20%
		private static final double highestPump = 0.2;	
		private static final double highPump = 0.15;
		private static final double middlePump = 0.1;
		private static final double lowPump = 0.05;
		private static final double NonePump = 0;
		
		//最大赔率
		private static double betMaxOdds = 30;
		
		/**
		 * @title : 计算比赛赔率
		 * @param double betOddsa 主战队赔率
		 * @param double betOddsb 客场战队赔率
		 * @param int beta 主场战队下注人数
		 * @param int betb 客场战队下注人数
		 * @param double betMoneya 主场战队下注金额
		 * @param double betMoneyb 客场战队下注金额
		 * @param String betRatioa 主场战队下注比例
		 * @param String betRatiob 客场战队下注比例
		 * */
		public static Map<String,Object> getJcOdds(double betOddsa,double betOddsb,double beta,double betb,double betMoneya,double betMoneyb){
			String betRatioa = "";
			String betRatiob = "";
			//总下注金额
			double betMoney = 0;
			//当前使用抽水的阶梯额
			double pump = NonePump;
			
			//抽水金额
			double pumpMoney = 0;
			//A获胜抽水额度
			double pumpMoneya = 0;
			//B获胜抽水额度
			double pumpMoneyb = 0;
			
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			
			NumberFormat ptFormat = NumberFormat.getPercentInstance();
			NumberFormat odFormat = NumberFormat.getInstance(); 
			odFormat.setMaximumFractionDigits(2);
			
			//总下注金额
			//betMoney = betMoneya + betMoneyb + money;
			betMoney = betMoneya + betMoneyb;
			
			//抽水阶梯
			if(100000 <= betMoney){
				pump = highestPump;
			}else if(50000 <= betMoney && betMoney < 100000){
				pump = highPump;;
			}else if(10000 <= betMoney && betMoney <50000){
				pump = middlePump;
			}else{
				pump = lowPump;
			}
			
			//抽水额度
			pumpMoneyb = pumpMoneya = pumpMoney = betMoney * pump;
			
			//用户选择下注战队
//			if(option.equals(GlobalConstant.JC_TEAM_TYPE_1)){
//				//主场战队
//				beta += 1;
//				betMoneya += money;
//			}else{
//				//客场战队
//				betb += 1;
//				betMoneyb += money;
//			}
			//主场战队下注比例
			betRatioa = ptFormat.format(beta/(beta + betb));
			//客场战队下注比例
			betRatiob = ptFormat.format(betb/(beta + betb));
			
			//主战队赔率
			betOddsa = (betMoneyb - pumpMoneya)/betMoneya+1;
			//客场战队赔率
			betOddsb = (betMoneya - pumpMoneyb)/betMoneyb+1;
			
			//主场战队下注金额 等于 0 或者 小于 抽水额度
			if(betMoneya == 0 || betMoneya <= pumpMoney){
				betOddsb = betMoneya/betMoneyb+1;
				pumpMoneyb = 0;
			}
			
			if(betMoneyb == 0 || betMoneyb <= pumpMoney){
				betOddsb = betMoneya/betMoneyb+1;
				pumpMoneya = 0;
			}
			
			if(betMoneya == 0 || betOddsb<=1){
				betOddsb = 1.01;
				betOddsa = betMaxOdds;
			}
			if(0 < betMoneya && betMoneya <= pumpMoney){
				if(betOddsa>=betMaxOdds){
					betOddsa = betMaxOdds;
					if( betMoneyb - (betOddsa * betMoneya) > pumpMoneya){
						pumpMoneya = betMoneyb - betOddsa * betMoneya;
					}
				}
			}
			
			if(betMoneyb == 0 || betOddsa<=1){
				betOddsa = 1.01;
				betOddsb = betMaxOdds;
			}
			if(0 < betMoneyb && betMoneyb <= pumpMoney){
				if(betOddsb>=betMaxOdds){
					betOddsb = betMaxOdds;
					if( betMoneya - (betOddsb * betMoneyb) > pumpMoneyb){
						pumpMoneyb = betMoneya - betOddsb * betMoneyb;
					}
				}
			}
			jsonMap.put("betOddsa", odFormat.format(betOddsa));
			jsonMap.put("betOddsb", odFormat.format(betOddsb));
			jsonMap.put("betRatioa", betRatioa);
			jsonMap.put("betRatiob", betRatiob);
			
			jsonMap.put("pumpMoney", pumpMoney);
			jsonMap.put("pumpMoneya", pumpMoneya);
			jsonMap.put("pumpMoneyb", pumpMoneyb);
			return jsonMap;
		}
}
