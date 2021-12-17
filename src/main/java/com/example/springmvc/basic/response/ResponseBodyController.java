package com.example.springmvc.basic.response;

import com.example.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
//@RestController
public class ResponseBodyController {

    @GetMapping("/response-body-string-v1")
    // 가장 기본적인 응답 방법
    public void responceBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    // @ResponseEntity를 이용한 응답 방법
    public ResponseEntity<String> responceBodyV2() throws IOException {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    // @ResponseBody를 이용한 응답 방법
    public String responceBodyV3(HttpServletResponse response) throws IOException {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    // 데이터 객체를 ResponseEntity에 담아 json데이터로 쉽게 응답하는 방법
    public ResponseEntity<HelloData> responseBodyJsonV1(){
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData,HttpStatus.OK);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/response-body-json-v2")
    // @ResponseBody를 이용하여 json 데이터를 간단하게 응답하는 방법
    // 이 방법의 경우에는 httpstatus를 설정하기 까다롭다.
    // 그래서 @ResponseStatus 어노테이션을 이용하여 간단하게 설정을 해주는데
    // 이 역시도 V1에 비해서 까다롭기 때문에 httpstatus를 유동적으로 주고 싶다면
    // V1을 주는게 좋다.
    public HelloData responseBodyJsonV2(){
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;

    }
    // 형태들을 보면 @ResponseBody 어노테이션이계속 중복되고 있음을 알 수 있다.
    // 이때, @ResponseBody를 Class에 주게 된다면 중복없이 사용 할 수 있다.
    // 그리고 더 나아가 @Controller와 @ResponseBody의 용도를 합쳐놓은
    // @RestController를 사용한다면 둘을 하나의 어노테이션으로 사용할 수 있다.
}
