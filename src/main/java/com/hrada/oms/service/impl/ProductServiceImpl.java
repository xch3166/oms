package com.hrada.oms.service.impl;

import com.hrada.oms.dao.model.ProductCategoryRepository;
import com.hrada.oms.dao.model.ProductRepository;
import com.hrada.oms.model.model.Product;
import com.hrada.oms.model.model.ProductCategory;
import com.hrada.oms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shin on 2019-03-12.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    private String no = "";

    @Override
    public Product save(Product product) {
        product = productRepository.save(product);
        no = "";
        createNo(product.getCategory());
        if(product.getId()<10){
            product.setNo(no+"000"+product.getId());
        }else if(product.getId()<100){
            product.setNo(no+"00"+product.getId());
        }else if(product.getId()<1000){
            product.setNo(no+"0"+product.getId());
        }else{
            product.setNo(no+product.getId());
        }

        return productRepository.save(product);
    }

    public void createNo(ProductCategory category){
        if(category!=null){
            if(!category.getNo().equals("")){
                no = category.getNo() + no;
            }else{
                List<ProductCategory> list = productCategoryRepository.findByParent(category.getParent());
                int index = list.indexOf(category)+1;
                if(index<10){
                    no = "0" + index + no;
                }else{
                    no = index + no;
                }
            }
            createNo(category.getParent());
        }
    }

}
