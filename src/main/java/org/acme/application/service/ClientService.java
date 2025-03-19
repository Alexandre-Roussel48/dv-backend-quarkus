package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.ClientDTO;
import org.acme.application.dto.IdClientDTO;
import org.acme.domain.model.Client;
import org.acme.domain.repository.ClientRepository;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public void createClient(ClientDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setSurname(dto.getSurname());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setAddress(dto.getAddress());
        clientRepository.persist(client);
    }

    @Transactional
    public void updateClient(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id);
        if (client != null) {
            client.setName(dto.getName() != null ? dto.getName() : client.getName());
            client.setSurname(dto.getSurname() != null ? dto.getSurname() : client.getSurname());
            client.setEmail(dto.getEmail() != null ? dto.getEmail() : client.getEmail());
            client.setPhoneNumber(dto.getPhoneNumber() != null ? dto.getPhoneNumber() : client.getPhoneNumber());
            client.setAddress(dto.getAddress() != null ? dto.getAddress() : client.getAddress());
        } else {
            throw new NotFoundException("Client with ID " + id + " not found.");
        }
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.deleteById(id)) {
            throw new NotFoundException("Client with ID " + id + " not found.");
        }
    }

    public IdClientDTO getClient(Long id) {
        Client client = clientRepository.findById(id);
        return getIdClientDTO(client);
    }

    public List<IdClientDTO> getClients(String email) {
        return clientRepository.findByEmail(email)
                .stream()
                .map(this::getIdClientDTO).toList();
    }

    private IdClientDTO getIdClientDTO(Client client) {
        IdClientDTO dto = new IdClientDTO();
        dto.setId(client.id);
        dto.setName(client.getName());
        dto.setSurname(client.getSurname());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setAddress(client.getAddress());
        return dto;
    }
}
