package com.zhizun.pos.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.ch.epw.utils.Constant;
import com.zhizun.pos.base.BaseBean;

public class SortTreeNodeWrapper extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final String TAG = SortTreeNodeWrapper.class.getName();

	public static List<SortTreeNode> parse(String[][] stringArrArray) {
		if (stringArrArray == null) {
			return null;
		}
		Map<String, SortTreeNode> mappedItems = new HashMap<String, SortTreeNode>();
		for (int k = 0; k < stringArrArray.length; k++) {
			String[] itemArray = stringArrArray[k];
			if (itemArray != null && itemArray.length >= 4) {
				String itemId = itemArray[0];
				String itemName = itemArray[1];
				String parentId = itemArray[2];
				String codeType = itemArray[3];
				boolean isChecked = false;
				if (itemArray.length == 5) {
					isChecked = "1".equals(itemArray[4]);
				}

				// 节点本身是一个父级元素，且该元素已经在map中，只需要补全父元素的itemName
				SortTreeNode treeNode = mappedItems.get(itemId);
				if (treeNode == null) {
					treeNode = new SortTreeNode();
				}
				treeNode.setItemName(itemName);
				treeNode.setType(codeType);
				treeNode.setChecked(isChecked);
				if (!TextUtils.isEmpty(treeNode.getItemId())) {
					continue;
				}

				treeNode.setItemId(itemId);
				SortTreeNode parentNode = null;
				// 存在父节点，将新节点插入到父节点中
				if (!TextUtils.isEmpty(parentId)) {
					parentNode = mappedItems.get(parentId);
					if (parentNode == null) {
						parentNode = new SortTreeNode();
						parentNode.setItemId(parentId);
						mappedItems.put(parentId, parentNode);
					}
					treeNode.setParentNode(parentNode);
					parentNode.getSubItemList().add(treeNode);
					continue;
				}

				// 将元素直接插入到map中
				mappedItems.put(itemId, treeNode);
			}
		}
		
		return new ArrayList<SortTreeNode>(mappedItems.values());
	}

	public static List<SortTreeNode> parse(List<CourseRegions> regionList) {
		if (regionList == null || regionList.size() == 0) {
			return null;
		}
		List<String[]> regionArrayList = new ArrayList<String[]>();
		for (CourseRegions region : regionList) {
			String[] regionArray = new String[4];
			regionArray[0] = region.getKey();
			regionArray[1] = region.getValue();
			regionArray[2] = "";
			regionArray[3] = Constant.SORT_NODE_TYPE_COUNTY_CODE;
			regionArrayList.add(regionArray);
		}

		String[][] retArray = new String[regionArrayList.size()][];
		return parse((String[][]) regionArrayList.toArray(retArray));
	}

	public static List<SortTreeNode> parse(JSONArray categoriesArray, SortTreeNode parentNode) {
		List<SortTreeNode> cateNodeList = new ArrayList<SortTreeNode>();
		for (int k = 0; k < categoriesArray.length(); k++) {
			JSONObject cateObject = categoriesArray.optJSONObject(k);
			SortTreeNode treeNode = new SortTreeNode();
			treeNode.setItemId(cateObject.optString("key"));
			treeNode.setItemName(cateObject.optString("value"));
			treeNode.setType(Constant.SORT_NODE_TYPE_CATEGORY_CODE);

			// 不包含 item 子元素，表示是二级分类
			if (cateObject.isNull("item")) {
				treeNode.setParentNode(parentNode);
				if (parentNode != null) {
					parentNode.getSubItemList().add(treeNode);
				}
			} else {
				JSONArray subItemJsonArray = cateObject.optJSONArray("item");
				parse(subItemJsonArray, treeNode);
				cateNodeList.add(treeNode);
			}
		}
		
		return cateNodeList;
	}
}
