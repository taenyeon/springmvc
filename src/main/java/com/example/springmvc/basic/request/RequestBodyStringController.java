package com.example.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {
    // 바디에 일반 텍스트의 값을 자바로 받는 방법

    @PostMapping("/request-body-string-v1")
    // 기존의 Servlet에서 사용했던 방식과 같이 request를 통해서 inputStream을 얻어 사용하는 방식
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}",messageBody);

        response.getWriter().write("ok");

    }

    @PostMapping("/request-body-string-v2")
    // Servlet과 다르게 고정적으로 request와 response를 반드시 인자로 받을 필요가 없어
    // 다음과 같이 InputStream과 Writer를 바로 받아서 사용.
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}",messageBody);
        responseWriter.write("ok");

    }

    @PostMapping("/request-body-string-v3")
    // http 메세지 컨버터를 이용하여 간단하게 표현 가능

    // HttpEntity : http header, body 정보를 편리하게 조회
    // 요청
    // 1. 메세지 바디 정보를 직접 조회
    // 2. 요청 파라미터를 조회하는 기능과 관련 X (@RequestParam,@ModelAttribute와 관련 X)

    // 응답
    // 1. 메세지 바디 정보 직접 반환
    // 2. 헤터 정보 포함 가능
    // view 조회는 불가능

    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();
        log.info("messageBody = {}",messageBody);

        return new HttpEntity<>("ok");
    }


    @ResponseBody
    @PostMapping("/request-body-string-v4")
    // V3보다 더 간단하게 해주는 어노테이션을 사용할 수도 있다.
    // 아래와 같이 @RequestBody 어노테이션을 사용하는 변수는 Spring에서 자동으로
    // 메시지 바디 정보로 다루게 해준다.
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody = {}",messageBody);

        return "ok";
    }
}
