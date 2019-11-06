package br.com.caelum.ingresso.model;

import java.time.LocalTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;


@Entity
public class Sessao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalTime horario;
	@ManyToOne
	private Sala sala;
	
	@ManyToOne
	private Filme fime;

	public Sessao(LocalTime horario, Filme filme, Sala sala) {
		this.horario = horario;
		this.fime = filme;
		this.sala = sala;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public Filme getFime() {
		return fime;
	}

	public void setFime(Filme fime) {
		this.fime = fime;
	}

}
