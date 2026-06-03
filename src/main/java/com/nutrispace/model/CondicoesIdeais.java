package com.nutrispace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CondicoesIdeais {

	@Column(name = "TEMP_MIN_IDEAL")
	private Double tempMinIdeal;

	@Column(name = "TEMP_MAX_IDEAL")
	private Double tempMaxIdeal;

	@Column(name = "UMI_MIN_IDEAL")
	private Double umiMinIdeal;
}
