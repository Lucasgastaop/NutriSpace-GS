package com.nutrispace.model;

import java.time.LocalDateTime;

import com.nutrispace.model.RegistroVinculadoEstufa;
import com.nutrispace.model.MedicaoAmbiental;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_NS_LEITURA_SENSOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeituraSensor extends RegistroVinculadoEstufa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_leitor")
	@SequenceGenerator(name = "seq_ns_leitor", sequenceName = "SEQ_NS_LEITOR", allocationSize = 1)
	@Column(name = "ID_LEITOR")
	private Long idLeitor;

	@Embedded
	private MedicaoAmbiental medicao = new MedicaoAmbiental();

	@Column(name = "DT_HR_LEITURA")
	private LocalDateTime dtHrLeitura;

	public Double getTemperaturaLida() {
		return medicao != null ? medicao.getTemperaturaLida() : null;
	}

	public Double getUmidadeLida() {
		return medicao != null ? medicao.getUmidadeLida() : null;
	}

	public void setTemperaturaLida(Double value) {
		if (medicao == null) {
			medicao = new MedicaoAmbiental();
		}
		medicao.setTemperaturaLida(value);
	}

	public void setUmidadeLida(Double value) {
		if (medicao == null) {
			medicao = new MedicaoAmbiental();
		}
		medicao.setUmidadeLida(value);
	}
}
