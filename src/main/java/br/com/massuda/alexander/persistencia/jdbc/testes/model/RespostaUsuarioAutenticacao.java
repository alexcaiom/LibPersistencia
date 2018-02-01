package br.com.massuda.alexander.persistencia.jdbc.testes.model;
/**
 * @author Alex
 *
 */
public enum RespostaUsuarioAutenticacao {

	SUCESSO									(0, "Te encontramos!"),
	USUARIO_INEXISTENTE						(1, "Usuário inexistente"),
	SENHA_INVALIDA							(2, "Senha inválida"),
	SENHA_INVALIDA_ULTIMA_TENTATIVA			(3, "Senha inválida.\n Proximo erro ocasionara bloqueio da conta."),
	USUARIO_BLOQUEADO						(4, "Usuário bloqueado"),
	FINAL_DE_SEMANA							(5, "O acesso é restrito aos dias de semana!"),
	FORA_DO_HORARIO_COMERCIAL				(6, "O sistema só pode ser acessado no horário comercial.");
	
	int cod;
	String mensagem;
	
	private RespostaUsuarioAutenticacao(int cod, String mensagem) {
		this.cod = cod;
		this.mensagem = mensagem;
	}

	/**
	 * @return the cod
	 */
	public final int getCod() {
		return cod;
	}

	/**
	 * @return the mensagem
	 */
	public final String getMensagem() {
		return mensagem;
	}	
	
	public static RespostaUsuarioAutenticacao get(int cod){
		for (RespostaUsuarioAutenticacao status : RespostaUsuarioAutenticacao.values()) {
			if (status.getCod() == cod) {
				return status;
			}
		}
		return null;
	}
}