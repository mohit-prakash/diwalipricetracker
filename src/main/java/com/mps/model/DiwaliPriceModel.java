package com.mps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiwaliPriceModel {
	private String modelName;
	private String mrp;
	private String dp;
	private String srp;
}
