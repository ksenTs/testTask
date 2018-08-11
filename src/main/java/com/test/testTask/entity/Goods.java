package com.test.testTask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Goods {
    private int goodId;
    private String description;
    private String goodName;


    @Id
    @GeneratedValue
    @Column(name = "good_id")
    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "good_name")
    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public List<Category> categoryList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "goods_category",
            joinColumns = @JoinColumn(name = "good_id", referencedColumnName = "good_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categories) {
        this.categoryList = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        if (goodId != goods.goodId) return false;
        if (description != null ? !description.equals(goods.description) : goods.description != null) return false;
        if (goodName != null ? !goodName.equals(goods.goodName) : goods.goodName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = goodId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (goodName != null ? goodName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodId=" + goodId +
                ", description='" + description + '\'' +
                ", goodName='" + goodName + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }

    public void deleteCategory(Category cat) {
        categoryList.removeIf(cat::equals);
    }
}
