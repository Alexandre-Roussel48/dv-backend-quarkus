package org.acme.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.acme.application.dto.GameDTO;
import org.acme.application.dto.IdGameDTO;
import org.acme.domain.model.Game;
import org.acme.domain.repository.GameRepository;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class GameService {
    private final GameRepository gameRepository;

    @Transactional
    public void createGame(GameDTO dto) {
        Game game = new Game();
        game.setName(dto.getName());
        game.setEditor(dto.getEditor());
        gameRepository.persist(game);
    }

    @Transactional
    public void updateGame(Long id, GameDTO dto) {
        Game game = gameRepository.findById(id);
        if (game != null) {
            game.setName(dto.getName() != null ? dto.getName() : game.getName());
            game.setEditor(dto.getEditor() != null ? dto.getEditor() : game.getEditor());
        } else {
            throw new NotFoundException("Game with ID " + id + " not found.");
        }
    }

    @Transactional
    public void deleteGame(Long id) {
        if (!gameRepository.deleteById(id)) {
            throw new NotFoundException("Game with ID " + id + " not found.");
        }
    }

    public List<IdGameDTO> getGames(String query) {
        return gameRepository.findByQuery(query).stream().map(game -> {
            IdGameDTO dto = new IdGameDTO();
            dto.setId(game.id);
            dto.setName(game.getName());
            dto.setEditor(game.getEditor());
            return dto;
        }).toList();
    }
}
