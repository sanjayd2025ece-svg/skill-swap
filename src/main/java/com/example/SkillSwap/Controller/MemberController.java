package com.example.SkillSwap.Controller;
import com.example.SkillSwap.Entity.Member;
import com.example.SkillSwap.Service.MemberService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/members") @CrossOrigin
public class MemberController {
    private final MemberService service;
    public MemberController(MemberService service) { this.service=service; }
    @PostMapping public ResponseEntity<?> create(@RequestBody Member m) { return ResponseEntity.status(201).body(ApiViews.MemberView.of(service.save(m))); }
    @GetMapping public List<ApiViews.MemberView> all() { return service.all().stream().map(ApiViews.MemberView::of).toList(); }
    @GetMapping("/{id}") public ApiViews.MemberView get(@PathVariable Long id) { return ApiViews.MemberView.of(service.get(id)); }
    @PutMapping("/{id}") public ApiViews.MemberView update(@PathVariable Long id,@RequestBody Member m) { return ApiViews.MemberView.of(service.update(id,m)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
