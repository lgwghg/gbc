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
 * @date 2017-04-18 11:04:31
 */
public class RollRoom implements Serializable
{
	private static final long serialVersionUID = 1L;

	public RollRoom(){}
	

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
	 * 房主
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 开始时间
	 */
	private String startTime;
	
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	
	public String getStartTime(){
		return this.startTime;
	}

	/**
	 * 结束时间
	 */
	private String endTime;
	
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	
	public String getEndTime(){
		return this.endTime;
	}

	/**
	 * 直播间
	 */
	private String roomUrl;
	
	public void setRoomUrl(String roomUrl){
		this.roomUrl = roomUrl;
	}
	
	public String getRoomUrl(){
		return this.roomUrl;
	}

	/**
	 * 新浪微博
	 */
	private String sinaUrl;
	
	public void setSinaUrl(String sinaUrl){
		this.sinaUrl = sinaUrl;
	}
	
	public String getSinaUrl(){
		return this.sinaUrl;
	}

	/**
	 * QQ群
	 */
	private Integer qqNum;
	
	public void setQqNum(Integer qqNum){
		this.qqNum = qqNum;
	}
	
	public Integer getQqNum(){
		return this.qqNum;
	}

	/**
	 * 活动说明
	 */
	private String remarks;
	
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	
	public String getRemarks(){
		return this.remarks;
	}

	/**
	 * 自定义房间号
	 */
	private String roomCode;
	
	public void setRoomCode(String roomCode){
		this.roomCode = roomCode;
	}
	
	public String getRoomCode(){
		return this.roomCode;
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
        if (!(other instanceof RollRoom)){
			return false;
		}
        RollRoom castOther = (RollRoom) other;
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