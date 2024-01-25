package itrum_test.service;

import itrum_test.model.Wallet;
import itrum_test.repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepo walletRepo;

    @Autowired
    public WalletServiceImpl(WalletRepo walletRepo) {
        this.walletRepo = walletRepo;
    }

    @Override
    public ResponseEntity<Wallet> createWallet() {
        Wallet newWallet = new Wallet(UUID.randomUUID());
        walletRepo.save(newWallet);
        return new ResponseEntity<>(newWallet, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getBalance(UUID uuid) {
        boolean isExist = walletRepo.existsWalletByUuid(uuid);
        Object message = isExist ? walletRepo.getByUuid(uuid).getBalance() : "Кошелек с указанным номером не существует";
        var status = isExist ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(Map.of("message", message), status);
    }

    @Override
    public ResponseEntity<Map<String,Object>> changeBalance(UUID uuid, String type, int amount) {
        type = type.toLowerCase();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Object message = "Кошелек с указанным номером не существует";
        if (walletRepo.existsWalletByUuid(uuid)) {
            Wallet wallet = walletRepo.getByUuid(uuid);
            switch (type){
                case "deposit":
                    int newBalance = wallet.getBalance() + amount;
                    wallet.setBalance(newBalance);
                    walletRepo.save(wallet);
                    message = "Баланс пополнен на " + amount + ", текущий баланс: " + newBalance;
                    status = HttpStatus.OK;
                    break;
                case "withdraw":
                    if (wallet.getBalance() >= amount) {
                        int newBalance1 = wallet.getBalance() - amount;
                        wallet.setBalance(newBalance1);
                        walletRepo.save(wallet);
                        message = "Баланс снижен на " + amount + ", текущий баланс " + newBalance1;
                        status = HttpStatus.OK;
                    } else {
                        message = "Недостаточно средств на счете";
                        status = HttpStatus.NOT_ACCEPTABLE;
                    }
                    break;
                    default:
                        message = "Тип операции не поддерживается";
                        status = HttpStatus.BAD_REQUEST;
            }
        }
        return new ResponseEntity<>(Map.of("message", message), status);
    }
}