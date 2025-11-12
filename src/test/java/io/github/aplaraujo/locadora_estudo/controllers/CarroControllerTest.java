package io.github.aplaraujo.locadora_estudo.controllers;

import io.github.aplaraujo.locadora_estudo.entities.CarroEntity;
import io.github.aplaraujo.locadora_estudo.exceptions.EntityNotFoundException;
import io.github.aplaraujo.locadora_estudo.services.CarroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarroController.class)
class CarroControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CarroService carroService;

    @Test
    void deveSalvarUmCarro() throws Exception{
        CarroEntity carro = new CarroEntity(1L, "Gol", 80.00, 2026);
        when(carroService.salvar(any())).thenReturn(carro);

        String json = """
                {
                  "modelo": "Gol",
                  "valorDiaria": 80.00,
                  "ano": 2026
                }
                """;
        var resultActions = mockMvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Gol"));
    }

    @Test
    void obterDetalhesCarro() throws Exception {
        when(carroService.buscarPorId(any())).thenReturn(new CarroEntity(1L, "Sedan", 100.00, 2024));

        mockMvc.perform(
                get("/carros/1")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Sedan"))
                .andExpect(jsonPath("$.valorDiaria").value(100.00))
                .andExpect(jsonPath("$.ano").value(2024));
    }

    @Test
    void deveRetornarNotFoundAoTentarObterDetalhesCarroComIdInexistente() throws Exception {
        when(carroService.buscarPorId(any())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        get("/carros/1")
                ).andExpect(status().isNotFound());
    }

    @Test
    void deveMostrarCarros() throws Exception {
        CarroEntity carro1 = new CarroEntity(1L, "Sedan", 100.00, 2024);
        CarroEntity carro2 = new CarroEntity(2L, "Argo", 200.00, 2025);

        var lista = List.of(carro1, carro2);

        when(carroService.listarTodos()).thenReturn(lista);

        mockMvc.perform(
                get("/carros")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Sedan"))
                .andExpect(jsonPath("$[0].valorDiaria").value(100.00))
                .andExpect(jsonPath("$[0].ano").value(2024))
                .andExpect(jsonPath("$[1].modelo").value("Argo"))
                .andExpect(jsonPath("$[1].valorDiaria").value(200.00))
                .andExpect(jsonPath("$[1].ano").value(2025));
    }

    @Test
    void deveAtualizarUmCarro() throws Exception {
        when(carroService.atualizar(any(), any())).thenReturn(new CarroEntity(1L, "Celta", 120.00, 2021));

        String json = """
                {
                  "modelo": "Celta",
                  "valorDiaria": 120.00,
                  "ano": 2021
                }
                """;

        mockMvc.perform(
                put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoTentarAtualizarCarroComIdInexistente() throws Exception {
        when(carroService.atualizar(any(), any())).thenThrow(EntityNotFoundException.class);

        String json = """
                {
                  "modelo": "Celta",
                  "valorDiaria": 120.00,
                  "ano": 2021
                }
                """;

        mockMvc.perform(
                put("/carros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    void deveExcluirUmCarro() throws Exception{
        doNothing().when(carroService).excluir(any());

        mockMvc.perform(delete("/carros/1")).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoExcluirUmCarroComIdInexistente() throws Exception{
        doThrow(EntityNotFoundException.class).when(carroService).excluir(any());

        mockMvc.perform(delete("/carros/10")).andExpect(status().isNotFound());
    }
}