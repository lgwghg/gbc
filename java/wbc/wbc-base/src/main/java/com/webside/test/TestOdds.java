package com.webside.test;

import java.text.NumberFormat;
import java.util.Scanner;

public class TestOdds {
	
	//抽水阶梯
	//金额在1万以内的抽5 %  1万-5万的抽10%  5万 -10万 抽15%   10万 网上的抽20%
	private static final double highestPump = 0.2;	
	private static final double highPump = 0.15;
	private static final double middlePump = 0.1;
	private static final double lowPump = 0.05;
	private static final double NonePump = 0;
	
	//当前使用抽水的阶梯额
	private static double pump = NonePump;
	
	//抽水金额
	private static double pumpMoney = 0;
	//A获胜抽水额度
	private static double pumpMoneya = 0;
	//B获胜抽水额度
	private static double pumpMoneyb = 0;
	
	//赔率
	private static double betOddsa = 1;
	private static double betOddsb = 1;
	
	//最大赔率
	private static double betMaxOdds = 30;
	
	//下注人数
	private static double beta = 0;
	private static double betb = 0;
	
	//下注金额
	private static double betMoneya = 0;
	private static double betMoneyb = 0;
	
	private static double betMoney = 0;
	
	//下注比例
	private static String betRatioa = "50%";
	private static String betRatiob = "50%";
	
	
	
	/**
	 * 赔率测试
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		NumberFormat ptFormat = NumberFormat.getPercentInstance();
		NumberFormat odFormat = NumberFormat.getInstance(); 
		odFormat.setMaximumFractionDigits(2);
		String  option = "out";
		int money = 0;
		
		while (!option.equals("")) {
			System.out.println("当前A队数据  下注人数:"+beta+",下注比例："+betRatioa+",下注金额:"+betMoneya+",赔率:"+odFormat.format(betOddsa)+",A获胜，抽水金额："+ pumpMoneya +"..当前B队数据  下注人数:"+betb+","
					+ "下注比例："+betRatiob+",下注金额:"+betMoneyb+",赔率:"+odFormat.format(betOddsb)+"...抽水阶梯:"+pump+",B获胜，抽水金额："+ pumpMoneyb );
			System.out.println("请选择下注的队伍a or b,如果想退出请输入c：");
			option = in.next();
			while(!option.equals("c")&&!option.equals("b")&&!option.equals("a")){
				System.out.println("输入了无效值，请重新输入:");
				option = in.next();
			}
			
			if(option.equals("c")){
				option = "";
				continue;
			}else{
				System.out.println("请输入下注金额:");
				money = in.nextInt();
				
				betMoney = betMoneya + betMoneyb + money;
				if(100000 <= betMoney){
					pump = highestPump;
				}else if(50000 <= betMoney && betMoney < 100000){
					pump = highPump;;
				}else if(10000 <= betMoney && betMoney <50000){
					pump = middlePump;
				}else{
					pump = lowPump;
				}
				
				pumpMoneyb = pumpMoneya = pumpMoney = betMoney * pump;
				
				if(option.equals("a")){
					beta += 1;
					betMoneya += money;
				}else{
					betb += 1;
					betMoneyb += money;
				}
				betRatioa = ptFormat.format(beta/(beta + betb));
				betRatiob = ptFormat.format(betb/(beta + betb));
				
				betOddsa = (betMoneyb - pumpMoneya)/betMoneya+1;
				betOddsb = (betMoneya - pumpMoneyb)/betMoneyb+1;
				
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
				
				
			}
		}
	}
	
	
}
