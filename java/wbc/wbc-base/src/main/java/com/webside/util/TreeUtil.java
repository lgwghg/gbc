package com.webside.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

import com.webside.common.model.JSTreeEntity;
import com.webside.common.model.Select2Entity;
import com.webside.resource.model.ResourceEntity;

/**
 * 
 * @ClassName: TreeUtil
 * @Description: 树形结构工具类
 * @author gaogang
 * @date 2016年7月12日 下午4:27:10
 *
 */
public class TreeUtil {
	
	/*
	 * select2下拉组件数据对象
	 */
	private List<Select2Entity> selectTree = new ArrayList<Select2Entity>();
	/*
	 * 生成select2下拉组件数据时遍历的次数
	 */
	private int selectCnt = 0;
	
	/**
	 * 生成jstree要求的数据结构
	 * @param resourceList
	 * @return
	 */
	public List<JSTreeEntity> generateJSTree(List<ResourceEntity> resourceList)
	{
		List<JSTreeEntity> jstreeList = new ArrayList<JSTreeEntity>();
		
		for (ResourceEntity resourceEntity : resourceList) {
			JSTreeEntity jstree = new JSTreeEntity();
			
			jstree.setId(resourceEntity.getId());
			jstree.setParent(StringUtils.isBlank(resourceEntity.getParentId()) ? "#" : resourceEntity.getParentId());
			jstree.setText(resourceEntity.getName());
			jstree.setIcon(StringUtil.isBlank(resourceEntity.getIcon()) ? "fa fa-cog" : resourceEntity.getIcon());
			JSTreeEntity.State state = new JSTreeEntity().new State();
			state.setDisabled(false);
			state.setSelected(resourceEntity.getSelected());
			state.setOpened(true/*resourceEntity.getParentId()==null ? true : false*/);
			jstree.setState(state);
			jstreeList.add(jstree);
		}
		
		return jstreeList;
	}


	/**
	 * 根据父节点的ID获取所有子节点
	 * @param list	具有树形结构特点的集合
	 * @param parent	父节点ID
	 * @return	树形结构集合
	 */
	public List<Select2Entity> getSelectTree(List<ResourceEntity> list, String parentId) {
		List<ResourceEntity> returnList = getChildResourceEntitys(list, parentId);
		recursionForSelect(returnList);
		return selectTree;
	}
	
	
	/**
	 * 递归列表
	 * @param list
	 * @param t
	 */
	private void recursionForSelect(List<ResourceEntity> list) {
		String str = "";
		for(int i=0; i< selectCnt; i++)
		{
			str += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		for (ResourceEntity re : list) {
			if(null == re.getParentId())
			{
				str = "";
				selectCnt = 0;
			}
			Select2Entity se = new Select2Entity();
			se.setId(re.getId().toString());
			se.setText(str + re.getName());
			se.setName(re.getName());
			selectTree.add(se);
			if(re.getChildren().size() > 0)
			{
				selectCnt ++;
				recursionForSelect(re.getChildren());
			}
		}
	}
	
	
	
	/**
	 * 根据父节点的ID获取所有子节点
	 * @param list	具有树形结构特点的集合
	 * @param parent	父节点ID
	 * @return	树形结构集合
	 */
	public List<ResourceEntity> getChildResourceEntitys(List<ResourceEntity> list, String parentId) {
		List<ResourceEntity> returnList = new ArrayList<ResourceEntity>();
		for (Iterator<ResourceEntity> iterator = list.iterator(); iterator.hasNext();) {
			ResourceEntity t = iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (StringUtils.isBlank(parentId)) { // 父菜单id为null或""
				if (StringUtils.isBlank(t.getParentId())) {
					recursionFn(list, t);
					returnList.add(t);
				}
			} else {
				if (t.getParentId() == parentId) {
					recursionFn(list, t);
					returnList.add(t);
				}
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 * @param list
	 * @param t
	 */
	private void recursionFn(List<ResourceEntity> list, ResourceEntity t) {
		List<ResourceEntity> childList = getChildList(list, t);// 得到子节点列表
		t.setChildren(childList);
		for (ResourceEntity tChild : childList) {
			if (hasChild(list, tChild)) {// 判断是否有子节点
				Iterator<ResourceEntity> it = childList.iterator();
				while (it.hasNext()) {
					ResourceEntity n = (ResourceEntity) it.next();
					recursionFn(list, n);
				}
			}
		}
	}
	
	/**
	 * 得到子节点列表
	 * @param list
	 * @param t
	 * @return
	 */
	private List<ResourceEntity> getChildList(List<ResourceEntity> list, ResourceEntity t) {
		List<ResourceEntity> tlist = new ArrayList<ResourceEntity>();
		Iterator<ResourceEntity> it = list.iterator();
		while (it.hasNext()) {
			ResourceEntity n = (ResourceEntity) it.next();
			if(t.getType()!=2)
			{
				if (StringUtils.isNotBlank(n.getParentId()) && n.getParentId().equals(t.getId())) {
					//n.setParentName(t.getName());
					tlist.add(n);
				}
			}
		}
		return tlist;
	} 
	
	/**
	 * 判断是否有子节点
	 * @param list
	 * @param t
	 * @return
	 */
	private boolean hasChild(List<ResourceEntity> list, ResourceEntity t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}
	
}
