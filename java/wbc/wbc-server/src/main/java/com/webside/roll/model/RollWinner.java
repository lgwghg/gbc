/*******************************************************************************
 * All rights reserved. 
 * 
 * @author 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package com.webside.roll.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *  entity
 *
 * @author zhangfei
 * @date 2017-04-18 13:51:57
 */
public class RollWinner implements Serializable
{
	private static final long serialVersionUID = 1L;

	public RollWinner(){}
	

	/**
	 * 主键ID
	 */
	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	/**
	 * 获奖人员
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 奖品
	 */
	private String poolId;
	
	public void setPoolId(String poolId){
		this.poolId = poolId;
	}
	
	public String getPoolId(){
		return this.poolId;
	}

	/**
	 * 次数
	 */
	private Integer num;
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Integer getNum(){
		return this.num;
	}

	/**
	 * 获奖时间
	 */
	private String winTime;
	
	public void setWinTime(String winTime){
		this.winTime = winTime;
	}
	
	public String getWinTime(){
		return this.winTime;
	}

	/**
	 * 状态
	 */
	private Integer status;
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public Integer getStatus(){
		return this.status;
	}

	public String toString() {
        return new ToStringBuilder(this)
        			.append("Id", getId())
		            .toString();
	}

	public boolean equals(Object other) {
        if (!(other instanceof RollWinner)){
			return false;
		}
        RollWinner castOther = (RollWinner) other;
        return new EqualsBuilder()
        			.append(this.getId(), castOther.getId())
		           .isEquals();
	}

	public int hashCode() {
        return new HashCodeBuilder()
        			.append(this.getId())
		            .toHashCode();
	}

}