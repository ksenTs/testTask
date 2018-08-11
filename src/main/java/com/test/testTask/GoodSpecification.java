package com.test.testTask;

import com.test.testTask.entity.Category;
import com.test.testTask.entity.Goods;
import com.test.testTask.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GoodSpecification {

    @Autowired
    private GoodsRepository goodsRepository;

    public static Specification<Goods> findGoods(final String name) {
        return new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("goodName"), name + "%");
            }
        };
    }

    public static Specification<Goods> findGoodsByCategory(final String categoryName) {
        return new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Join<Goods, Category> category = root.join("categoryList");
                return criteriaBuilder.equal(category.get("categoryName"), categoryName);
            }
        };
    }




    @RequestMapping(value = "/findGood", method = RequestMethod.POST)
    public @ResponseBody
    List findGoods(@RequestParam("search_req") String search_req,
                          @RequestParam("filter") String filter)
    {

        List goods = new ArrayList<>();

        switch (filter){
            case ("category"):{

                goods = goodsRepository.findAll(findGoodsByCategory(search_req));
                break;
            }

            case ("name"):{

                goods = goodsRepository.findAll(findGoods(search_req + "%"));
                break;
            }
        }
        return goods;

    }

}
