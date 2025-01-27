package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;

public class DesontoTest {
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		Lugar	lugar	=	new	Lugar("A",1);
		Sala sala = new Sala("Eldorado", new BigDecimal("20.5"));
		Filme filme = new Filme("Rougue One",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("12"));
		
		Sessao sessao = new Sessao(LocalTime.parse("10:00"),filme,sala);
		Ingresso ingresso = new Ingresso(sessao,TipoDeIngresso.INTEIRO,lugar);
		
		BigDecimal precoEsperado = new BigDecimal("32.50");
		Assert.assertEquals(precoEsperado, ingresso.getPreco());

	}
	
	@Test
	public void deveConcederDesconto30PorCentoParaIngressoBanco() {
		Lugar	lugar	=	new	Lugar("A",1);
		Sala sala = new Sala("Eldorado", new BigDecimal("20.5"));
		Filme filme = new Filme("Rougue One",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("12"));
		
		Sessao sessao = new Sessao(LocalTime.parse("10:00"),filme,sala);
		Ingresso ingresso = new Ingresso(sessao,TipoDeIngresso.BANCO,lugar);
		
		BigDecimal precoEsperado = new BigDecimal("22.75");
		Assert.assertEquals(precoEsperado, ingresso.getPreco());

	}
	@Test
	public void deveConcederDesconto50PorCentoParaEstudantes() {
		Sala sala = new Sala("Eldorado", new BigDecimal("20.5"));
		Lugar	lugar	=	new	Lugar("A",1);
		Filme filme = new Filme("Rougue One",Duration.ofMinutes(120),"SCI-FI",new BigDecimal("12"));
		
		Sessao sessao = new Sessao(LocalTime.parse("10:00"),filme,sala);
		Ingresso ingresso = new Ingresso(sessao,TipoDeIngresso.ESTUDANTE,lugar);
		
		BigDecimal precoEsperado = new BigDecimal("16.25");
		Assert.assertEquals(precoEsperado, ingresso.getPreco());

	}
}
