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

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FacetOptions;
import org.springframework.data.solr.core.query.FacetQuery;
import org.springframework.data.solr.core.query.SimpleFacetQuery;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;

import com.webside.data.solr.model.User;

/**
 * @author Christoph Strobl
 */
@NoRepositoryBean
public class SolrProductRepository extends SimpleSolrRepository<User, String> implements UserRepository {

	@Override
	public FacetPage<User> findByName(String namePrefix) {
		FacetQuery query = new SimpleFacetQuery(new Criteria(SolrSearchableFields.NAME).startsWith(namePrefix));
		query.setFacetOptions(new FacetOptions(SolrSearchableFields.DELETE));
		return getSolrOperations().queryForFacetPage(query, User.class);
	}
}
