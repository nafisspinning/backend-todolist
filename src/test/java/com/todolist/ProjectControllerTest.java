package com.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAndFetchProject() throws Exception {
        String payload = """
                {
                  "title": "Website Revamp",
                  "description": "Redesign the marketing site",
                  "dueDate": "2026-12-31"
                }
                """;

        mockMvc.perform(post("/api/v1/projects")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Website Revamp"));

        mockMvc.perform(get("/api/v1/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void rejectsInvalidProject() throws Exception {
        String payload = """
                { "title": "", "description": "", "dueDate": null }
                """;

        mockMvc.perform(post("/api/v1/projects")
                        .contentType("application/json")
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void returns404ForUnknownProject() throws Exception {
        mockMvc.perform(get("/api/v1/projects/00000000-0000-0000-0000-000000000000"))
                .andExpect(status().isNotFound());
    }
}
