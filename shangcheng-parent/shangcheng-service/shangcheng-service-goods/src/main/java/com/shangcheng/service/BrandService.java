package com.shangcheng.service;

import com.github.pagehelper.PageInfo;
import com.shangcheng.goods.pojo.Brand;
import io.swagger.models.auth.In;

import java.util.List;

public interface BrandService {
    /**
     * 多条件+分页搜索
     * @param brand:搜索条件
     */
    PageInfo<Brand> findPage(Brand brand,Integer page,Integer size);
    /**
     * 分页搜素 pagehelper
     * @param page:当前页
     * @param size:每页条数
     */
    PageInfo<Brand> findPage(Integer page,Integer size);
    /**
     * 根据品牌信息多条件搜索
     */
    List<Brand> findList(Brand brand);
    /**
     * 根据id删除
     */
    void delete(Integer id);
    /**
     * 修改品牌
     */
    void update(Brand brand);
    /**
     * 增加品牌
     */
    void add(Brand brand);
    /**
     * 根据id查询
     */
    Brand findById(Integer id);

    /**
     * 查询所有
     */
    List<Brand> findAll();
}
