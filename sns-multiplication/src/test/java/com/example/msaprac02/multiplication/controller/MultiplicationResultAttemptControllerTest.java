package com.example.msaprac02.multiplication.controller;

import com.example.msaprac02.multiplication.domain.Multiplication;
import com.example.msaprac02.multiplication.domain.MultiplicationResultAttempt;
import com.example.msaprac02.multiplication.domain.Player;
import com.example.msaprac02.multiplication.service.MultiplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static com.example.msaprac02.multiplication.controller.MultiplicationResultAttemptController.ResultResponse;


/*
Controller Test
Test List
1.Mapping
2.After Business Logic

사용자의 답안을 검토하고, 맞고 틀림을 처리하는 Controller
* */
@ExtendWith(SpringExtension.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {
    @MockBean
    private MultiplicationService multiplicationService;
    @Autowired
    private MockMvc mockMvc;
    private JacksonTester<MultiplicationResultAttempt> jsonResult;
    private JacksonTester<ResultResponse> jsonResponse;
    private JacksonTester<MultiplicationResultAttempt> jsonResultAttempt;
    private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;


    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this,new ObjectMapper());
    }

    /**
     * @throws Exception : mockMvc Err
     * Player Alias 기준으로 사용기록 검색.
     * Param : String alias
     *
     * TestFor
     * - Http OK
     * - List recentAttempt
     *  G: john_doe , 50 , 70 , true
     *  W: resp ,
     *  T:HttpStatus & Json
     */
    @Test
    public void getUserStats() throws Exception{
        //given
        Player player = new Player("john_doe");
        Multiplication multiplication = new Multiplication(50,70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(player, multiplication , 3500 ,true);
        List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt,attempt);
        given(multiplicationService
                .getStatsForPlayer("john_doe")).willReturn(recentAttempts);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/results")
                .param("alias","john_doe"))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResultAttemptList.write(recentAttempts).getJson());

    }

    /**
     * @param correct : 정답, 오답
     * @throws Exception : mockMvc Err
     */
    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    void genericParameterizedTest(final boolean correct) throws Exception{
        //given
        given(
                multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class)))
                .willReturn(correct);
        Player player = new Player("john");
        Multiplication multiplication = new Multiplication(50, 70);
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
                player, multiplication, 3500,correct);
        //when
        MockHttpServletResponse response = mockMvc.perform(
                post("/results").contentType(MediaType.APPLICATION_JSON)
                                .content(jsonResult.write(attempt).getJson()))
                .andReturn().getResponse();
        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResult.write(
                        new MultiplicationResultAttempt(
                                attempt.getPlayer(),
                                attempt.getMultiplication(),
                                attempt.getResultAttempt(), correct
                                )).getJson());

    }
}