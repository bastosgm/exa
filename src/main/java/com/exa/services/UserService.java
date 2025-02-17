package com.exa.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.exa.entities.User;
import com.exa.repositories.UserRepository;
import com.exa.services.exceptions.DataBaseException;
import com.exa.services.exceptions.ResourceNotFoundException;

import org.slf4j.Logger;

import jakarta.persistence.EntityNotFoundException;

@Service // @Component serviria para registrar a classe como componente do Spring, porem
         // existem os especificos pra services, repositories e etc
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        logger.info("Fetching all users");
        return repository.findAll(); // simplesmente repassa a chamada pra camada de repository
    }

    public User findById(Long id) {
        logger.info("Finding user with ID: {}", id);
        // Optional é um container que pode ou nao conter um valor nao nulo
        // retorna um Optional, entao se usa o get pra pegar o objeto
        Optional<User> obj = repository.findById(id);
        // return obj.get(); esse agora da espaço pra um tratamento de excecao
        User user = obj.orElseThrow(() -> new ResourceNotFoundException(id));
        logger.info("User found with ID: {}", id);

        return user;
    }

    // metodo pra inserir um novo usuario no db
    public User insert(User obj) {
        logger.info("Adding a new user");
        return repository.save(obj);
    }

    // metodo pra deletar um usuario
    public void delete(Long id) {
        // precisa agora manualmente fazer isso pois o JPA nao lança mais exception caso o id nao exista
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(1);
        }
        
        try {
            repository.deleteById(id);
        // aqui vai o tipo mais genérico de exceção, pra pegar qualquer exceção que o deleteById possa lançar e printar o erro especifico que voce quer pegar pra depois colocar aqui no lugar do RuntimeException
        // } catch (RuntimeException e) {
        // a maneira usada aqui pra ir tratando excecoes é imaginar cenarios de erro e ir tratando um por um, como este primeiro que é caso alguem tente deletar um usuario que nao existe
        } catch (EmptyResultDataAccessException e) {
            // printStackTrace apenas para mostrar o erro no console
            throw new ResourceNotFoundException(id);
        // este seria o proximo cenario de erro, caso o usuario tenha alguma dependencia que nao pode ser deletada, testando primeiro sempre com:
        // } catch (RuntimeException e) {
        //     e.printStackTrace();
        // }
        // e depois de pegar o erro especifico, colocar aqui no lugar do RuntimeException e lançar o erro próprio criado
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    // metodo pra atualizar um usuario
    public User update(Long id, User obj) {
        try {
            logger.info("Updating user with ID: {}", id);
            // getReferenceById instancia um objeto provisorio monitorado sem acessar o db
            User entity = repository.getReferenceById(id);
    
            // atualiza os dados do entity com os dados do obj
            updateData(entity, obj);

            // salva o entity atualizado no db
            User updatedUser = repository.save(entity);
            logger.info("User updated successfully: {}", updatedUser);
    
            return updatedUser;         
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    // metodo auxiliar pra atualizar os dados do entity com os dados do obj
    private void updateData(User entity, User obj) {
        if (obj.getName() != null) entity.setName(obj.getName());
        if (obj.getEmail() != null) entity.setEmail(obj.getEmail());
        if (obj.getPhone() != null) entity.setPhone(obj.getPhone());
    }    
}
