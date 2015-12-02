package com.adc.idea.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adc.idea.sys.entity.SysOrganization;
import com.adc.idea.sys.mapper.SysOrganizationMapper;
import com.adc.idea.sys.service.SysOrganizationService;

@Service
public class SysOrganizationServiceImpl implements SysOrganizationService {
	
	@Autowired
	private SysOrganizationMapper organizationMapper;

	@Override
	public List<SysOrganization> selectAll(SysOrganization organization) {
		return organizationMapper.selectList(organization);
	}

}
