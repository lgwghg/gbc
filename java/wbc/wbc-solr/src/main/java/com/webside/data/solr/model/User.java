/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webside.data.solr.model;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author Christoph Strobl
 */
public class User implements SearchableUser {

	@Field(ID_FIELD)
	private String id;

	@Field(NAME_FIELD)
	private String name;

	@Field(DELETE_FIELD)
	private String isDeleted;

	@Field(MOBILE_FIELD)
	private String mobile;

	@Field(EMAIL_FIELD)
	private String email;

	@Field(UPDATE_TIME_FIELD)
	private Long updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", isDeleted="
				+ isDeleted + ", mobile=" + mobile + ", email=" + email
				+ ", updateTime=" + updateTime + "]";
	}

}
