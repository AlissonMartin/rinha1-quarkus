package com.AlissonMartin.controller;

import com.AlissonMartin.model.Pessoa;
import com.AlissonMartin.service.PessoaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaController {

  @Inject
  PessoaService pessoaService;

  @POST
  @Transactional
  @Path("/pessoas")
  public Response create(Pessoa pessoa) {
    if (pessoa.apelido == null || pessoa.apelido.isBlank() || pessoa.apelido.length() > 32 || pessoa.nome == null || pessoa.nome.isBlank() || pessoa.nome.length() > 100 || nascimentoIsInvalid(pessoa.nascimento)) {
      return Response.status(422).build();
    }

    if (Pessoa.pessoaExistsByApelido(pessoa.apelido)) {
      return Response.status(422).build();
    }

    String uid = pessoaService.create(pessoa);

    return Response.status(Response.Status.CREATED).header(HttpHeaders.LOCATION, "/pessoas/" + uid).build();
  }

  @GET
  @Path("/pessoas")
  public Response list(@QueryParam("t") String t) {
    if (t == null || t.isBlank()) {
      return Response.status(400).build();
    }
    List<Pessoa> pessoas = pessoaService.list(t);

    return Response.ok(pessoas).build();
  }

  @GET
  @Path("/pessoas/{uid}")
  public Response getById(@PathParam("uid") String uid) {
    Pessoa pessoa = pessoaService.getById(uid);

    return Response.ok(pessoa).build();
  }

  @GET
  @Path("/contagem-pessoas")
  public Response count() {
    return Response.ok(Pessoa.count()).build();
  }

  private boolean nascimentoIsInvalid(String nascimento) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    try {
      sdf.parse(nascimento);
      return false;
    } catch (ParseException e) {
      return true;
    }
  }
}
