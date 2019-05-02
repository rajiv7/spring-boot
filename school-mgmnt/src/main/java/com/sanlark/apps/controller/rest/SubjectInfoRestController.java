/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.controller.rest;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanlark.apps.dao.SubjectRepository;
import com.sanlark.apps.dto.SubjectDto;
import com.sanlark.apps.entity.SubjectInfo;
import com.sanlark.apps.util.SecuriyUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/rest")
public class SubjectInfoRestController {
	@Autowired
	private SubjectRepository repo;
	
	@GetMapping({"/subject", "/subject.html", "/subject/"})
    public ResponseEntity<List<SubjectDto>> getAll() {
        log.info("Serving for get all subject");
        
        List<SubjectDto> resultCol = repo.findAll()
        		.stream()
        		.map(entity -> convert(entity))
        		.collect(Collectors.toList());
        
        return new ResponseEntity<>(resultCol, HttpStatus.OK);
    }
	
	@PostMapping({"/subject", "/subject.html", "/subject/"})
	public SubjectDto createEmployee(@Valid @RequestBody SubjectInfo entity) {
		setCreatedBy(entity);
		SubjectInfo createdEntity = repo.save(entity);
		return convert(createdEntity);
	}
	
	@GetMapping({"/subject/{subjectRefId}", 
		"/subject/{subjectRefId}/", "/subject/{subjectRefId}.html"})
    public ResponseEntity<SubjectDto> get(
    		@PathVariable final String subjectRefId){
        log.info("Serving for get enterprise : " + subjectRefId);
        Long recordId = SecuriyUtil.decryptId(subjectRefId);
        
        SubjectInfo entity = repo.findById(recordId).orElseThrow(() -> new EntityNotFoundException(subjectRefId));
        return new ResponseEntity<>(convert(entity), HttpStatus.OK);
    }

	@PutMapping({"/subject/{subjectRefId}", 
		"/subject/{subjectRefId}/", "/subject/{subjectRefId}.html"})
	public ResponseEntity<SubjectDto> updateEmployee(@PathVariable final String subjectRefId,
			@Valid @RequestBody SubjectInfo inputObj){
		
		Long recordId = SecuriyUtil.decryptId(subjectRefId);
		
		SubjectInfo entity = repo.findById(recordId)
				.orElseThrow(() -> new EntityNotFoundException(subjectRefId));
		
		entity.setName(inputObj.getName());
		entity.setShortName(inputObj.getShortName());
		entity.setRemarks(inputObj.getRemarks());
		
		setModifiedBy(entity);
		
		SubjectDto dtoEntity = convert(repo.save(entity));
		return ResponseEntity.ok(dtoEntity);
	}

	private void setCreatedBy(SubjectInfo entity) {
		entity.setCreatedAt(Instant.now());
		entity.setCreatedById(100l);
	}
	
	private void setModifiedBy(SubjectInfo entity) {
		entity.setModifiedAt(Instant.now());
		entity.setModifiedById(100l);
	}
	
	private SubjectDto convert(SubjectInfo entity) {
		SubjectDto dto = new SubjectDto();
		BeanUtils.copyProperties(entity, dto);
		dto.setRefId(SecuriyUtil.encryptId(entity.getRecordId()));
    	return dto;
	}
}