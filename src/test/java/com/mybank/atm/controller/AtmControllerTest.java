package com.mybank.atm.controller;

import com.mybank.atm.AtmApplication;
import com.mybank.atm.config.ErrorCodes;
import com.mybank.atm.config.ErrorMessages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtmApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AtmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBalance() throws Exception {
        Long testAccountNum = 100100013L;
        String testPin = "9923";
        String url = getBalanceUri(testAccountNum, testPin);

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.balance", is(9.50)))
                .andExpect(jsonPath("$.maxWithdrawal", is(309.51)));

    }

    @Test
    public void testInvalidAccountNum() throws Exception {
        Long testAccountNum = 11L;
        String testPin = "0000";
        String url = getBalanceUri(testAccountNum, testPin);

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCodes.ACCOUNT_LOOKUP)))
                .andExpect(jsonPath("$.message", is(ErrorMessages.ACCOUNT_NOT_FOUND)));
    }

    @Test
    public void testIncorrectPin() throws Exception {
        Long testAccountNum = 443457424L;
        String testPin = "0000";
        String url = getBalanceUri(testAccountNum, testPin);

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCodes.PIN_VALIDATION)))
                .andExpect(jsonPath("$.message", is(ErrorMessages.INVALID_PIN)));
    }

    @Test
    public void testWithdrawFundsBasic() throws Exception {
        Long testAccountNum = 443457424L;
        String testPin = "5623";
        String url = getWithdrawUri(testAccountNum, testPin, "300");

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.amount", is(300)))
                .andExpect(jsonPath("$.cashMap.fifty", is(6)))
                .andExpect(jsonPath("$.cashMap.twenty", is(0)))
                .andExpect(jsonPath("$.cashMap.ten", is(0)))
                .andExpect(jsonPath("$.cashMap.five", is(0)))
                .andExpect(jsonPath("$.accountResource.balance", is(54.54)))
                .andExpect(jsonPath("$.accountResource.maxWithdrawal", is(554.54)));
    }

    @Test
    public void testWithdrawFundsOverdraft() throws Exception {
        Long testAccountNum = 443457424L;
        String testPin = "5623";
        String url = getWithdrawUri(testAccountNum, testPin, "845");

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.amount", is(845)))
                .andExpect(jsonPath("$.cashMap.fifty", is(16)))
                .andExpect(jsonPath("$.cashMap.twenty", is(2)))
                .andExpect(jsonPath("$.cashMap.ten", is(0)))
                .andExpect(jsonPath("$.cashMap.five", is(1)))
                .andExpect(jsonPath("$.accountResource.balance", is(0)))
                .andExpect(jsonPath("$.accountResource.maxWithdrawal", is(9.54)));
    }

    @Test
    public void testWithdrawFundsUntilBroke() throws Exception {
        Long testAccountNum = 494911101L ;
        String testPin = "4425";
        String url = getWithdrawUri(testAccountNum, testPin, "95");

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.amount", is(95)))
                .andExpect(jsonPath("$.cashMap.fifty", is(1)))
                .andExpect(jsonPath("$.cashMap.twenty", is(2)))
                .andExpect(jsonPath("$.cashMap.ten", is(0)))
                .andExpect(jsonPath("$.cashMap.five", is(1)))
                .andExpect(jsonPath("$.accountResource.balance", is(5.00)))
                .andExpect(jsonPath("$.accountResource.maxWithdrawal", is(405.00)));

        url = getWithdrawUri(testAccountNum, testPin, "205");

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.amount", is(205)))
                .andExpect(jsonPath("$.cashMap.fifty", is(4)))
                .andExpect(jsonPath("$.cashMap.twenty", is(0)))
                .andExpect(jsonPath("$.cashMap.ten", is(0)))
                .andExpect(jsonPath("$.cashMap.five", is(1)))
                .andExpect(jsonPath("$.accountResource.balance", is(0)))
                .andExpect(jsonPath("$.accountResource.maxWithdrawal", is(200.00)));

        url = getWithdrawUri(testAccountNum, testPin, "150");

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.amount", is(150)))
                .andExpect(jsonPath("$.cashMap.fifty", is(3)))
                .andExpect(jsonPath("$.cashMap.twenty", is(0)))
                .andExpect(jsonPath("$.cashMap.ten", is(0)))
                .andExpect(jsonPath("$.cashMap.five", is(0)))
                .andExpect(jsonPath("$.accountResource.balance", is(0)))
                .andExpect(jsonPath("$.accountResource.maxWithdrawal", is(50.00)));

        url = getWithdrawUri(testAccountNum, testPin, "75");

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.code", is("A1002")))
                .andExpect(jsonPath("$.message", is("Insufficient account balance")));
    }

    private String getBalanceUri(Long accountNum, String pin) {
        return String.format("/balance/%s/%s", accountNum, pin);
    }

    private String getWithdrawUri(Long accountNum, String pin, String amount) {
        return String.format("/withdraw/%s/%s/%s", accountNum, pin, amount);
    }
}
