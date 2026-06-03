package com.nutrispace.model;

import com.nutrispace.model.CondicoesIdeais;

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
@Table(name = "TB_NS_PLANTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ns_planta")
	@SequenceGenerator(name = "seq_ns_planta", sequenceName = "SEQ_NS_PLANTA", allocationSize = 1)
	@Column(name = "ID_PLANTA")
	private Long idPlanta;

	@Column(name = "NOME_PLANTA", length = 80)
	private String nomePlanta;

	@Embedded
	private CondicoesIdeais condicoesIdeais = new CondicoesIdeais();

	public Double getTempMinIdeal() {
		return condicoesIdeais != null ? condicoesIdeais.getTempMinIdeal() : null;
	}

	public Double getTempMaxIdeal() {
		return condicoesIdeais != null ? condicoesIdeais.getTempMaxIdeal() : null;
	}

	public Double getUmiMinIdeal() {
		return condicoesIdeais != null ? condicoesIdeais.getUmiMinIdeal() : null;
	}

	public void setTempMinIdeal(Double value) {
		if (condicoesIdeais == null) {
			condicoesIdeais = new CondicoesIdeais();
		}
		condicoesIdeais.setTempMinIdeal(value);
	}

	public void setTempMaxIdeal(Double value) {
		if (condicoesIdeais == null) {
			condicoesIdeais = new CondicoesIdeais();
		}
		condicoesIdeais.setTempMaxIdeal(value);
	}

	public void setUmiMinIdeal(Double value) {
		if (condicoesIdeais == null) {
			condicoesIdeais = new CondicoesIdeais();
		}
		condicoesIdeais.setUmiMinIdeal(value);
	}
}
