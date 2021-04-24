package br.com.alura.forum.modelo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.modelo.dto.TopicoDTO;
import br.com.alura.forum.modelo.dto.TopicoNewDTO;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso) {
		if(nomeCurso == null) {
			List<Topico> list = topicoRepository.findAll();
			return TopicoDTO.converter(list);
		} else {
			List<Topico> list = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDTO.converter(list);
		}
	}
	
	@PostMapping
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody TopicoNewDTO newDto,
			UriComponentsBuilder uriBuilder) {
		Topico topico = newDto.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}")
				.buildAndExpand(topico.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDTO(topico)); 
	}
}
