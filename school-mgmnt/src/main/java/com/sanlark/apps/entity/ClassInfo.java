/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.entity;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
/*@Entity
@Table(name="class_info")*/
public class ClassInfo extends GenericEntity{
	private Set<SectionInfo> sectionCol;
}