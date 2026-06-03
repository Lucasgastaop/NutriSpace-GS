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
public class CapacidadeReservatorio {

	@Column(name = "CAPACIDADE_MAX_LITROS")
	private Double capacidadeMaxLitros;

	@Column(name = "NIVEL_ATUAL_PERCENTUAL")
	private Double nivelAtualPercentual;
}
