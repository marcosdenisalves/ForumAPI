package br.com.alura.forum.modelo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.modelo.dtos.TopicoDTO;
import br.com.alura.forum.repositories.TopicoRepository;

@RestController
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;
	
	@RequestMapping("/topicos")
	public List<TopicoDTO> lista(String nomeCurso) {
		if(nomeCurso == null) {
			List<Topico> list = topicoRepository.findAll();
			return TopicoDTO.converter(list);
		} else {
			List<Topico> list = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDTO.converter(list);
		}
	}
}
