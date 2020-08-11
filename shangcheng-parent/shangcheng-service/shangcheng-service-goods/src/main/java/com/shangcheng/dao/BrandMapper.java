package com.shangcheng.dao;

import com.shangcheng.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

/**
 * Dao使用通用Mapper
 * 只要继承这个包下的Mapper
 * tk.mybatis.mapper.common.Mapper
 * 如果想要增加数据，直接调用Mapper.insert(),Mapper.insertSelective()
 * 修改,调用Mapper.update(T),Mapper.updateByPrimayKey(T)
 * 等等
 */
public interface BrandMapper extends Mapper<Brand> {
}
