package com.pinyougou.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {


    @Reference
    private BrandService brandService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbBrand> finidAll(){

        return brandService.findAll();
    };

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */

    @RequestMapping("/findPage")
    public PageResult findPage (int page ,int rows){

        return brandService.findPage(page, rows);
    }

    /**
     * 添加数据
     * @param tbBrand
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestBody TbBrand tbBrand){

        try {

            brandService.save(tbBrand);
            return  new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    /**
     * 删除数据
     * @param ids
     * @return
     */

    @RequestMapping("/delete")
    public Result delete(Long[] ids){

        try {
            brandService.delete(ids);
           return   new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbBrand findOne(long id){


         return  brandService.findOne(id);

    }

    /**
     * 修改数据
     * @param tbBrand
     * @return
     */

    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand tbBrand){

        try {

            brandService.update(tbBrand);
            return  new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    /**
     * 条件查询
     * @param tbBrand
     * @return
     */
    @RequestMapping("/select")
    public PageResult select(@RequestBody TbBrand tbBrand,int page ,int rows){

      return  brandService.findPage(tbBrand,page,rows);

    }
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
    return  brandService.selectOptionList();

    }

}
