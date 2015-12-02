package com.adc.idea.common.page;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.adc.idea.common.Constants;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@SuppressWarnings("rawtypes")
@Repository
public class PageHelper extends SqlSessionDaoSupport {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	public PageModel getPage(String querySqlId, Map<String, ? extends Object> params, PageModel pageModel) {
		try {
			List list = this.getSqlSession().selectList(querySqlId, params,
					new PageBounds(pageModel.getPageNo(), pageModel.getPageSize()));
			pageModel.setList(list);
			PageList pageList = (PageList) list;
			pageModel.setTotalRecord(pageList.getPaginator().getTotalCount());

			return pageModel;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getPage error " + e);
			return null;
		}
	}

	public PageModel getPage(String querySqlId, Map<String, ? extends Object> params) {
		try {
			String pageNo = StringUtils.defaultIfEmpty((String) params.get(Constants.PAGE_NO), "1");// 当前页数
			String pageSize = StringUtils.defaultIfEmpty((String) params.get(Constants.PAGE_SIZE), "10");// 每页条数
			logger.info("pageNo:" + pageNo + ", pageSize:" + pageSize);
			
			PageModel pageModel = new PageModel(Integer.parseInt(pageNo));
			pageModel.setPageSize(Integer.parseInt(pageSize));

			return this.getPage(querySqlId, params, pageModel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getPage error " + e);
			return null;
		}
	}

}
