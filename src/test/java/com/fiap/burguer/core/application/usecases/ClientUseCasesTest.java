package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.Exception.InvalidCPFException;
import com.fiap.burguer.core.application.Exception.InvalidEmailException;
import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.ClientPort;
import com.fiap.burguer.core.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientUseCasesTest {
    @Mock
    private ClientPort clientPort;

    @InjectMocks
    private ClientUseCases clientUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClientOrUpdate_ValidData_Success() {
        Client client = new Client();
        client.setCpf("859.481.100-44");
        client.setEmail("valid@example.com");

        when(clientPort.save(client)).thenReturn(client);

        Client result = clientUseCases.saveClientOrUpdate(client);

        assertNotNull(result);
        assertEquals(client, result);
        verify(clientPort).save(client);
    }

    @Test
    void saveClientOrUpdate_InvalidCPF_ThrowsException() {
        Client client = new Client();
        client.setCpf("123.456.789-00");
        client.setEmail("valid@example.com");

        InvalidCPFException thrown = assertThrows(InvalidCPFException.class, () -> {
            clientUseCases.saveClientOrUpdate(client);
        });

        assertEquals("CPF '12345678900' inválido!", thrown.getMessage());
    }

    @Test
    void saveClientOrUpdate_InvalidEmail_ThrowsException() {
        Client client = new Client();
        client.setCpf("859.481.100-44");
        client.setEmail("invalid-email");

        InvalidEmailException thrown = assertThrows(InvalidEmailException.class, () -> {
            clientUseCases.saveClientOrUpdate(client);
        });

        assertEquals("E-mail 'invalid-email' inválido!", thrown.getMessage());
    }

    @Test
    void findById_ExistingClient_ReturnsClient() {
        Client client = new Client();
        client.setId(1);

        when(clientPort.findById(1)).thenReturn(client);

        Client result = clientUseCases.findById(1);

        assertNotNull(result);
        assertEquals(client, result);
        verify(clientPort).findById(1);
    }

    @Test
    void findById_NonExistingClient_ThrowsException() {
        when(clientPort.findById(1)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            clientUseCases.findById(1);
        });

        assertEquals("Cliente não encontrado", thrown.getMessage());
    }

    @Test
    void findByCpf_ExistingClient_ReturnsClient() {
        Client client = new Client();
        client.setCpf("85948110044");

        when(clientPort.findByCpf("85948110044")).thenReturn(client);

        Client result = clientUseCases.findByCpf("85948110044");

        assertNotNull(result);
        assertEquals(client, result);
        verify(clientPort).findByCpf("85948110044");
    }

    @Test
    void findByCpf_InvalidCPF_ThrowsException() {
        String invalidCpf = "123.456.789-XX";

        InvalidCPFException thrown = assertThrows(InvalidCPFException.class, () -> {
            clientUseCases.findByCpf(invalidCpf);
        });

        assertEquals("CPF '123456789' inválido!", thrown.getMessage());
    }

    @Test
    void findByCpf_NonExistingClient_ThrowsException() {
        when(clientPort.findByCpf("85948110044")).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            clientUseCases.findByCpf("859.481.100-44");
        });

        assertEquals("Cliente não encontrado a partir de CPF", thrown.getMessage());
    }

    @Test
    void deleteById_ExistingClient_Success() {
        when(clientPort.findById(1)).thenReturn(new Client());

        clientUseCases.deleteById(1);

        verify(clientPort).deleteById(1);
    }

    @Test
    void deleteById_NonExistingClient_ThrowsException() {
        when(clientPort.findById(1)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            clientUseCases.deleteById(1);
        });

        assertEquals("Cliente não encontrado", thrown.getMessage());
    }
}
