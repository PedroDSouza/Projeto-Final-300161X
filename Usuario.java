package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import database.DBQuery;
import mail.SendMail;

public class Usuario {
	private int	   	Codconta;
	private String 	cpf
	private String 	nome;
	private int 	tipoconta;
	
	private String tableName	= "LOJA"; 
	private String fieldsName	= "Codconta, cpf, nome, tipoconta";  
	private String fieldKey		= "Codconta";
	
	private DBQuery dbQuery = new DBQuery(tableName, fieldsName, fieldKey);
	
	public Usuario() {
		
	}
	
	public Usuario( int Codconta, String cpf, int tipoconta, String nome) {
		this.setIdUsuario(Codconta);
		this.setNome(nome);
		this.setIdNivelUsuario(tipoconta;
		this.setCpf(cpf);
		
	}
	
	
	public String toJson() {
		return(
				"{\n\t"+
						"'Código da Conta':'"+this.getCodconta() + ""+"',\n\t"+
						"'Tipo':'"+this.getTipo() + ""+"',\n\t"+
						"'nome':'"+this.getNome()+"',\n\t"+
						"'cpf':'"+this.getCpf()+"',\n\t"+
				"}"
		);
	}	

	public String toString() {
		return(
				this.getCodconta() + ""+" | "+
				this.getTipo() + ""+" | "+
				this.getNome()+" | "+
				this.getCpf()+" | "
		);
	}
	
	private String[] toArray() {
		
		String[] temp =  new String[] {
				this.getCodconta() + "",
				this.getTipo() + "",
				this.getNome(),
				this.getCpf(),
				this.getCep(),
		};
		return(temp);
	}
	
	
	
	public void save() {
		if( this.getIdUsuario() > 0 ) {
			this.dbQuery.update(this.toArray());
		}else {
			this.dbQuery.insert(this.toArray());
		}
	}
	
	public void update() {
		if( this.getIdUsuario() > 0 ) {
			this.dbQuery.update(this.toArray2());
		}else {
			System.out.print("Errão");
		}
	}

	
	public void delete(int id) {
		if( id > 0 ) {
			this.dbQuery.delete(this.toArray3());
		}
	}
	
	public ResultSet selectAll() {
		ResultSet resultset = this.dbQuery.select("");
		return(resultset);
	}
	
	public ResultSet select( String where ) {
		ResultSet resultset = this.dbQuery.select(where);
		return(resultset);
	}
	
	public void enviarEmailComSenha( String mailFrom, String mailTo, String mailSubject, String mailBody ){
		
		String smtpHost = "smtp.gmail.com"; 
		String smtpPort = "587"; 
		String username = "teste@gmail.com;
		String password = "Senha@1234";
		String auth     = "TLS";  
		
		SendMail sendMail =  new SendMail( smtpHost,  smtpPort,  username,  password,  auth  );		
		sendMail.send( mailFrom, mailTo, mailSubject, mailBody );
		
	}
	
	
	static String getRandomString() 
    { 
		int i = 10;
        String theAlphaNumericS;
        StringBuilder builder;
        
        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"; 

        //create the StringBuffer
        builder = new StringBuilder(i); 

        for (int m = 0; m < i; m++) { 

            // generate numeric
            int myindex 
                = (int)(theAlphaNumericS.length() 
                        * Math.random()); 

            // add the characters
            builder.append(theAlphaNumericS 
                        .charAt(myindex)); 
        } 

        return builder.toString(); 
    }
	
	public String newPassword() {
		
		if (this.getEmail() != "" && this.getEmail()!= null) {
			if ( this.getIdUsuario() > 0 ) {
				try {
					ResultSet resultset = this.select(" email ='"+this.getEmail()+"'");
					boolean existe = resultset.next();
					if ( existe ) {
//						this.setSenha(  new RandomCode().generate(32) );
						this.save();
						return( this.getSenha());
					}
					resultset.getInt("idUsuario");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} 
		return this.getSenha(); 
	}

	public boolean checkLogin(String psw) {
		
		int id = 0;
		try {
			ResultSet resultSet = this.select(" email='"+ this.getEmail()+ "' AND senha = '"+psw+"'");
			while (resultSet.next()) {
				System.out.println( "\n"+resultSet.getString("nome"));
				id =  resultSet.getInt("Codconta");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.setIdUsuario(id);
		return(id > 0);	
	}
	
	public int getIdUsuario() {
		return Codconta;
	}

	public String listAllHtml() {
		ResultSet rs =  this.dbQuery.select("");
		String saida = "<br>";
		saida += "<table border=1>";
		try {
			while (rs.next()) {
				saida += "<tr>";
		     
				saida += "<td>" + rs.getString("Codconta" ) +  "</td>";
				saida += "<td>" + rs.getString("Tipoconta" ) +  "</td>";
				saida += "<td>" + rs.getString("nome" ) +  "</td>";
				saida += "<td>" + rs.getString("cpf" ) +  "</td>";
				saida += "</tr> <br>";
			}
	   } catch (SQLException e) {
		 e.printStackTrace();
	   }
	   saida += "</table>";
	   return (saida);
	}

	public void setCodconta(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = getRandomString();
	}

	public void setExistsSenha(String senha) {
		this.senha = senha;
	}
	
	public int getTipo) {
		return Tipoconta;
	}

	public void setTipo(int Tipoconta) {
		this.Tipoconta = Tipoconta;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	
}
