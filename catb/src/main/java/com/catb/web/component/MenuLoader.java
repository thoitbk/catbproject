package com.catb.web.component;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.catb.bo.NewsCatalogBO;
import com.catb.model.NewsCatalog;

@Component
public class MenuLoader {
	
	@Autowired
	private NewsCatalogBO newsCatalogBO;
	
	private String prefix = "";
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public List<Menu> loadMenuTree() {
		List<NewsCatalog> rootNewsCatalogs = newsCatalogBO.getNewsCatalogs(
				DisplayLocation.TOP.getPosition(), null, 0, true);
		List<Menu> rootMenus = convertNewsCatalogsToMenus(rootNewsCatalogs);
		if (rootMenus != null && rootMenus.size() > 0) {
			for (Menu menu : rootMenus) {
				buildMenuTree(menu);
			}
		}
		
		return rootMenus;
	}
	
	private void buildMenuTree(Menu menu) {
		List<NewsCatalog> childNewsCatalogs = newsCatalogBO.getNewsCatalogs(
				DisplayLocation.TOP.getPosition(), menu.getId(), menu.getLevel() + 1, true);
		List<Menu> childMenus = convertNewsCatalogsToMenus(childNewsCatalogs);
		if (childMenus == null || childMenus.size() == 0) {
			return;
		} else {
			menu.getChildMenus().addAll(childMenus);
			for (Menu childMenu : childMenus) {
				buildMenuTree(childMenu);
			}
		}
	}
	
	private List<Menu> convertNewsCatalogsToMenus(List<NewsCatalog> newsCatalogs) {
		List<Menu> menus = new LinkedList<Menu>();
		if (newsCatalogs != null && newsCatalogs.size() > 0) {
			for (NewsCatalog newsCatalog : newsCatalogs) {
				menus.add(new Menu(newsCatalog.getId(), newsCatalog.getName(), prefix + newsCatalog.getUrl(), 
									newsCatalog.getChildLevel(), newsCatalog.getParentId()));
			}
		}
		
		return menus;
	}
	
	public enum DisplayLocation {
		TOP("top"), BOTTOM("bottom"), LEFT("left"), RIGHT_TOP("rightTop"), RIGHT_CENTER("rightCenter");
		
		private String position;
		
		private DisplayLocation(String position) {
			this.position = position;
		}
		
		public String getPosition() {
			return position;
		}
	}
}
