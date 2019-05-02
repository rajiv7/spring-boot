/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 01-May-2019
 */
package com.sanlark.apps.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericDto {
	private String refId;
	private String name;
	private String remarks;
	private int statusId;
}