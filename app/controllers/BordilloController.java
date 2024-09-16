package controllers;

import models.Bordillo;
import play.mvc.*;
import play.libs.Json;
import repositories.BordilloRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class BordilloController extends Controller {

    private final BordilloRepository bordilloRepository;

    @Inject
    public BordilloController(BordilloRepository bordilloRepository) {
        this.bordilloRepository = bordilloRepository;
    }

    // Lista todos los bordillos
    public Result list() {
        List<Bordillo> bordillos = bordilloRepository.findAll();
        return ok(Json.toJson(bordillos));
    }

    // Crea un nuevo bordillo
    public Result create(Http.Request request) {
        Bordillo bordillo = Json.fromJson(request.body().asJson(), Bordillo.class);

        if ( bordillo.getLength() == null || bordillo.getSegmentId() == null) {
            return badRequest("Invalid Bordillo data");
        }

        bordilloRepository.save(bordillo);
        return created(Json.toJson(bordillo));
    }

    // Muestra un bordillo por ID
    public Result show(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Bordillo bordillo = bordilloRepository.findById(uuid);
        if (bordillo == null) {
            return notFound("Bordillo not found");
        }
        return ok(Json.toJson(bordillo));
    }

    // Actualiza un bordillo existente
    public Result update(String id, Http.Request request) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Bordillo existingBordillo = bordilloRepository.findById(uuid);
        if (existingBordillo == null) {
            return notFound("Bordillo not found");
        }

        Bordillo updatedBordillo = Json.fromJson(request.body().asJson(), Bordillo.class);

        if (updatedBordillo.getLength() == null || updatedBordillo.getSegmentId() == null) {
            return badRequest("Invalid Bordillo data");
        }

        existingBordillo.setLength(updatedBordillo.getLength());
        existingBordillo.setSegmentId(updatedBordillo.getSegmentId());
        bordilloRepository.update(existingBordillo);

        return ok(Json.toJson(existingBordillo));
    }

    // Elimina un bordillo por ID
    public Result delete(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Bordillo bordillo = bordilloRepository.findById(uuid);
        if (bordillo == null) {
            return notFound("Bordillo not found");
        }

        bordilloRepository.delete(uuid);
        return ok("Bordillo deleted");
    }
}
