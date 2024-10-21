package com.AlissonMartin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
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

  @Generated
  @JsonIgnore
  @Column(columnDefinition = "TEXT")
  public String concatsearch;


  public static Boolean pessoaExistsByApelido(String apelido) {
    long count = Pessoa.count("apelido = ?1", apelido);
    return count > 0;
  }

  // SQL puro pro list
  public static List<Pessoa> findByTerm(String term) {
    String sql = "concatsearch ILIKE ?1";
    return Pessoa.<Pessoa>find("concatsearch like '%' || ?1 || '%'", term).page(0, 50).list();
  }
}
