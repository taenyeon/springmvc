package com.example.springmvc.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    // 같은 매핑일 경우, 요청방식에 따라 다른 메소드를 호출할 수 있다.
    // 이를 통해 사용할 메소드를 그에 맞는 요청정보와 묶을 수 있다.
    // 이러한 방식은 계층 구조를 짜기에 좋고 개발자가 보기에 편리한 장점이 있다.

    // GET 방식
    // 유저 전체 조회
    @GetMapping
    public String user(){
        return "get users";
    }
    // POST 방식
    // 유저 생성
    @PostMapping
    public String addUser(){
        return "post user";
    }
    // GET 방식
    // 특정 유저 조회
    @GetMapping("/{userID}")
    public String findUser(@PathVariable String userID){
        return "get userId="+userID;
    }
    // PATCH 방식
    // 유저 수정
    @PatchMapping("/{userID}")
    public String updateUser(@PathVariable String userID){
        return "update userId="+userID;
    }
    // DELETE 방식
    // 유저 삭제
    @DeleteMapping("/{userID}")
    public String deleteUser(@PathVariable String userID){
        return "delete userId="+userID;
    }

}
