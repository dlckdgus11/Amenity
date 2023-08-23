package com.amenity.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.amenity.goods.vo.GoodsVO;

@Mapper
@Repository("goodsDAO")
public interface GoodsDAO {
	
	public List selectAllGoodsList() throws DataAccessException;
	public List<GoodsVO> selectGoodsByCompany(String company) throws DataAccessException;
	public List companyGoodsList(String company) throws DataAccessException;
	public GoodsVO selectGoodsByNo(int g_no) throws DataAccessException;

}
