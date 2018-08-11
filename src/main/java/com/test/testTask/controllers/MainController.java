package com.test.testTask.controllers;

import com.test.testTask.entity.Category;
import com.test.testTask.entity.Goods;
import com.test.testTask.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private GoodsRepository goodsRepository;


    @RequestMapping(value = "/")
    public ModelAndView getPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("../static/indexx");
        return model;
    }


    @RequestMapping(value = "/getCategoryList")
    public @ResponseBody
    List<Category> getCategoryList(@RequestParam("goodName") String goodName)
    {

        Goods good = goodsRepository.findByGoodName(goodName);
        List<Category> categories = good.getCategoryList();
        return categories;

    }









}
