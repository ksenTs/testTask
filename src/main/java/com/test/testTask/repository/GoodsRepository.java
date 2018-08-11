package com.test.testTask.repository;

import com.test.testTask.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsRepository extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {

    Goods findByGoodId(int id);

   Goods findByGoodName(String name);
}
