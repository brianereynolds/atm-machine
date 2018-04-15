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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AtmApplication.class)
@AutoConfigureMockMvc
public class AtmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBalance() throws Exception {
        Long testAccountNum = 100100013L;
        String testPin = "9923";
        String url = getUrl(testAccountNum, testPin);

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
        String url = getUrl(testAccountNum, testPin);

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
        String url = getUrl(testAccountNum, testPin);

        this.mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.code", is(ErrorCodes.PIN_VALIDATION)))
                .andExpect(jsonPath("$.message", is(ErrorMessages.INVALID_PIN)));
    }

    private String getUrl(Long accountNum, String pin) {
        return String.format("/balance/%s/%s", accountNum, pin);

    }
}
