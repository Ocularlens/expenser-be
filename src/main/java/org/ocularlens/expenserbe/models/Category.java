package org.ocularlens.expenserbe.models;

import jakarta.persistence.*;
import org.ocularlens.expenserbe.common.TransactionType;

import java.util.List;

@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private boolean createdByAdmin;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    public Category() {}

    public Category(TransactionType type, String name, boolean createdByAdmin) {
        this.type = type;
        this.name = name;
        this.createdByAdmin = createdByAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCreatedByAdmin() {
        return createdByAdmin;
    }

    public void setCreatedByAdmin(boolean createdByAdmin) {
        this.createdByAdmin = createdByAdmin;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
