package com.example.springmvc.basic.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    // ModelAndView 타입으로 반환을 하는 방법
    public ModelAndView responseViewV1(){
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data","hello!");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    // String 타입으로 반환을 하는 방법
    // @Controller는 String을 반환할때 자동으로 그 이름의 View를 찾으므로
    // 이러한 방법을 사용할 수 있다.
    public String responseViewV2(Model model){
        model.addAttribute("data","hello!");
        return "response/hello";
    }

    @RequestMapping("/response/hello")
    // Spring에서는 Controller의 이름과 View의 이름이 같으면 void를 반환하여
    // 바로 호출함과 동시에 값을 넣어줄 수 있다.
    // 허나, 이 방법은 명시성이 떨어져 가독성이 낮고, 사용할 수 있는 상황이 딱 맞는 경우가 적어서
    // 많이 사용을 지양한다.
    public void responseViewV3(Model model){
        model.addAttribute("data","hello!");
    }
}
