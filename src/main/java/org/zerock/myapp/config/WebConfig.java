package org.zerock.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Controller
@Configuration

public class WebConfig implements WebMvcConfigurer{

//   @GetMapping(value = "/**/{path:[^\\.]+}")
//      public String redirect() {
//          return "forward:/index.html";
//      }
   
   
   //프론트에서 실험할때 데이터 바로 받아서 쓸 수 있게
   @Override
   public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**") // 모든 경로 허용
           .allowedOrigins("http://localhost:3000")
           .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
   }
   
}