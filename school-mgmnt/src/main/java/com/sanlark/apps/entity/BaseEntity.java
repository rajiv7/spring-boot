/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.entity;

import java.time.Instant;

import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
	private int statusId;
	
	private Instant createdAt;
	private Instant modifiedAt;
	
	private Long createdById;
	private Long modifiedById;
}