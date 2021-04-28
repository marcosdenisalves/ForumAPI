package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfigurtations extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TokenService tokenService;
	
	@Bean
	@Override //Disponibiliza uma instancia dessa classe para o spring
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	@Override //Configuracoes de autenticacao
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService) //passar classe com a logica de autenticacao
		.passwordEncoder(new BCryptPasswordEncoder()); //gera senha criptografada
	}
	
	@Override //Configuracoes de autorização
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll() //Permite listar todos
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll() //Permite busca por parametros
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.anyRequest().authenticated() //Para indicar que outras URLs que não foram configuradas devem ter acesso restrito
			.and().csrf().disable() //Disabilita protecao contra ataques csrf
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Disabilita criacao de sessao
			.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class); //Diz para o spring qual filtro ele deve adicionar e antes de qual filtro
	}
	
	@Override //Configuracoes de recursos estaticos(js, css, imagens, e etc..)
	public void configure(WebSecurity web) throws Exception {
	}
}