/*******************************************************************************
 * All rights reserved. 
 *
 * Contributors:
 *     PolyInfo Corporation - POC Team
 *******************************************************************************/
package org.webside.data.solr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.FacetPage;

import com.webside.data.solr.model.User;
import com.webside.data.solr.repository.CustomSolrRepository;
import com.webside.data.solr.repository.UserRepository;
import com.webside.data.spring.AppContext;

/**
 * @author sunhw
 * 
 */
public class SolrMain 
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try 
		{
			AppContext.getInstance().initFromClasspathXmlFiles("classpath*:spring/spring-*.xml");
			
			System.out.println("==================================================================");
			System.out.println("==============================模糊查询===============================");
			System.out.println("==================================================================");
			UserRepository repository = AppContext.getBean(UserRepository.class);
			FacetPage<User> pages = repository.findByName("");
			
			for (User user : pages)
			{
				System.out.println(user.toString());
			}
			
			System.out.println("");
			System.out.println("==================================================================");
			System.out.println("==============================分页查询===============================");
			System.out.println("==================================================================");
			
			CustomSolrRepository customSolrRepository = AppContext.getBean(CustomSolrRepository.class);
			Pageable page = new SolrPageRequest(0,15);
			
			Page<User> dtos = customSolrRepository.findProductsByCustomImplementation("Kua", page);
			
			for (User user : dtos.getContent()) {
				System.out.println(user.toString());
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
