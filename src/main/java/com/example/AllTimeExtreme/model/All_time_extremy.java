package com.example.AllTimeExtreme.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class All_time_extremy {

	@Id
	private Integer id_cenneho_papiera;
	private Double all_time_low;
	private Double all_time_high;
}
