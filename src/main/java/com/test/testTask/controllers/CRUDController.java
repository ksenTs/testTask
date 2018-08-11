package com.test.testTask.controllers;

import com.test.testTask.entity.Category;
import com.test.testTask.entity.Goods;
import com.test.testTask.repository.CategoryRepository;
import com.test.testTask.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CRUDController {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @RequestMapping(value = "/editGood")
    public @ResponseBody
    ResponseEntity editGood(@RequestParam("id") String id, @RequestParam("newName") String newName, @RequestParam("newDescr") String newDescr) {
        Goods good = goodsRepository.findByGoodId(Integer.valueOf(id));
        if (!newName.isEmpty() && !newDescr.isEmpty()) {
            good.setGoodName(newName);
            good.setDescription(newDescr);
        } else
            System.out.println("Проблема!");

        goodsRepository.save(good);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/removeGood")
    public @ResponseBody
    ResponseEntity removeGood(@RequestParam("id") String id) {
        Goods good = goodsRepository.findByGoodId(Integer.valueOf(id));
        good.categoryList.forEach(category -> category.deleteGood(good));
        categoryRepository.save(good.categoryList);
        good.categoryList.clear();
        goodsRepository.delete(good);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/addGood")
    public @ResponseBody
    ResponseEntity addGood(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("categoryName") String categoryName) {

        Goods newOne = new Goods();

        newOne.setGoodName(name);
        newOne.setDescription(description);

        goodsRepository.save(newOne);

        Category newCategory = new Category();
        newCategory.setCategoryName(categoryName);
        categoryRepository.save(newCategory);

        newOne.categoryList.add(newCategory);
        newCategory.goodsList.add(newOne);

        return new ResponseEntity(HttpStatus.OK);
    }

}
