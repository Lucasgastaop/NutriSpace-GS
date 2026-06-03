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
public class MedicaoAmbiental {

	@Column(name = "TEMPERATURA_LIDA")
	private Double temperaturaLida;

	@Column(name = "UMIDADE_LIDA")
	private Double umidadeLida;
}
