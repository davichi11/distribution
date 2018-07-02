package com.distribution.modules.integral.service.impl;

import com.distribution.modules.integral.dao.ProductDetailDao;
import com.distribution.modules.integral.entity.ProductDetailEntity;
import com.distribution.modules.integral.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("productDetailService")
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailDao productDetailDao;

    @Override
    public ProductDetailEntity queryObject(String id) {
        return productDetailDao.queryObject(id);
    }

    @Override
    public List<ProductDetailEntity> queryList(Map<String, Object> map) {
        return productDetailDao.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProductDetailEntity productDetail) throws Exception {
        productDetailDao.save(productDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductDetailEntity productDetail) throws Exception {
        productDetailDao.update(productDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        productDetailDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        productDetailDao.deleteBatch(ids);
    }

}
