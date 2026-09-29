package com.example.SkillSwap.Controller;
import com.example.SkillSwap.Service.CreditLedgerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/credits") @CrossOrigin
public class CreditLedgerController {
    private final CreditLedgerService service;
    public CreditLedgerController(CreditLedgerService service) { this.service=service; }
    @GetMapping public List<ApiViews.LedgerView> all() { return service.all().stream().map(ApiViews.LedgerView::of).toList(); }
    @GetMapping("/{id}") public ApiViews.LedgerView get(@PathVariable Long id) { return ApiViews.LedgerView.of(service.get(id)); }
    @GetMapping("/member/{memberId}") public List<ApiViews.LedgerView> byMember(@PathVariable Long memberId) { return service.byMember(memberId).stream().map(ApiViews.LedgerView::of).toList(); }
}
