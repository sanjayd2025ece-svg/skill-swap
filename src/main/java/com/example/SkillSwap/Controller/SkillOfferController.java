package com.example.SkillSwap.Controller;
import com.example.SkillSwap.Entity.SkillOffer;
import com.example.SkillSwap.Service.SkillOfferService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/skills") @CrossOrigin
public class SkillOfferController {
    private final SkillOfferService service;
    public SkillOfferController(SkillOfferService service) { this.service=service; }
    @GetMapping public List<ApiViews.SkillView> all() { return service.all().stream().map(ApiViews.SkillView::of).toList(); }
    @GetMapping("/{id}") public ApiViews.SkillView get(@PathVariable Long id) { return ApiViews.SkillView.of(service.get(id)); }
    @PostMapping("/member/{memberId}") public ResponseEntity<?> create(@PathVariable Long memberId,@RequestBody SkillOffer s) { return ResponseEntity.status(201).body(ApiViews.SkillView.of(service.create(memberId,s))); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
