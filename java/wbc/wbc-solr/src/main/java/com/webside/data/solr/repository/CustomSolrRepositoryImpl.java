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

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;

import com.webside.data.solr.model.User;
import com.webside.data.solr.model.SearchableUser;

/**
 * @author Christoph Strobl
 */
public class CustomSolrRepositoryImpl implements CustomSolrRepository {

	private SolrOperations solrTemplate;

	public CustomSolrRepositoryImpl() {
		super();
	}

	public CustomSolrRepositoryImpl(SolrOperations solrTemplate) {
		super();
		this.solrTemplate = solrTemplate;
	}

	@Override
	public Page<User> findProductsByCustomImplementation(String value, Pageable page) {
		return solrTemplate.queryForPage(new SimpleQuery(new SimpleStringCriteria(SearchableUser.NAME_FIELD + ":" + value)).setPageRequest(page),
				User.class);
	}

	@Override
	public void updateProductCategory(String productId, List<String> categories) {
		PartialUpdate update = new PartialUpdate(SearchableUser.ID_FIELD, productId);
		solrTemplate.saveBean(update);
		solrTemplate.commit();
	}

}
