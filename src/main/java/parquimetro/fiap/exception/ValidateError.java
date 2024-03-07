package parquimetro.fiap.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidateError extends StandartError{

    private List<ValidateMessage> mensagens = new ArrayList<ValidateMessage>();

    public List<ValidateMessage> getMensagens() {
        return mensagens;
    }

    public void addMensagens(String campo, String mensagem){
        mensagens.add(new ValidateMessage(campo, mensagem));
    }
}
