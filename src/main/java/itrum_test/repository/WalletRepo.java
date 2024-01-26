package itrum_test.repository;

import itrum_test.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepo extends JpaRepository <Wallet, Long> {

    //проверка наличия кошелька в бд
    boolean existsWalletByUuid (UUID uuid);

    //получение кошелька из бд по id
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    Wallet getByUuid (UUID uuid);
}
