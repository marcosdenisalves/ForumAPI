package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.modelo.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {
	
	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2) //Tipo de documentaçãp
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum")) //Apartir de qual pacote ele irar lê as classes do projeto
				.paths(PathSelectors.ant("/**")) //Passando quais paths o SpringFox vai fazer uma analise
				.build()
				.ignoredParameterTypes(Usuario.class) //Ignora todas as urls que trabalham com a classe usuario
				.globalOperationParameters(Arrays.asList( //Adiciona parametros globais, este parametro ira ser apresentado em todos os endpoints do swagger-ui
						new ParameterBuilder() //informações do parametro do header
						.name("Authorization")
						.description("Header para token JWT")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()));
	}
	
}
