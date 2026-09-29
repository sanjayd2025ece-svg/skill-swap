package com.example.SkillSwap.Controller;
import com.example.SkillSwap.Entity.SessionRequest;
import com.example.SkillSwap.Service.SessionRequestService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/session-requests") @CrossOrigin
public class SessionRequestController {
    private final SessionRequestService service;
    public SessionRequestController(SessionRequestService service) { this.service=service; }
    public record CreateRequest(Long requesterId, Long providerId, Long skillOfferId, Integer requestedHours) {}
    @PostMapping public ResponseEntity<?> create(@RequestBody CreateRequest q) { return ResponseEntity.status(201).body(ApiViews.RequestView.of(service.create(q.requesterId(),q.providerId(),q.skillOfferId(),q.requestedHours()))); }
    @GetMapping public List<ApiViews.RequestView> all() { return service.all().stream().map(ApiViews.RequestView::of).toList(); }
    @GetMapping("/{id}") public ApiViews.RequestView get(@PathVariable Long id) { return ApiViews.RequestView.of(service.get(id)); }
    @PutMapping("/{id}/confirm") public ApiViews.RequestView confirm(@PathVariable Long id) { return ApiViews.RequestView.of(service.confirm(id)); }
    @PutMapping("/{id}/reject") public ApiViews.RequestView reject(@PathVariable Long id) { return ApiViews.RequestView.of(service.reject(id)); }
    @PutMapping("/{id}/complete") public ApiViews.RequestView complete(@PathVariable Long id) { return ApiViews.RequestView.of(service.complete(id)); }
}
