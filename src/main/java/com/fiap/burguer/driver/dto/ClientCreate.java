package com.fiap.burguer.driver.dto;

public class ClientCreate {

    private String name;
    private String email;
    private String cpf;

    public ClientCreate() {
    }

    public ClientCreate(String name, String email, String cpf) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    public String getNameClient() {
        return name;
    }

    public void setNameClient(String name) {
        this.name = name;
    }

    public String getEmailClient() {
        return email;
    }

    public void setEmailClient(String email) {
        this.email = email;
    }

    public String getCpfClient() {
        return cpf;
    }

    public void setCpfClient(String cpf) {
        this.cpf = cpf;
    }
}
