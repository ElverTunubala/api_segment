package controllers;

import models.Segment;
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import play.libs.Json;
import repositories.SegmentRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class SegmentController extends Controller {

    private final SegmentRepository segmentRepository;

    @Inject
    public SegmentController(SegmentRepository segmentRepository) {
        this.segmentRepository = segmentRepository;
    }

    // Lista todos los segmentos
    public Result list() {
        List<Segment> segments = segmentRepository.findAll();
        return ok(Json.toJson(segments));
    }

    // Crea un nuevo segmento
    public Result create(Request request) {
        Segment segment = Json.fromJson(request.body().asJson(), Segment.class);

        if ( segment.getLength() == null || segment.getDirectionNomenclature() == null) {
            return badRequest("Invalid Segment data");
        }

        segmentRepository.save(segment);
        return created(Json.toJson(segment));
    }

    

    // Muestra un segmento por ID
    public Result show(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Segment segment = segmentRepository.findById(uuid);
        if (segment == null) {
            return notFound("Segment not found");
        }
        return ok(Json.toJson(segment));
    }

    // Actualiza un segmento existente
    public Result update(String id, Request request) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Segment existingSegment = segmentRepository.findById(uuid);
        if (existingSegment == null) {
            return notFound("Segment not found");
        }

        Segment updatedSegment = Json.fromJson(request.body().asJson(), Segment.class);

        if (updatedSegment.getNumber() == null || updatedSegment.getLength() == null || updatedSegment.getDirectionNomenclature() == null) {
            return badRequest("Invalid Segment data");
        }

        existingSegment.setNumber(updatedSegment.getNumber());
        existingSegment.setLength(updatedSegment.getLength());
        existingSegment.setDirectionNomenclature(updatedSegment.getDirectionNomenclature());
        segmentRepository.update(existingSegment);

        return ok(Json.toJson(existingSegment));
    }

    // Elimina un segmento por ID
    public Result delete(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return badRequest("Invalid UUID format");
        }

        Segment segment = segmentRepository.findById(uuid);
        if (segment == null) {
            return notFound("Segment not found");
        }

        segmentRepository.delete(uuid);
        return ok("Segment deleted");
    }
}
