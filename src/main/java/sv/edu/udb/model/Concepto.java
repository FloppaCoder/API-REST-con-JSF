package sv.edu.udb.model;

public class Concepto {
	
	private int id;
	private String name;
	private double value;
	private int categoryId;
	private Categoria category;
	
	public int getId() {
		return id;
		}
		/**
		* @param id the id to set
		*/
		public void setId(int id) {
		this.id = id;
		}
		/**
		* @return the name
		*/
		public String getName() {
		return name;
		}
		/**
		* @param name the name to set
		*/
		public void setName(String name) {
		this.name = name;
		}
		/**
		* @return the value
		*/
		public double getValue() {
		return value;
		}
		/**
		* @param value the value to set
		*/
		public void setValue(double value) {
		this.value = value;
		}
		/**
		* @return the categoryId
		*/
		public int getCategoryId() {
		return categoryId;
		}
		/**
		* @param categoryId the categoryId to set
		*/
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
		/**
		* @return the category
		*/
		public Categoria getCategory() {
		return category;
		}
		/**
		* @param category the category to set
		*/
		public void setCategory(Categoria category) {
		this.category = category;
		}

}
