package br.edu.ifpr.casalapp.controller;

import br.edu.ifpr.casalapp.dto.TransacaoRequest;
import br.edu.ifpr.casalapp.dto.TransacaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransacaoController {

    private int proximoId = 4;

    private final List<TransacaoResponse> transacoes = new ArrayList<>(List.of(
        new TransacaoResponse(1, "Salário", 3000.0, "receita"),
        new TransacaoResponse(2, "Mercado", 250.0, "despesa"),
        new TransacaoResponse(3, "Internet", 120.0, "despesa")
    ));

    @GetMapping("/transacoes")
    public List<TransacaoResponse> listar() {
        return transacoes;
    }

    @GetMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoResponse> buscar(@PathVariable int id) {
        for (TransacaoResponse t : transacoes) {
            if (t.id() == id) return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/transacoes/{id}")
    public ResponseEntity<Void> remover(@PathVariable int id) {
        for (int i = 0; i < transacoes.size(); i++) {
            if (transacoes.get(i).id() == id) {
                transacoes.remove(i);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/transacoes")
    public ResponseEntity<TransacaoResponse> criar(@RequestBody TransacaoRequest request) {
        TransacaoResponse nova = new TransacaoResponse(
                proximoId++, request.descricao(), request.valor(), request.tipo());
        transacoes.add(nova);
        return ResponseEntity.status(201).body(nova);
    }

    @PutMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoResponse> atualizar(@PathVariable int id,
                                                       @RequestBody TransacaoRequest request) {
        for (int i = 0; i < transacoes.size(); i++) {
            if (transacoes.get(i).id() == id) {
                TransacaoResponse atualizada = new TransacaoResponse(
                        id, request.descricao(), request.valor(), request.tipo());
                transacoes.set(i, atualizada);
                return ResponseEntity.ok(atualizada);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/transacoes/{id}")
    public ResponseEntity<TransacaoResponse> atualizarParcial(@PathVariable int id,
                                                              @RequestBody TransacaoRequest request) {
        for (int i = 0; i < transacoes.size(); i++) {
            if (transacoes.get(i).id() == id) {
                TransacaoResponse existente = transacoes.get(i);

                String descricao = request.descricao() != null ? request.descricao() : existente.descricao();
                Double valor = request.valor() != null ? request.valor() : existente.valor();
                String tipo = request.tipo() != null ? request.tipo() : existente.tipo();

                TransacaoResponse atualizada = new TransacaoResponse(id, descricao, valor, tipo);
                transacoes.set(i, atualizada);
                return ResponseEntity.ok(atualizada);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
