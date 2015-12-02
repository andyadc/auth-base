package com.adc.idea.sys.task;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.adc.idea.common.utils.JodaDateUtils;

@Service("sysTaskScheduler")
public class SysTaskScheduler {

	public void taskTest() {
		System.out.println("----------------" + JodaDateUtils.date2Str(new Date(), JodaDateUtils.DEFAULT_FORMAT));
	}

}
