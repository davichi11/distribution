package com.distribution.modules.integral.service.impl;

import com.distribution.modules.integral.dao.ProductTypeDao;
import com.distribution.modules.integral.entity.ProductTypeEntity;
import com.distribution.modules.integral.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("productTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeDao productTypeDao;

    @Override
    public ProductTypeEntity queryObject(String id) {
        return productTypeDao.queryObject(id);
    }

    @Override
    public List<ProductTypeEntity> queryList(Map<String, Object> map) {
        return productTypeDao.queryList(map);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(ProductTypeEntity productType) throws Exception {
        productTypeDao.save(productType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductTypeEntity productType) throws Exception {
        productTypeDao.update(productType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) throws Exception {
        productTypeDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) throws Exception {
        productTypeDao.deleteBatch(ids);
    }

}
