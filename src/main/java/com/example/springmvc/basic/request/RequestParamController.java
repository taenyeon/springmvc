package com.example.springmvc.basic.request;

import com.example.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    // 파라미터 값을 받는 여러 방법

    // 기존의 servlet Request와 Response의 getParameter 메소드를 통해 파라미터 얻음.
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");
    }

    // @RequestParam 어노테이션을 통해서 getParameter 메소드보다 더 편하게 파라미터를 얻을 수 있음.
    // @Controller의 경우, @RestController와 다르게 String을 받았을때, 그 이름의 View를 반환한다.
    // 이때, @ResponseBody 어노테이션을 통해서 View가 아닌 String을 그대로 보낼 수 있게 한다.
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    // @RequestParam 어노테이션을 통해서 getParameter 메소드보다 더 편하게 파라미터를 얻을 수 있음.
    // 파라미터의 이름과 받는 변수의 이름이 일치하면 @RequestParam의 인자를 생략할 수 있다.
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // 스프링에서는 아무것도 적지 않아도 이름만 맞다면 자동으로 찾아주는 기능도 있다.
    // 그래서 이렇게 타입과 이름만으로 바로 파라미터를 적용할 수 있다.
    // 하지만 이렇게 한다면 값을 페이지에서 파라미터로 받아온것인지 명확하지 않고, 이로 인해 가독성이 떨어질 수 있다.
    // 그래서 팀원의 스프링 경험, 역량에 따라서 적절하게 사용하는것이 좋다.
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // required 속성에 true를 넣으면 파리미터를 필수적으로 기입하도록 할 수 있다.
    //예를 들어 아이디에 따른 회원 정보를 조회한다고 할떼, 아이디를 기입하지 않으면 조회할 수 없도록 하는 속성이다.
    // default value = true
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // 위와 같이 코드를 작성했을때, 문제가 발생하는 경우가 있다.
    // 예를 들어 username에 빈 값("")을 넣거나 age에 값을 안넣어 NULL인 경우에 이를 처리해주는 과정이 필요하다.
    // 이를 간단하게 값을 안넣으면 @RequestParam 어노테이션의 속성인 defaultValue를 통해 지정한 값을 넣어줄 수 있다.
    // 물론, 빈값("")도 처리를 해준다.
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(defaultValue = "guest") String username,
                                      @RequestParam(defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    // 파라미터를 Map에 묶어서 값을 꺼내올 수도 있다.
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    // 기존 Data 입출력 방법
  @ResponseBody
  @RequestMapping("/model-attribute-v1-before")
    public String modelAttributeV1_Before(@RequestParam String username,
                                   @RequestParam int age){
      HelloData helloData = new HelloData();
      helloData.setUsername(username);
      helloData.setAge(age);

      log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
      // 롬복의 @Data 어노테이션을 통해 ToString도 지원을 해주므로 아래와 같은 방법도 가능하다.
      log.info("helloData={}",helloData);

return "ok";
  }

  // @ModelAttribute 어노테이션을 통한 Data 입출력 방법
    // 어노테이션을 통해 자동으로 HelloData에 해당하는 username 과 age가 get,set 된다.
    @ResponseBody
    @RequestMapping("/model-attribute-v1-after")
    public String modelAttributeV1_After(@ModelAttribute HelloData helloData){
        // 스프링은 어노테이션을 먼저 실행하므로
        // @ModelAttribute 어노테이션을 통해 요청 파라미터의 이름으로 객체의 프로퍼티를 찾고
        // 이 프로퍼티의 setter를 호출하여 파라미터 값을 자동으로 바인딩(입력)해준다.
        // 만약 타입이 안맞으면 바인딩 예외가 발생하게 된다. 이는 검증 과정을 통해서 해결하여야 한다.
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }

    // @ModelAttribute도 @RequestParam과 같이 생략이 가능하다.
    // 이때, @RequestParam는 단순한 타입(String,int,Integer...) @ModelAttribute는 그 외의 복잡한 타입들을 처리할 수 있으니,
    // 각각의 타입에 따라 해당 어노테이션이 적용된다.
    // 예외로 @ModelAttribute는 argument resolver로 저정한 타입은 생략이 불가능하다.
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){
        log.info("username={}, age={}",helloData.getUsername(),helloData.getAge());
        return "ok";
    }
}
