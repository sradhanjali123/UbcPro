package com.ubc.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(name="Menu")
@Table(name="MENUCONTENT")
public class Menu {
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MENU")
	 @SequenceGenerator(sequenceName = "SEQ_MENU", allocationSize = 1, name = "SEQ_MENU")
	 @Column(name="MENU_ID")
	 @Expose
	 @SerializedName("menu_id")
	 private Long menuId;
	@Column(name="MENU_NAME")
	 @Expose
	 @SerializedName("menu_name")
	 @OrderBy("MENU_ORDER ASC")
	 private String menuName;
	 @Column(name="MENU_ORDER")
	 @Expose
	 @SerializedName("menu_order")
	 private Long menuOrder;
	 public Long getMenuId() {
			return menuId;
		}
		public void setMenuId(Long menuId) {
			this.menuId = menuId;
		}
		public String getMenuName() {
			return menuName;
		}
		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}
		public Long getMenuOrder() {
			return menuOrder;
		}
		public void setMenuOrder(Long menuOrder) {
			this.menuOrder = menuOrder;
		}
	 

}
