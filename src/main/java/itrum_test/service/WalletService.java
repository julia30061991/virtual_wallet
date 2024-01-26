package itrum_test.service;

import itrum_test.model.Wallet;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

public interface WalletService {

    //добавление кошелька/счета
    ResponseEntity<Wallet> createWallet();

    //получение строковой информации о балансе
    ResponseEntity<Map<String, Object>> getBalance(UUID uuid);

    //изменение баланса счета
    ResponseEntity<Map<String, Object>> changeBalance(UUID uuid, String type, int amount);
}