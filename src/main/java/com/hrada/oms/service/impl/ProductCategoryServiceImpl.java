package com.hrada.oms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrada.oms.dao.model.ProductCategoryRepository;
import com.hrada.oms.model.model.ProductCategory;
import com.hrada.oms.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shin on 2019-04-09.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Override
    public JSONArray tree(Long id) {
        List<ProductCategory> list = productCategoryRepository.findAllByParentIsNull();
        return recursion(list, id);
    }

    public JSONArray recursion(List<ProductCategory> list, Long id){
        JSONArray array = new JSONArray();
        for(ProductCategory productCategory:list) {
            JSONObject object = new JSONObject( );
            String no = "";
            if(!productCategory.getNo().equals("")){
                no = productCategory.getNo() + no;
            }else{
                int index = list.indexOf(productCategory)+1;
                if(index<10){
                    no = "0" + index + no;
                }else{
                    no = index + no;
                }
            }
            object.put("id", productCategory.getId( ));
            object.put("text", productCategory.getName() +" "+ no);
            if (id == productCategory.getId( )) {
                JSONObject select = new JSONObject( );
                select.put("selected", true);
                object.put("state", select);
            }
            List<ProductCategory> slist = productCategoryRepository.findByParent(productCategory);
            object.put("children", recursion(slist, id));
            array.add(object);
        }
        return array;
    }
}
