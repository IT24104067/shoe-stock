package com.example.shoestock.service;

import com.example.shoestock.model.*;
import com.example.shoestock.repo.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository repo;
    private final NotificationService notificationService;

    public OfferService(OfferRepository repo, NotificationService notificationService) {
        this.repo = repo;
        this.notificationService = notificationService;
    }

    public Offer proposeOffer(Offer o) {
        o.setStatus(OfferStatus.PENDING);
        Offer saved = repo.save(o);
        notificationService.createNotificationForRole("New offer proposed by " + o.getProposedByUserId(), Role.ROLE_MANAGER);
        return saved;
    }

    public Offer approveOffer(String id, String managerId) {
        Offer o = repo.findById(id).orElseThrow();
        o.setStatus(OfferStatus.APPROVED);
        Offer s = repo.save(o);
        notificationService.createNotification(o.getProposedByUserId(), "Your offer approved by " + managerId);
        return s;
    }

    public Offer rejectOffer(String id, String managerId, String reason) {
        Offer o = repo.findById(id).orElseThrow();
        o.setStatus(OfferStatus.REJECTED);
        repo.save(o);
        notificationService.createNotification(o.getProposedByUserId(), "Your offer rejected: " + reason);
        return o;
    }
}
