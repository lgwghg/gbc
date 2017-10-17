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
package com.webside.data.solr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.core.query.result.FacetPage;

import com.webside.data.solr.model.User;

/**
 * @author Christoph Strobl
 */
public interface UserRepository extends CrudRepository<User, String>
{
	/**
	 * 根据前缀查询
	 * 字符串空 "", 查询全部
	 * @param namePrefix
	 * @return
	 */
	FacetPage<User> findByName(String namePrefix);
}
