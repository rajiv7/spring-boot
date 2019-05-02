/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanlark.apps.entity.SubjectInfo;

public interface SubjectRepository extends JpaRepository<SubjectInfo, Long>{

}