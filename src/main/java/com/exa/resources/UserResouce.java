package com.exa.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exa.entities.User;
import com.exa.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User", description = "User operations")
public class UserResouce {
    // adicionando uma dependencia (injecao de dependencia, que é um objeto
    // UserService que vira automatico) pra UserServices
    // lembrando que pra isso funcionar, essa classe precisa estar registrada como
    // componente do spring lá no UserService
    @Autowired
    private UserService service;

    // GET - /users
    // GET - Swagger annotations
    @Operation(
        summary = "Retrieve all users",
        description = "Returns a list of all users in the database.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "OK", 
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"João\", \"email\": \"joao@email.com\", \"phone\": \"999999999\", \"password\": \"123456\" }")
                )
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Internal Server Error",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 500, \"error\": \"Internal Server Error\", \"trace\": \"Lorem Ipsum\", \"message\": \"Lorem Ipsum\", \"path\": \"/user\"}")
                )
            )
        }
    )
    // GET - Controller method
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll(); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(list);
    }

    // GET - /users/{id}
    // GET - Swagger annotations
    @Operation(
        summary = "Get user by ID", 
        description = "Retrieve a specific user based on their ID", 
        parameters = {
            @Parameter(name = "id", description = "ID of the user", required = true)
        },
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "OK",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"João\", \"email\": \"joao@email.com\", \"phone\": \"999999999\", \"password\": \"123456\" }")
                )
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not Found",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 404, \"error\": \"Not Found\", \"trace\": \"Lorem Ipsum\", \"message\": \"Lorem Ipsum\", \"path\": \"/user/1\"}")
                )
                ),
            @ApiResponse(
                responseCode = "500", 
                description = "Internal Server Error",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 500, \"error\": \"Internal Server Error\", \"trace\": \"Lorem Ipsum\", \"message\": \"Lorem Ipsum\", \"path\": \"/user/1\"}")
                )    
            )
        }
    )
    // GET - Controller Method
    @GetMapping(value = "/{id}") // indica que essa request aceita um parametro na url
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = service.findById(id); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(obj);
    }

    // POST - /users
    // POST - Swagger annotations
    @Operation(
        summary = "Create a new user",
        description = "Add a new user in the database", 
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object to be created", 
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"name\": \"João\", \"email\": \"joao@email.com\", \"phone\": \"999999999\", \"password\": \"123456\"}")
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Created",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"João\", \"email\": \"joao@email.com\", \"phone\": \"999999999\", \"password\": \"123456\" }")
            )
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal Server Error",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 500, \"error\": \"Internal Server Error\", \"trace\": \"Lorem Ipsum\", \"message\": \"Lorem Ipsum\", \"path\": \"/users\"}")
            )    
        )
    })
    // POST - Controller Method
    @PostMapping
    // Pra dizer que User obj vai ser um objeto json que vai ser enviado no corpo da
    // requisicao, usa-se o @RequestBody
    public ResponseEntity<User> insert(@RequestBody User obj) {
        obj = service.insert(obj); // aqui se usa a dependencia que foi injetada
        // criando o objeto do tipo URI pra retornar o endereco do novo objeto criado e
        // servir de parametro para created()
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    // DELETE - /users/{id}
    // DELETE - Swagger annotations
    @Operation(
        summary = "Delete user by ID",
        description = "Delete a specific user based on their ID",
        parameters = {
            @Parameter(name = "id", description = "ID of the user", required = true)
        }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(
            responseCode = "404", 
            description = "Not Found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Lorem Ipsum\", \"path\": \"/users/1\"}")
            )
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal Server Error",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 500, \"error\": \"Internal Server Error\", \"trace\": \"Lorem Ipsum\", \"message\": \"Lorem Ipsum\", \"path\": \"/users/1\"}")
            )
        )
    })
    // DELETE - Controller Method
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); // aqui se usa a dependencia que foi injetada

        // noContent() retorna uma resposta vazia com o codigo 204
        return ResponseEntity.noContent().build();
    }

    // PUT - /users/{id}
    // PUT - Swagger annotations
    @Operation(
        summary = "Update user by ID",
        description = "Update a specific user based on their ID",
        parameters = {
            @Parameter(name = "id", description = "ID of the user", required = true)
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object to be updated",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"name\": \"João\", \"email\": \"joao@email.com\", \"phone\": \"999999999\", \"password\": \"123456\"}")
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "OK",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"João\", \"email\": \"joao@email.com\", \"phone\": \"999999999\", \"password\": \"123456\" }")
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Not Found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Lorem Ipsum\", \"path\": \"/users/1\"}")
            )
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal Server Error",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"timestamp\": \"2025-02-15T16:24:13.577+00:00\", \"status\": 500, \"error\": \"Internal Server Error\", \"trace\": \"Lorem Ipsum\", \"message\": \"Lorem Ipsum\", \"path\": \"/users/1\"}")
            )   
        )
    })
    // PUT - Controller Method
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj) {
        obj = service.update(id, obj); // aqui se usa a dependencia que foi injetada

        return ResponseEntity.ok().body(obj);
    }
}
