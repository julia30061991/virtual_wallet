package itrum_test.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    public Wallet(UUID uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return "Идентификационный номер кошелька " + getUuid() + ", текущий баланс " + getBalance();
    }
}