package br.com.alura.forum.modelo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.modelo.dtos.TopicoDTO;

@RestController
public class TopicoController {

	@RequestMapping("/topicos")
	public List<TopicoDTO> lista() {
		Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		return TopicoDTO.converter(Arrays.asList(topico, topico, topico));
	}
}
