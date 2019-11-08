package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingersso.validacao.GerenciadorDeSessao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTeste {
	private Filme rogueOne;
	private Sala sala3D;
	private Sessao sessaoDasDez;
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasDezoito;

	@Before
	public void preparaSesssoes() {
		this.rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		this.sala3D = new Sala("Sala 3D", BigDecimal.TEN);

		this.sessaoDasDez = new Sessao(LocalTime.parse("10:00"), rogueOne, sala3D);
		this.sessaoDasTreze = new Sessao(LocalTime.parse("13:00"), rogueOne, sala3D);
		this.sessaoDasDezoito = new Sessao(LocalTime.parse("18:00"), rogueOne, sala3D);
	}

	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessaoDasDez));
	}

	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().minusHours(1), rogueOne, sala3D);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoesDaSala = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().plusHours(1), rogueOne, sala3D);

		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);

		Assert.assertTrue(gerenciador.cabe(sessaoDasTreze));
	}

	@Test
	public void garanteQueNaoDevePermitirUmaSessaoQueTerminaNoProximoDia() {
		List<Sessao> sessoes = Collections.emptyList();
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessaoQueTerminaAmanhaAmanha = new Sessao(LocalTime.parse("23:00"), rogueOne, sala3D);
		Assert.assertFalse(gerenciador.cabe(sessaoQueTerminaAmanhaAmanha));
	}

	@Test
	public void oPrecoDaSessaoDeveSerIgualASomaDoPrecoDaSalaMaisOPrecoDoFilme() {

		Sala sala = new Sala("Eldorado", new BigDecimal("22.5"));
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12.0"));
		BigDecimal somaDosPrecosDaSalaFilme = sala.getPreco().add(filme.getPreco());

		Sessao sessao = new Sessao(LocalTime.parse("10:00"), filme, sala);

		Assert.assertEquals(somaDosPrecosDaSalaFilme, sessao.getPreco());
	}

}
