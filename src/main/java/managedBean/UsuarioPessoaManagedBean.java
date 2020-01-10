package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;

import dao.DaoGeneric;
import dao.DaoUsuario;
import model.UsuarioPessoa;

@ManagedBean(name = "usuarioPessoaManagedBean")
@ViewScoped
public class UsuarioPessoaManagedBean {

	private UsuarioPessoa usuarioPessoa = new UsuarioPessoa();
	private DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
	private DaoUsuario daoUsuario=new DaoUsuario();
	private List<UsuarioPessoa> list = new ArrayList<UsuarioPessoa>();

	@PostConstruct
	public void init() {
		list = daoGeneric.listar(UsuarioPessoa.class);
	}
	
	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	public void setUsuarioPessoa(UsuarioPessoa usuarioPessoa) {
		this.usuarioPessoa = usuarioPessoa;
	}
	
	public String salvar(){
		 daoGeneric.salvar(usuarioPessoa);
		 list.add(usuarioPessoa);
		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sucesso", "Salvo com sucesso"));
		 usuarioPessoa = new UsuarioPessoa();
		return "";
	}
	
	public String novo(){
		 usuarioPessoa = new UsuarioPessoa();
		 return "";
	}
	
	
	public List<UsuarioPessoa> getList() {		
		return list;
	}
	
	public String remover(){
		try {
			daoUsuario.removerUsuario(usuarioPessoa);
			list.remove(usuarioPessoa); 
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Sucesso", "Removido com sucesso"));
			usuarioPessoa = new UsuarioPessoa();
		} catch (Exception e) {
			if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "Este usuário possui telefones vinculados!"));
			}		
		}
		
		return "";
	}
	

}
