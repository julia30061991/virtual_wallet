package itrum_test.controller;

import itrum_test.model.Wallet;
import itrum_test.service.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WalletController {

    private final WalletServiceImpl walletServiceImpl;

    @Autowired
    public WalletController(WalletServiceImpl walletServiceImpl) {
        this.walletServiceImpl = walletServiceImpl;
    }

    @PostMapping("/v1")
    public ResponseEntity<Wallet> createWallet() {
        return walletServiceImpl.createWallet();
    }

    @PostMapping("/v1/wallet")
    public ResponseEntity<Map<String, Object>> changeWalletBalance(@RequestParam UUID uuid, @RequestParam String type, @RequestParam int amount) {
        return walletServiceImpl.changeBalance(uuid, type, amount);
    }

    @GetMapping("/v1/wallets/{WALLET_UUID}")
    public ResponseEntity<Map<String, Object>> getWalletBalance(@PathVariable("WALLET_UUID") UUID uuid) {
        return walletServiceImpl.getBalance(uuid);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException ex) {
        String errorInfo = "Обязательный параметр " + ex.getParameterName() + " не указан";
        return new ResponseEntity<>(Map.of("message", errorInfo), HttpStatus.BAD_REQUEST);
    }
}