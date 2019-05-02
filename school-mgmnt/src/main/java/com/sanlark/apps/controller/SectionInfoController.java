/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sanlark.apps.dao.SectionRepository;
import com.sanlark.apps.dto.GenericDto;
import com.sanlark.apps.dto.SubjectDto;
import com.sanlark.apps.entity.SectionInfo;
import com.sanlark.apps.util.SecuriyUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SectionInfoController {
	@Autowired
	private SectionRepository repo;
	
	@GetMapping({"/section", "/section.html", "/section/"})
    public String getAll(ModelMap model) {
        log.info("Serving for get all section");
        
        List<GenericDto> resultCol = repo.findAll()
        		.stream()
        		.map(entity -> convert(entity, null))
        		.collect(Collectors.toList());
        
        model.addAttribute("resultCol", resultCol);
		return "manage-section";
    }
	
	@GetMapping({"/section/create", "/section/create/", "/section/create.html"})
	public String create(ModelMap model) {
		GenericDto entity = new GenericDto();
		entity.setRefId("create");
		model.addAttribute("entity", entity);
		model.addAttribute("isNewRecord", true);
		return "section-info";
	}
	
	@PostMapping({"/section/create", "/section/create/", "/section/create.html"})
	public String create(@Valid GenericDto dto, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			log.warn("has errors");
			return "section-info";
		}
		SectionInfo entity = create(dto);
		entity.setCreatedById(ADMIN_REF_ID);
		repo.save(entity);
		return "redirect:/section.html";
	}
	
	@GetMapping({"/section/{entityRefId}", "/section/{entityRefId}/", "/section/{entityRefId}.html"})
	public String get(@PathVariable final String entityRefId, ModelMap model) {
		Long recordId = SecuriyUtil.decryptId(entityRefId);
		SectionInfo entity = repo.findById(recordId).get();
		model.addAttribute("entity", convert(entity, entityRefId));
		return "section-info";
	}
	
	@PostMapping({"/section/{entityRefId}", "/section/{entityRefId}/", "/section/{entityRefId}.html"})
	public String updateEmployee(@PathVariable final String entityRefId,
			@Valid SubjectDto dto, BindingResult result, ModelMap model){
		if(result.hasErrors()) {
			log.warn("has errors");
			return "section-info";
		}
		Long recordId = SecuriyUtil.decryptId(entityRefId);
		
		SectionInfo entity = repo.findById(recordId).get();
		entity.setModifiedById(ADMIN_REF_ID);
		//entity.setName(dto.getName());
		entity.setRemarks(dto.getRemarks());
		repo.save(entity);
		return "redirect:/section.html";
	}
	
	private Long ADMIN_REF_ID = 100L;
	
	private GenericDto convert(SectionInfo entity, String encryptedRefId) {
		GenericDto dto = new GenericDto();
		BeanUtils.copyProperties(entity, dto);
		if(encryptedRefId == null) {
			encryptedRefId = SecuriyUtil.encryptId(entity.getRecordId());
		}
		dto.setRefId(encryptedRefId);
    	return dto;
	}
	
	private SectionInfo create(GenericDto dto) {
		SectionInfo entity = new SectionInfo();
		BeanUtils.copyProperties(dto, entity);
		if(dto.getRefId() == null) {
			entity.setCreatedById(ADMIN_REF_ID);
		}
    	return entity;
	}
}