package com.AlissonMartin.service;

import com.AlissonMartin.exception.RecordNotFoundException;
import com.AlissonMartin.model.Pessoa;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PessoaService {

  public String create(Pessoa pessoa) {
    Pessoa.persist(pessoa);
    return (pessoa.id);
  }

  public List<Pessoa> list(String term) {
    return Pessoa.findByTerm(term);
  }

  public Pessoa getById(String uid) {
    Optional<Pessoa> optionalPessoa = Pessoa.findByIdOptional(uid);
    return optionalPessoa.orElseThrow(()-> new RecordNotFoundException());
  }
}
