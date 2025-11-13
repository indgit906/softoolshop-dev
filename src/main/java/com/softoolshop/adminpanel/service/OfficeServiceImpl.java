package com.softoolshop.adminpanel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softoolshop.adminpanel.entity.Office;
import com.softoolshop.adminpanel.entity.OfficeHours;
import com.softoolshop.adminpanel.repository.OfficeHoursRepository;
import com.softoolshop.adminpanel.repository.OfficeRepository;

@Service
public class OfficeServiceImpl implements OfficeService {
	
	@Autowired
	private OfficeRepository officeRepo;
	
	@Autowired
	private OfficeHoursRepository officeHrsRepo;

	@Override
	public Map<String, Object> getOfficeById(Integer officeId) {
		Optional<OfficeHours> officeHrsOpt = officeHrsRepo.findById(1);
		
		OfficeHours officeHr =	officeHrsOpt.get();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("office", officeRepo.findById(officeId).get());
		map.put("officeHours", officeHr);
		return map;
	}

	
}
