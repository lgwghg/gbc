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
 * @date 2017-04-18 11:02:39
 */
public class RollMember implements Serializable
{
	private static final long serialVersionUID = 1L;

	public RollMember(){}
	

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
	 * 房间ID
	 */
	private String roomId;
	
	public void setRoomId(String roomId){
		this.roomId = roomId;
	}
	
	public String getRoomId(){
		return this.roomId;
	}

	/**
	 * 成员ID
	 */
	private String userId;
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	/**
	 * 参与时间
	 */
	private String createTime;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String toString() {
        return new ToStringBuilder(this)
        			.append("Id", getId())
		            .toString();
	}

	public boolean equals(Object other) {
        if (!(other instanceof RollMember)){
			return false;
		}
        RollMember castOther = (RollMember) other;
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