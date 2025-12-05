package com.example.shoestock.controller;

import com.example.shoestock.model.Offer;
import com.example.shoestock.service.OfferService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private final OfferService offerService;
    public OfferController(OfferService offerService) { this.offerService = offerService; }

    @PreAuthorize("hasAuthority('ROLE_ASSISTANT')")
    @PostMapping("/propose")
    public ResponseEntity<?> propose(@RequestBody Offer o) {
        return ResponseEntity.ok(offerService.proposeOffer(o));
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable String id, @RequestParam String managerId) {
        return ResponseEntity.ok(offerService.approveOffer(id, managerId));
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable String id, @RequestParam String managerId, @RequestParam String reason) {
        return ResponseEntity.ok(offerService.rejectOffer(id, managerId, reason));
    }
}
