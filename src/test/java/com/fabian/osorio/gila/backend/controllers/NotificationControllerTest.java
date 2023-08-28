package com.fabian.osorio.gila.backend.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fabian.osorio.gila.backend.dtos.NotificationMessageDTO;
import com.fabian.osorio.gila.backend.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    NotificationService notificationService;

    @Test
    void sendANotification() throws Exception {
        mvc.perform(
                post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"FINANCE\",\"message\":\"hello\"}")


        );
        verify(notificationService).processNotification(
                new NotificationMessageDTO(CategoryEnum.FINANCE, "hello")
        );
    }

    @Test
    void sendANotificationWithAInvalidCategory() throws Exception {
        String expectedError = "Invalid value for 'category'. Allowed values are: SPORTS, FINANCE, FILMS";

        mvc.perform(
                post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"category\":\"ERROR\",\"message\":\"hello\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(expectedError));
    }
}
