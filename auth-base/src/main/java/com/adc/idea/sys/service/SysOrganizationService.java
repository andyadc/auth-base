package com.adc.idea.sys.service;

import java.util.List;

import com.adc.idea.sys.entity.SysOrganization;

public interface SysOrganizationService {

	List<SysOrganization> selectAll(SysOrganization organization);
	
}
