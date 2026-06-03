package com.nutrispace.model;

import com.nutrispace.model.CapacidadeReservatorio;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_NS_RESERVATORIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservatorio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_reservatorio")
	@SequenceGenerator(name = "seq_ns_reservatorio", sequenceName = "SEQ_NS_RESERVATORIO", allocationSize = 1)
	@Column(name = "ID_RESERVATORIO")
	private Long idReservatorio;

	@Embedded
	private CapacidadeReservatorio capacidade = new CapacidadeReservatorio();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTUFA", nullable = false)
	private Estufa estufa;

	public Double getCapacidadeMaxLitros() {
		return capacidade != null ? capacidade.getCapacidadeMaxLitros() : null;
	}

	public Double getNivelAtualPercentual() {
		return capacidade != null ? capacidade.getNivelAtualPercentual() : null;
	}

	public void setCapacidadeMaxLitros(Double value) {
		if (capacidade == null) {
			capacidade = new CapacidadeReservatorio();
		}
		capacidade.setCapacidadeMaxLitros(value);
	}

	public void setNivelAtualPercentual(Double value) {
		if (capacidade == null) {
			capacidade = new CapacidadeReservatorio();
		}
		capacidade.setNivelAtualPercentual(value);
	}
}
