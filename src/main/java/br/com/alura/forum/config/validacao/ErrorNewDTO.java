package br.com.alura.forum.config.validacao;

public class ErrorNewDTO {

	private String campo;
	private String erro;

	public ErrorNewDTO(String campo, String erro) {
		this.campo = campo;
		this.erro = erro;
	}

	public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}
}
