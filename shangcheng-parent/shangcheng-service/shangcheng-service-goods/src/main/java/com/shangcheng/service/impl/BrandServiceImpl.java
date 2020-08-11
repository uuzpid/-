package com.shangcheng.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shangcheng.dao.BrandMapper;
import com.shangcheng.goods.pojo.Brand;
import com.shangcheng.service.BrandService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    /**
     * 多条件+分页搜索
     * @param brand:搜索条件
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        int p = 10/0;
        //分页
        PageHelper.startPage(page,size);
        //搜索数据
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        //封装PageInfo<Brand>
        return new PageInfo<Brand>(brands);
    }

    /**
     * 分页查询
     * @param page:当前页
     * @param size:每页条数
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        /**
         * 分页查询 必须查询的是集合
         * @param page:当前页
         * @param size:每页条数
         */
        PageHelper.startPage(page,size);
        List<Brand> brands = brandMapper.selectAll();
        //封装PageInfo
        return new PageInfo<Brand>(brands);
    }
    /**
     * 多条件搜索
     * @param brand
     * @return
     */
    @Override
    public List<Brand> findList(Brand brand) {
        //调用构建条件
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }
    /**
     * 条件查询的条件构建
     */
    public Example createExample(Brand brand){
        //自定义条件搜索对象tk包下的example
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();//条件构造器
        if(brand!=null){
            //根据名字模糊搜索
            if(!StringUtils.isEmpty(brand.getName())){
                /**
                 * 1.Brand的属性名
                 * 2.占位符参数，搜索的条件
                 */
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            if(!StringUtils.isEmpty(brand.getLetter())){
                //如果首字母不为空
                criteria.andEqualTo("letter",brand.getLetter());
            }
        }
        return example;
    }

    /**
     * 根据id删除
     * @param id
     */
    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改品牌
     */
    @Override
    public void update(Brand brand) {
       brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 增加品牌
     */
    @Override
    public void add(Brand brand) {
        //方法中带有Selective会忽略空值
        brandMapper.insertSelective(brand);
    }

    /**
     * 根据id查询
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }
}
