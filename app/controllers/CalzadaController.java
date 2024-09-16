package controllers;

import models.Calzada;
import play.mvc.*;
import play.libs.Json;
import repositories.CalzadaRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class CalzadaController extends Controller {

    private final CalzadaRepository calzadaRepository;

    @Inject
    public CalzadaController(CalzadaRepository calzadaRepository) {
        this.calzadaRepository = calzadaRepository;
    }

    // Lista todas las calzadas
    public Result list() {
        List<Calzada> calzadas = calzadaRepository.findAll();
        return ok(Json.toJson(calzadas));
    }

    // Crea una nueva calzada
    public Result create(Http.Request request) {
        Calzada calzada = Json.fromJson(request.body().asJson(), Calzada.class);

        if ( calzada.getLength() == null || calzada.getSegmentId() == null) {
            return badRequest("Invalid Calzada data");
        }

        calzadaRepository.save(calzada);
        return created(Json.toJson(calzada));
    }

    // Muestra una calzada por ID
    public Result show(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Calzada calzada = calzadaRepository.findById(uuid);
        if (calzada == null) {
            return notFound("Calzada not found");
        }
        return ok(Json.toJson(calzada));
    }

    // Actualiza una calzada existente
    public Result update(String id, Http.Request request) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Calzada existingCalzada = calzadaRepository.findById(uuid);
        if (existingCalzada == null) {
            return notFound("Calzada not found");
        }

        Calzada updatedCalzada = Json.fromJson(request.body().asJson(), Calzada.class);

        if (updatedCalzada.getLength() == null || updatedCalzada.getSegmentId() == null) {
            return badRequest("Invalid Calzada data");
        }

        existingCalzada.setLength(updatedCalzada.getLength());
        existingCalzada.setSegmentId(updatedCalzada.getSegmentId());
        calzadaRepository.update(existingCalzada);

        return ok(Json.toJson(existingCalzada));
    }

    // Elimina una calzada por ID
    public Result delete(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Calzada calzada = calzadaRepository.findById(uuid);
        if (calzada == null) {
            return notFound("Calzada not found");
        }

        calzadaRepository.delete(uuid);
        return ok("Calzada deleted");
    }
}
