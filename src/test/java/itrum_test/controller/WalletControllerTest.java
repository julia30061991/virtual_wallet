package itrum_test.controller;

import itrum_test.model.Wallet;
import itrum_test.repository.WalletRepo;
import itrum_test.service.WalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletRepo repo;

    @MockBean
    private WalletServiceImpl service;

    @Test
    void create_wallet_test() throws Exception {
        when(service.createWallet()).thenReturn(new ResponseEntity<>(new Wallet(), HttpStatus.CREATED));
        mockMvc.perform(post("/api/v1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void change_wallet_test() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(service.changeBalance(uuid, "DEPOSIT", 100)).thenReturn(new ResponseEntity<>(Map.of(), HttpStatus.OK));
        mockMvc.perform(post("/api/v1/wallet?uuid=" + uuid + "&type=DEPOSIT&amount=100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        when(service.changeBalance(uuid, "WITHDRAW", 100)).thenReturn(new ResponseEntity<>(Map.of(), HttpStatus.OK));
        mockMvc.perform(post("/api/v1/wallet?uuid=" + uuid + "&type=DEPOSIT&amount=100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        when(service.changeBalance(uuid, "WITHDRAW", 300)).thenReturn(new ResponseEntity<>(Map.of(), HttpStatus.NOT_ACCEPTABLE));
        mockMvc.perform(post("/api/v1/wallet?uuid=" + uuid + "&type=WITHDRAW&amount=300")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        when(service.changeBalance(uuid, "CREDIT", 300)).thenReturn(new ResponseEntity<>(Map.of(), HttpStatus.BAD_REQUEST));
        mockMvc.perform(post("/api/v1/wallet?uuid=" + uuid + "&type=CREDIT&amount=300")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void get_wallet_balance_test() throws Exception {
        UUID uuid = UUID.randomUUID();

        when(service.getBalance(uuid)).thenReturn((new ResponseEntity<>(Map.of(), HttpStatus.OK)));
        mockMvc.perform(get("/api/v1/wallets/" + uuid).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        when(service.getBalance(uuid)).thenReturn((new ResponseEntity<>(Map.of(), HttpStatus.BAD_REQUEST)));
        mockMvc.perform(get("/api/v1/wallets/" + uuid).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}