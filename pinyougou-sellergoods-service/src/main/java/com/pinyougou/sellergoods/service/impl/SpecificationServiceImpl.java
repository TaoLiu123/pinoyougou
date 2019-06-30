package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		TbSpecification tbspecification = specification.getSpecification();
		//获取规格实体
		specificationMapper.insert(tbspecification);
		//获取回个选项集合
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();

		for (TbSpecificationOption option : specificationOptionList) {
			option.setSpecId(tbspecification.getId());//获取新增id
			specificationOptionMapper.insert(option);//新证规格

		}
	}


	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification) {
		//获取实体对象
		TbSpecification tbSpecification = specification.getSpecification();
		specificationMapper.updateByPrimaryKey(tbSpecification);
		//删除原来的数据
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(tbSpecification.getId());
		specificationOptionMapper.deleteByExample(example);
		//给specification中添加的第二个集合列表，即列集合
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		//遍历集合
		for (TbSpecificationOption tbSpecificationOption : specificationOptionList) {
			tbSpecificationOption.setSpecId(tbSpecification.getId());//设置规格id
			specificationOptionMapper.insert(tbSpecificationOption);//新增规格

		}
	}

	/**
	 * 根据ID获取实体
	 *
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id) {
		Specification specification = new Specification();
		//获取规格实体对象
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		//给实体中添加第一个属性
		specification.setSpecification(tbSpecification);
		//获取第二个集和属性
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);

		List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);
		//给specification中添加的第二个集合列表，即列集合
		specification.setSpecificationOptionList(tbSpecificationOptions);

		return specification;//返回组合实体类
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
//			删除规格表数据
			specificationMapper.deleteByPrimaryKey(id);
//			删除规格选项表数据
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);

			specificationOptionMapper.deleteByExample(example);

		}
	}


	@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbSpecificationExample example = new TbSpecificationExample();
		Criteria criteria = example.createCriteria();

		if (specification != null) {
				if (specification.getSpecName()!=null && specification.getSpecName().length()>0){

					criteria.andSpecNameLike("%"+specification.getSpecName()+"%");

				}
				if (specification.getId()!=null){
					//specificationMapper.selectByPrimaryKey(specification.getId());
					criteria.andIdEqualTo(specification.getId());
				}
		}
		List<TbSpecification> tbSpecifications = specificationMapper.selectByExample(example);
		Page<TbSpecification> page = (Page<TbSpecification>) tbSpecifications;
		return new PageResult(page.getTotal(), page.getResult());


	}


	@Override
	public List<Map> selectOptionList() {
		return specificationMapper.selectOptionList();
	}
}
