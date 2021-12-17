package com.example.springmvc.basic.request;

import com.example.springmvc.basic.HelloData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    // 기존의 Servlet에서 사용하던 Json을 받는 방법
    // 앞에서 했던 String과 같은 방법으로 받지만, 다른 점은
    // Json으로 받기때문에 objectMapper의 readValue() 메소드를 통해서
    // HelloData 객체에 넣는다.
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");

    }
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    // String을 받을때와 마찬가지로 @RequestBody 어노테이션을 이용하면 inputStream을 사용할 필요없이
    // 바로 값을 받아서 사용할 수 있다.
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    // http 메세지 컨버터를 통해서 값이 무슨 타입인지 확인하고
    // 그 타입에 맞는 자바 객체에 담아서 사용할 수 있도록 해준다.
    // 우리가 V2처럼 직접 objectMapper에 담아서 사용하지 않아도
    // 이를 처리하는 과정을 생략할 수 있도록 해주기 때문에 편리하다.
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    // 어노테이션이 아닌 HttpEntity 객체를 사용해도 된다.
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData helloData = httpEntity.getBody();
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    // 응답 또한 @ResponseBody를 통해서 해당 객체를 http 메서지 바디에 직접 넣어줄 수 있다.
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return helloData;
    }
}
