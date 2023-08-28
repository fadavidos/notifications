package com.fabian.osorio.gila.backend.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fabian.osorio.gila.backend.services.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    NotificationService notificationService;

    @Test
    void getAllCategories() throws Exception {
        List<String> expectedCategories = List.of("SPORTS", "FINANCE", "FILMS");

        when(notificationService.getAllCategories()).thenReturn(expectedCategories);

        mvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedCategories.size())))
                .andExpect(jsonPath("$", containsInAnyOrder(expectedCategories.toArray())));

        verify(notificationService).getAllCategories();
    }
}
