package com.jerry.domainservice.api.model;

import java.util.Objects;

import lombok.Data;

@Data
public class DomainInformationDto {

	/**
	 * Domain name
	 */
	private String domainName;
	
	/**
	 * Time in milliseconds of getting domain name by short domain information.
	 */
	private Long lastUpdateTime;
	
	/**
	 * Position of the domain information in a list.
	 */
	private Integer position;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainInformationDto other = (DomainInformationDto) obj;
		return Objects.equals(domainName, other.domainName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(domainName);
	}
	
	
}
