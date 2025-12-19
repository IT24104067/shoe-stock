package com.example.shoestock.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.shoestock.model.Order;
import com.example.shoestock.model.OrderItem;
import com.example.shoestock.model.Role;
import com.example.shoestock.model.ShoeSizeEntry;
import com.example.shoestock.model.ShoeType;
import com.example.shoestock.repo.ShoeRepository;

@Service
public class  ShoeService {
    private final ShoeRepository repo;
    private final GridFsService gridFsService;
    private final NotificationService notificationService;

    public ShoeService(ShoeRepository repo, GridFsService gridFsService, NotificationService notificationService) {
        this.repo = repo;
        this.gridFsService = gridFsService;
        this.notificationService = notificationService;
    }

    public ShoeType createShoe(ShoeType s) {
        return repo.save(s);
    }

    public ShoeType findById(String id) {
        return repo.findById(id).orElseThrow();
    }

    public List<ShoeType> findAll() {
        return repo.findAll();
    }

    /**
     * Shoes that have any size entry at or below the low-stock threshold (<=10).
     */
    public List<ShoeType> findLowStockShoes() {
        return repo.findAll().stream()
                .filter(shoe -> shoe.getSizes() != null && shoe.getSizes().stream().anyMatch(size -> size.getCount() <= 10))
                .toList();
    }

    public ShoeType addSize(String shoeId, ShoeSizeEntry entry) {
        ShoeType s = findById(shoeId);
        s.getSizes().add(entry);
        return repo.save(s);
    }

    public ShoeType addImage(String shoeId, MultipartFile file) throws IOException {
        ShoeType s = findById(shoeId);
        String imageId = gridFsService.store(file, file.getOriginalFilename());
        s.getImageIds().add(imageId);
        return repo.save(s);
    }

    @Transactional
    public void decrementStockForOrder(Order order) {
        for (OrderItem it : order.getItems()) {
            ShoeType shoe = repo.findById(it.getShoeTypeId()).orElseThrow();
            ShoeSizeEntry entry = shoe.getSizes().stream()
                    .filter(x -> x.getSize().equals(it.getSize()))
                    .findFirst().orElseThrow();
            if (entry.getCount() < it.getQuantity()) throw new IllegalStateException("Not enough stock");
            entry.setCount(entry.getCount() - it.getQuantity());
            if (entry.getCount() <= 10) {
                notificationService.createNotificationForRole("Low stock: " + shoe.getName() + " size " + entry.getSize(), Role.ROLE_ASSISTANT);
            }
            repo.save(shoe);
        }
    }

    @Transactional
    public void increaseStockOnReturn(Order order) {
        for (OrderItem it : order.getItems()) {
            ShoeType shoe = repo.findById(it.getShoeTypeId()).orElseThrow();
            ShoeSizeEntry entry = shoe.getSizes().stream()
                    .filter(x -> x.getSize().equals(it.getSize()))
                    .findFirst().orElseThrow();
            entry.setCount(entry.getCount() + it.getQuantity());
            repo.save(shoe);
        }
    }
}
