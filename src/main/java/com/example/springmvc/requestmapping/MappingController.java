package com.example.springmvc.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";
    }
    @RequestMapping(value = "/mapping-get-v1",method = RequestMethod.GET)
    // 일반적으로 RequestMapping은 http 메소드를 지정하지 않으면 모든 메소드 방식으로
    // 요청할 수 있다. 그리고 위와 같이 메소드를 따로 지정한다면 지정한 메소드로만 읽을 수 있도록 할 수 있다.
    public String mappingGetV1(){
        log.info("mappingGetV1");
        return "ok";
    }
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mappingGetV2");
        return "ok";
    }
    @GetMapping("/mapping/{userId}")
    // 경로 자체에 값을 가지고 이를 파라미터로 사용할 수 있는 방식
    // 최근 HTTP API는 리소스 경로에 식별자를 넣는 스타일 선호
    // 쿼리 파라미터를 받는 방식과 다르게 경로 변수를 설정함.
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mapping userId={}",data);
        return "ok";
    }
    // 변수명을 매핑의 경로 변수와 맞추면 PathVariable 어노테이션에서 이름을 맞춰줄 필요가 없음.
    //public String mappingPath(@PathVariable String userId){
    //        log.info("mapping userId={}",userId);
    //        return "ok";
    //    }
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    // 경로 변수 2개 이상 설정을 통해서 user,order을 연결하여 경로로 넘겨줄 수 있음.
    public String mappingPath(@PathVariable String userId,@PathVariable Long orderId){
        log.info("mappingPath userId={}",userId);
        log.info("mappingPath orderId={}",orderId);
        return "ok";
    }

    @GetMapping(value = "/mapping-param", params = "a=1")
    // 쿼리 파라미터를 매핑정보에 넣어 해당 파라미터가 들어와야만, 호출할 수 있도록 함.
    public String mappingParam(){
        log.info("mappingParam");
        return "ok";
    }
    @GetMapping(value = "/mapping-header", headers = "a=1")
    // 헤더에 키 벨류 값을 읽어 있을 경우에, 호출함
    public String mappingHeader(){
        log.info("mappingHeader");
        return "ok";
    }

    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    // 컨텐츠 타입 헤더기반 추가 매핑 media type
    // consumes -> 주는 타입
    public String mappingConsumes(){
        log.info("mappingConsume");
        return "ok";
    }

    @PostMapping(value = "/mapping-produce", produces = "text/html")
    // 컨텐츠 타입 헤더기반 추가 매핑 media type
    // produce -> 받는 타입
    // 클라이언트가 받을 수 있는 타입일때, 호출함
    public String mappingProduces(){
        log.info("mappingProduces");
        return "ok";
    }

}
