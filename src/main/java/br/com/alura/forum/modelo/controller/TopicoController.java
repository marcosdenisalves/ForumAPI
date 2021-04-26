package br.com.alura.forum.modelo.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.modelo.dto.AtualizarTopicoDTO;
import br.com.alura.forum.modelo.dto.DetalhamentoTopicoDTO;
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
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, 
			@PageableDefault(sort= "id", direction = Direction.DESC,
			page = 0, size = 5)  Pageable paginacao) {

		if(nomeCurso == null) {
			Page<Topico> list = topicoRepository.findAll(paginacao);
			return TopicoDTO.converter(list);
		} else {
			Page<Topico> list = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDTO.converter(list);
		}
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoNewDTO newDto,
			UriComponentsBuilder uriBuilder) {
		Topico topico = newDto.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}")
				.buildAndExpand(topico.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDTO(topico)); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhamentoTopicoDTO> detalhar(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent())
			return ResponseEntity.ok(new DetalhamentoTopicoDTO(optional.get()));

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, 
			@RequestBody @Valid AtualizarTopicoDTO atualizaTopicoDto) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = atualizaTopicoDto.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDTO(topico));
		}

		return ResponseEntity.notFound().build();
 	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}