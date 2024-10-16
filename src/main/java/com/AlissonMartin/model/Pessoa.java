package com.AlissonMartin.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@Table(name = "pessoas")
public class Pessoa extends PanacheEntityBase {
  @Id
  @GeneratedValue
  @UuidGenerator
  public String id;

  @Column(unique = true)
  public String apelido;

  public String nome;

  public String nascimento;

  public List<String> stack;


  // SQL puro pro list
  public static List<Pessoa> findByTerm(String term) {
    String sql = "SELECT * FROM pessoas WHERE concatsearch ILIKE %?1% LIMIT 50";
    return find(sql, term).list();
  }
}
