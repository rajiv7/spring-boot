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

import com.sanlark.apps.dao.SubjectRepository;
import com.sanlark.apps.dto.SubjectDto;
import com.sanlark.apps.entity.SubjectInfo;
import com.sanlark.apps.util.SecuriyUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SubjectInfoController {
	@Autowired
	private SubjectRepository repo;
	
	@GetMapping({"/subject", "/subject.html", "/subject/"})
    public String getAll(ModelMap model) {
        log.info("Serving for get all subject");
        
        List<SubjectDto> resultCol = repo.findAll()
        		.stream()
        		.map(entity -> convert(entity, null))
        		.collect(Collectors.toList());
        
        model.addAttribute("resultCol", resultCol);
		return "manage-subject";
    }
	
	@GetMapping({"/subject/create", "/subject/create/", "/subject/create.html"})
	public String create(ModelMap model) {
		model.addAttribute("entity", new SubjectDto());
		model.addAttribute("isNewRecord", true);
		return "create-subject-info";
	}
	
	@PostMapping({"/subject/create", "/subject/create/", "/subject/create.html"})
	public String create(@Valid SubjectDto dto, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			log.warn("has errors");
			return "create-subject-info";
		}
		SubjectInfo subject = create(dto);
		subject.setCreatedById(ADMIN_REF_ID);
		repo.save(subject);
		return "redirect:/subject.html";
	}
	
	@GetMapping({"/subject/{entityRefId}", "/subject/{entityRefId}/", "/subject/{entityRefId}.html"})
	public String get(@PathVariable final String entityRefId, ModelMap model) {
		Long recordId = SecuriyUtil.decryptId(entityRefId);
		SubjectInfo entity = repo.findById(recordId).get();
		SubjectDto dto = convert(entity, entityRefId);
		model.addAttribute("entity", dto);
		return "update-subject-info";
	}
	
	@PostMapping({"/subject/{entityRefId}", 
		"/subject/{entityRefId}/", "/subject/{entityRefId}.html"})
	public String updateEmployee(@PathVariable final String entityRefId,
			@Valid SubjectDto dto, BindingResult result, ModelMap model){
		if(result.hasErrors()) {
			log.warn("has errors");
			return "update-subject-info";
		}
		Long recordId = SecuriyUtil.decryptId(entityRefId);
		
		SubjectInfo entity = repo.findById(recordId).get();
		entity.setModifiedById(ADMIN_REF_ID);
		//entity.setName(dto.getName());
		entity.setShortName(dto.getShortName());
		entity.setRemarks(dto.getRemarks());
		repo.save(entity);
		return "redirect:/subject.html";
	}
	
	private Long ADMIN_REF_ID = 100L;
	
	private SubjectDto convert(SubjectInfo entity, String encryptedRefId) {
		SubjectDto dto = new SubjectDto();
		BeanUtils.copyProperties(entity, dto);
		if(encryptedRefId == null) {
			encryptedRefId = SecuriyUtil.encryptId(entity.getRecordId());
		}
		dto.setRefId(encryptedRefId);
    	return dto;
	}
	
	private SubjectInfo create(SubjectDto dto) {
		SubjectInfo entity = new SubjectInfo();
		BeanUtils.copyProperties(dto, entity);
		if(dto.getRefId() == null) {
			entity.setCreatedById(ADMIN_REF_ID);
		}
    	return entity;
	}
}