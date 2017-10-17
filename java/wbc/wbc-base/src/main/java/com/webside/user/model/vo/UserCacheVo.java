package com.webside.user.model.vo;

import java.io.Serializable;

public class UserCacheVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2246260794376350349L;
	/**
	 * 用户id
	 */
	private String id;
	private String n; // 用户昵称
	private String m; // 手机号码
	private String p; // 头像
	private String e; // 邮箱
	private String s; // 签名
	private String rl; // 头衔级别
	private String ck; // 推广key
	private String pa; // 支付宝账号

	/**
	 * 竞猜量
	 */
	private Integer jn;
	/**
	 * 胜利量
	 */
	private Integer vn;
	/**
	 * 胜率
	 */
	private Double vr;
	/**
	 * 累计盈利G币数量
	 */
	private Integer pg;
	/**
	 * 消息未读量 UNREAD_NUM
	 */
	private Integer un;
	/**
	 * 签到总量 signNum
	 */
	private Integer sn;
	/**
	 * 是否参与过竞猜
	 */
	private Integer ij;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Integer getUn() {
		return un;
	}

	public void setUn(Integer un) {
		this.un = un;
	}

	public Integer getJn() {
		return jn;
	}

	public void setJn(Integer jn) {
		this.jn = jn;
	}

	public Integer getVn() {
		return vn;
	}

	public void setVn(Integer vn) {
		this.vn = vn;
	}

	public Double getVr() {
		return vr;
	}

	public void setVr(Double vr) {
		this.vr = vr;
	}

	public Integer getPg() {
		return pg;
	}

	public void setPg(Integer pg) {
		this.pg = pg;
	}

	public String getRl() {
		return rl;
	}

	public void setRl(String rl) {
		this.rl = rl;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}

	public Integer getIj() {
		return ij;
	}

	public void setIj(Integer ij) {
		this.ij = ij;
	}

	public String getPa() {
		return pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}

}
