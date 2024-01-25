package itrum_test.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "balance", columnDefinition = "INT")
    private int balance;

    public Wallet() {}

    public Wallet(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String toString() {
        return "Идентификационный номер кошелька " + getUuid() + ", текущий баланс " + getBalance();
    }
}