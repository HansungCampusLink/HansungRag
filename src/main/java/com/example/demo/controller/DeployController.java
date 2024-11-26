//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//@RestController
//@RequestMapping("/deploy")
//public class DeployController {
//
//    private final String deployToken;
//
//    // 생성자를 통한 @Value 주입
//    public DeployController(@Value("${deploy.token}") String deployToken) {
//        this.deployToken = deployToken;
//    }
//
//    @PostMapping("/front")
//    public String deploy(@RequestHeader(value = "X-DEPLOY-TOKEN", required = true) String token) {
//        // 헤더에 포함된 토큰 검증
//        if (!deployToken.equals(token)) {
//            return "인증 실패: 잘못된 토큰입니다.";
//        }
//
//        StringBuilder output = new StringBuilder();
//
//        try {
//            // 배포 스크립트 실행
//            Process process = Runtime.getRuntime().exec("/home/ec2-user/front-deploy.sh");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//
//            process.waitFor();
//        } catch (Exception e) {
//            return "배포 중 오류 발생: " + e.getMessage();
//        }
//
//        return "배포 완료:\n" + output.toString();
//    }
//}
