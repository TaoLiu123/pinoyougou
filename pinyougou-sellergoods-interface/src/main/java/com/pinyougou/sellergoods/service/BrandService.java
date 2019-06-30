package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {

   public List<TbBrand> findAll();

   public PageResult findPage(int pageNum ,int pageSize);

   public void save(TbBrand tbBrand );

   public void delete (Long[] ids);

   public TbBrand findOne(long id);

   public void update(TbBrand tbBrand);

   public PageResult findPage(TbBrand tbBrand ,int page ,int rows);

   public List<Map> selectOptionList();

}
