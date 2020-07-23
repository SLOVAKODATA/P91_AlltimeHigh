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
public class Cenny_Papier {

	@Id
	private Integer id_cenneho_papiera;
	private Integer zobrazovat;
	private Integer stahovat;
}
