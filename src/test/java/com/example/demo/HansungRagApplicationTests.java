package com.example.demo;

import com.example.demo.dto.Document;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class HansungRagApplicationTests {

	@Autowired
	private VectorStore vectorStore;

	@Test
	@Disabled
	void contextLoads() {
		Document document = new Document();
		document.setContent("# 전북특별자치도평생교육장학진하원\n" +
				"\n" +
				"# 육성방안 담구지원\n" +
				"\n" +
				"신청기간 2024.06.12(수) ~ 06.30.(일) 18.00까지 온라인 신청\n" +
				"\n" +
				"|모집대상 및 인원|활동 및 지원내용|신청방법 및 문의처|\n" +
				"|---|---|---|\n" +
				"|전부특별자지도 축신 대략생 (규학생 포람) 13개팀 정도 (팀당 3덩으로 구성)|전북 선도산입 발전 유성방안에 더한 탑구 빛아이디어 제시 팀당 300만원 지원(담구활동비) 결과보고 우수팀 시심 주가지금) 최우수 ; 1개팀 100만원 무수: 3개팀 60만원 장려; 3개팀 40만원|온라인 신청만 기늄 053-276-6307/8309|");
		document.setDate(Timestamp.valueOf("2024-06-11 10:11:25.0"));

		System.out.println(document.getDate().getTime());
		document.setTitle("2024 전북특별자치도평생교육장학진하원 육성방안 담구지원");
		document.setLink("https://www.hansung.ac.kr/bbs/hansung/143/261648/artclView.do");
		document.setAuthor("학생장학팀");
		document.setCategory("한성공지");

		var springAiDocument = document.toSpringAiDocument();

		vectorStore.add(List.of(springAiDocument));
	}

	@Autowired
	private MaumChatModel maumChatModel;

	@Test
	void chatTest() {
		long start = System.currentTimeMillis();
		Prompt prompt = new Prompt("2024 공모전에 대해 알려줘");
		ChatResponse call = maumChatModel.call(prompt);
		System.out.println("response = " + call.getResult().getOutput().getContent());

		long end = System.currentTimeMillis();

		System.out.println((end - start) / 1000);
	}

	@Autowired
	private OpenAiChatModel openAiChatModel;


	@Test
	void openChatTest() {
		long start = System.currentTimeMillis();
		Prompt prompt = new Prompt("2024 공모전에 대해 알려줘");
		ChatResponse call = openAiChatModel.call(prompt);
		System.out.println("response = " + call.getResult().getOutput().getContent());

		long end = System.currentTimeMillis();

		System.out.println((end - start) / 1000);
	}

}
