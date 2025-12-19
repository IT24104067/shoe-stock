package com.example.shoestock.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoestock.model.OfferStatus;
import com.example.shoestock.repo.OfferRepository;
import com.example.shoestock.repo.ShoeRepository;
import com.example.shoestock.repo.UserRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ShoeRepository shoeRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public DashboardController(ShoeRepository shoeRepository,
                               OfferRepository offerRepository,
                               UserRepository userRepository) {
        this.shoeRepository = shoeRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER','ROLE_IT')")
    @GetMapping("/stats")
    public DashboardStats getStats() {
        long totalShoes = shoeRepository.count();
        long lowStock = shoeRepository.findAll().stream()
                .filter(shoe -> shoe.getSizes() != null)
                .flatMap(shoe -> shoe.getSizes().stream())
                .filter(entry -> entry.getCount() <= 10)
                .count();
        long pendingOffers = offerRepository.countByStatus(OfferStatus.PENDING);
        long totalUsers = userRepository.count();
        return new DashboardStats(totalShoes, lowStock, pendingOffers, totalUsers);
    }

    public record DashboardStats(long totalShoes,
                                 long lowStock,
                                 long pendingOffers,
                                 long totalUsers) { }
}
