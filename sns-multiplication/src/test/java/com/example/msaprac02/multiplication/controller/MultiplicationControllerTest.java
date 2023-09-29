package com.example.msaprac02.multiplication.controller;

import com.example.msaprac02.multiplication.domain.Multiplication;
import com.example.msaprac02.multiplication.service.MultiplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


/*
Controller Test
Test List
1.Mapping
2.After Business Logic

사용자에게 무작위 곱셈컨탠츠를 제공 처리하는 Controller
* */
@ExtendWith(SpringExtension.class)
@WebMvcTest(MultiplicationController.class)
public class MultiplicationControllerTest {

    /*
    1. Mocking 기능 == 모의 Bean.
    2. 테스트의 격리 == 외부 서비스와 상호작용을 피함.
    3. 테스트케이스의 설정 최소화.
    4. 예외 시나리오 테스트.
    * */
    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester jacksonTester;

    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
    }

    /**
     * Test For
     *  임의로 Multiplication 잘 만들어지나?
     *  Controller Mapping 잘 되었나?
     *
     *  G : Entity 생성자 활용 Field 값 지정
     *  W : URL 로 GET 요청. MediaType : Json
     *  T : Http 200 OK? && JSON Data == {factorA:70 , factor:20}
     *
     *  @throws Exception
     */
    @Test
    public void getRandomMultiplicationTest() throws Exception {
        //given
        given(multiplicationService.createRandomMultiplication()).willReturn(new Multiplication(70,20));

        //when
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(
                get("/multiplications/random")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then

        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(mockHttpServletResponse.getContentAsString())
                .isEqualTo(jacksonTester.write(new Multiplication(70,20)).getJson());
    }
}