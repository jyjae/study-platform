package com.example.studyplatform.controller.user;

import com.example.studyplatform.advice.ExceptionAdvice;
import com.example.studyplatform.dto.sign.SignUpRequest;
import com.example.studyplatform.exception.UserEmailAlreadyExistsException;
import com.example.studyplatform.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.studyplatform.factory.dto.SignUpRequestFactory.createSignUpRequest;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Mock
    private ExceptionAdvice exceptionAdvice;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(exceptionAdvice).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void signUpTest() throws Exception {
        // given
        SignUpRequest req = createSignUpRequest();

        // when, then
        mockMvc.perform(
                post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").doesNotExist());
    }

    @Test
    void signUpDuplicateExceptionTest() throws Exception {
        // given
        SignUpRequest req = createSignUpRequest();
        doThrow(UserEmailAlreadyExistsException.class).when(userService).signUp(req);

        mockMvc.perform(
                        post("/api/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                ).andExpect(status().isConflict());
    }
}