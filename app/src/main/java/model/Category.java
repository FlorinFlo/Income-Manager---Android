package model;


public class Category {
    private long category_id;
    private String name;
    private String color;

    public Category(long category_id, String name, String color) {
        super();
        this.category_id = category_id;
        this.name = name;
        this.color = color;
    }

    public Category() {
        super();
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return name;
    }
}
