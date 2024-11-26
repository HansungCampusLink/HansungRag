package com.example.demo.service;

import java.util.Map;

public final class NavigationConst {

    private NavigationConst() {}

    public static final String CHECK_NAV_PRE_PROMPT = """
        Please respond in JSON format. The json should represent whether the given input is a navigation-related query.
        Input: 
        """;

    public static final String CHECK_NAV_AFTER_PROMPT = """
        
        The response should strictly follow this structure:
        {
            "navigation": boolean
        }
        For example:
        - If the input is "공학관이 어디야?", respond with {"navigation": true}.
        - If the input is "체육관까지 어떻게 가?", respond with {"navigation": true}.
        - If the input is "오늘 날씨는 어때?", respond with {"navigation": false}.
        Do not include any extra text, explanations, or formatting outside of the JSON structure.
        """;

    public static final String MAP_KAKAO_LINK_TO = "https://map.kakao.com/link/to/";

    public static final String NAV_CHECK_SCHEMA = """
        {
            "type": "object",
            "properties": {
                "navigation": {
                    "type": "boolean"
                }
            },
        
            "required": ["navigation"],
            "additionalProperties": false
        }
        """;

    public static final String NAV_SCHEMA = """
        {
            "type": "object",
            "properties": {
                "destination": {
                    "type": "string"
                },
                "content": {
                    "type": "string"
                }
            },
            "required": ["destination", "content"],
            "additionalProperties": false
        }
        """;

    public static final Map<String, String> DESTINATION_TO_MAP_ID = Map.ofEntries(
            Map.entry("정문", "한성대정문,37.582355,127.011293"),
            Map.entry("우촌관", "17566958"),
            Map.entry("진리관", "17567370"),
            Map.entry("학송관", "17564465"),
            Map.entry("탐구관", "17564491"),
            Map.entry("한성 학군단", "2071025171"),
            Map.entry("연구관", "17564464"),
            Map.entry("지선관", "17565751"),
            Map.entry("공학관 A동", "17561891"),
            Map.entry("공학관 B동", "225891315"),
            Map.entry("인성관", "17565408"),
            Map.entry("창의관", "17566957"),
            Map.entry("낙산관", "17567225"),
            Map.entry("미래관", "17563225"),
            Map.entry("상상관", "423761522"),
            Map.entry("상상빌리지", "1359808250")
    );

    public static String getDestinationMapLink(String destination) {
        return MAP_KAKAO_LINK_TO + DESTINATION_TO_MAP_ID.get(destination);
    }

    public static final String NAVIGATION_INFO = """
        {
            "buildings": {
                "정문": {
                    "description": "한성대학교 정문입니다.",
                },
                "우촌관": {
                    "description": "한성대학교 우촌관입니다.",
                    "floors": {
                        "7F": "연구실, 상담실, 직원휴게실",
                        "6F": "연구처장실 / 산학협력단장실, 산학협력단 부단장실, 산학협력팀, 홍보팀, 교육과 로봇 연구소, 과제지원팀, 특허경영 지원사무실, 산학연컨소시엄센터 / 공학컨설팅센터, 홍보대사실, 인테리어디자인전공 대학원실습실, 기자실, 교육연구정책사업단",
                        "5F": "대외협력처장실, 대외협력팀, 교육혁신원장실, 연구지원팀, 교육혁신원 부원장실, 국방기술연구셑너, 이주의 인문학사업단, 노조사무실, 접견실",
                        "4F": "예술대학원 학과실, 교수실, 분장예술실습실, 회의실, 뷰티에스테틱실습실, 헤어디자인실습실, 락카실/휴게실, 뷰티색채학/웨딩플레너실습실",
                        "3F": "학사운영팀, 학사기획팀, 국제교류원장실, 재무회계팀, 국제교류팀 / 유학생지원팀 / 예비군실 / 입학관리팀 / 입학기획팀 / 연구실 / TLCⅡ학습역량개발팀, 교강사실, 상담실",
                        "2F": "학생회실(행정학과 / 부동산학과 / 사회과학대 / 무역학과 / 역사문화학부 / 인테리어 전공 / 문헌정보 / 영어영문학부 / 글로벌패션산업학부 / 국어국문전공 / 뷰티디자인학과 / 경제학과), 사회봉사단, 문헌정보전공 실습실, 인문과학, 사회과학 연구소, 고시반 열람실",
                        "1F": "자유열람실, 캠퍼스타운사업단, 우체국, 구내서점, 안내 및 주차관리실, 통신실, 학생회실(경영학부 / 무용학과 / 애니제품 / ICT디자인학부), 학부동문회실, 문서고(총무 / 학사 / 홍보), 대기실(여)"
                    }
                },
                "진리관": {
                    "description": "한성대학교 진리관입니다.",
                    "floors": {
                        "4F": "크리에이티브 인문예술대학(학장실 / 교학팀), 미래융합 사회과학대학(학장실 / 교학팀), 상상력 교양대학(학장실 / 교학팀), 상상력 인재학부(학장실 / 교학팀), 교양교육연구소",
                        "3F": "미래플러스대학(교학팀), 교수학습센터, 교육성과관리센터, 교육인증센터, 학생성공센터, 강의실, 컴퓨터실습실1,2, TCL-2, 평생교육원 학생회실, 패션실습실, 평생교육 연구지원센터, 혁신지원사업센터",
                        "2F": "애니제품/시각영상대학원, 강의실, 감사위원회, 총대의원회, HBS방송국, 총학생회, 총학생회 졸업준비국, 학생복지위원회, 컴퓨터실습실, 인문/사회과학연구원, 크리에이티브인문예술대학 학생회실, 미용실습실, 단과대학 통합문서고, 사물함실, PC실습실",
                        "1F": "시설지원팀, 학생장학팀, 사고와표현 행정실, 연구실, 모형실습실, 홍보자료실, 패션실습실, 실내디자인 실습실, 환경관리팀, 장애학생지원센터"
                    }
                },
                "학송관": {
                    "description": "한성대학교 학송관입니다.",
                    "floors": {
                        "2F": "원우회팀, 세미나실, 컴퓨터실습실, 강의실, 기계실, 대학원장실, 언어교육원, 교학부장실, 대학원교학팀, 기자재실",
                        "1F": "강의실"
                    }
                },
                "탐구관": {
                    "description": "한성대학교 탐구관입니다.",
                    "floors": {
                        "5F": "강의실1, 강의실2, 강의실3, 강의실4, 강의실5, 강의실6, 강의실7",
                        "4F": "교강사휴게실, 강의실1, 강의실2, 강의실3, 강의실4, 강의실5, 강의실6, 강의실7",
                        "3F": "강의실1, 강의실2, 강의실3, 강의실4, 강의실5, 강의실6, 강의실7",
                        "2F": "제1실습실, 제2실습실, 제3실습실, 강의실1, 강의실2, 강의실3, 실습상담실, 기자재실, 조교실, 컴퓨터실습실",
                        "1F": "강의실1, 강의실2, 강의실3, 강의실4, 강의실5, 강의실6, 대기실(남)",
                        "B1": "강의실1, 일반열람실, 강의실2, 휴게실, 강의실3, 매점",
                        "B2": "기계실, 조정실, 전기실, 저수조, 다용도실, 대기실(여)"
                    }
                },
                "한성 학군단": {
                    "description": "한성대학교 학군단입니다.",
                    "floors": {
                        "4F": "정보전략운영팀, 산학협력중심대학 사업단, 교수연구실",
                        "3F": "대회의실, 국제협력센터, 한중협력센터, 산학관리지원팀, 산학협렵단, 인력개발처장, INNO CAFE",
                        "2F": "처장실, 정책기획팀, 사무관리운영팀, 선진사회연구원, 법인사무국",
                        "1F": "학생서비스센터, 교육행정팀, 입학관리팀, 경리팀"
                    }
                },
                "연구관": {
                    "description": "한성대학교 연구관입니다. 교수님들 연구실이 위치하고있습니다.",
                    "floors": {
                        "HF": "옥상정원",
                        "7F": "교수연구실",
                        "6F": "교수연구실",
                        "5F": "교수연구실",
                        "4F": "교수연구실",
                        "3F": "교수연구실",
                        "1F": "전시실",
                        "B1": "전시실, 기계실",
                    }
                },
                "지선관": {
                    "description": "한성대학교 지선관입니다.",
                    "floors": {
                        "4F": "2학년 동양화 실기실, 회화과 교수연구실, 대학원 진채 실기실, 2학년 서양화 실기실, 한지 조형 실습실, 회화과 학생회실",
                        "3F": "3학년 동양화 실기실, 회화과 교수연구실, 대학원 동양화 실기실, 1학년 동양화 실기실, 3학년 서양화 실기실, 회화과 사무실",
                        "2F": "4학년 동양화 실기실, 사진실, 사진 스튜디오, 회화과 교수연구실, 대학원 서양화 실기실, 4학년 서양화 실기실, 기자재실",
                        "1F": "대학원 서양화 실기실, 1학년 서양화 실기실, 판화실, IT공과대학(학장실 / 교학팀), 대기실(여)"
                    }
                },
                "공학관 A동": {
                    "description": "한성대학교 공학관 A동입니다.",
                    "floors": {
                        "5F": "시뮬레이션 연구실, 서비스혁신 연구실, 인간공학 연구실, 통계분석 실험실, 품질관리 실험실, 제조공학 실험실, 경영과학 연구실, 전산설계실, 고객전략 연구실, 공학교육개발센터, 디지털 신호처리 연구실",
                        "4F": "IT융합 프로젝트실1·2·3, IT융합 PC실습실 1·2·3, IT융합 실험실습실, 열 유체 환경측정 실험실, 환경측정 분석실",
                        "3F": "듀얼공동 훈련센터 실습실1·2, 프로젝트실, 통신시스템 실험실, 자유실습실, 대학원연구실(IT융합), 대학원연구실(컴공), 대학원연구실(기계전자)",
                        "2F": "강의실, 교수연구실, 물리실험 강의실, 유지보수실, 세미나실",
                        "1F": "제1·2실습실, 디지털컨텐츠 제작실, 자유실습실, 휴계실, 매점, 실습조교 사무실, 대기실(남,여)",
                        "B1": "상상파크 플러스"
                    }
                },
                "공학관 B동": {
                    "description": "한성대학교 공학관 B동입니다.",
                    "floors": {
                        "6F": "강의실, 재료실험실, 메카트로닉스 실습실, 기계제작 실습실",
                        "5F": "세미나실, 실습실, 소모임 연구실, 제어공학 실험실, 생체공학 실험실, 연구과제 연구실",
                        "4F": "인공지능 실험실, 정보통신 연구실, 프린터룸, 정보처리 실습실, 종합설계 실습실",
                        "3F": "공장설계 연구실, 생산경영 연구실, 연구과제 연구실, 대학원 실습실",
                        "2F": "디지털시스템 실험실, 통신 실습실, 프로그래밍 실습실, 소모임실, 세미나실",
                        "1F": "컴퓨터 시스템 제1~3연구실, 임베디드 시스템 연구실, 개인학습실1·2, 실습실",
                        "B1": "학생회실(공과대학,컴퓨터공학부,기계전자공학부,IT융합공학부,스마트경영공학부)"
                    }
                },
                "인성관": {
                    "description": "한성대학교 인성관입니다. 한성대학교의 동아리가 모여있습니다.",
                    "floors": {
                        "5F": "극예술연구회, 경제학연구회, 로사리오, 한불회, 플래쉬, UBF, IVF, CAM, JOY, CCC, 동아리연합회",
                        "4F": "영화다솜, 한성타이포그래피연구회, 유스호스텔, 매나니로, 별조각, VISION, 이무기, H-LEP, 타임, PIG, 셈들",
                        "3F": "TRIAX-4000, 해랑사리우, UP, 한옴, 한얼, DC&M, 버팔로, 피닉스, 테니스부, 한검회, 터틀스",
                        "2F": "HBRG, SGS, BUG, 목표는상장, Team ODD, 탈패, 해외봉사단H.A.V.E, 들불, 한성, 낙산극회, NOD, 오케스트라"
                    }
                },
                "창의관": {
                    "description": "한성대학교 창의관입니다.",
                    "floors": {
                        "5F": "디자인대학(학장실 / 교학팀), 실기실(A.C.D), 그룹실기실(A.B), 컴퓨터실기실, 공작실, 스튜디오",
                        "4F": "디자인대학 실습조교실, 애니메이션 스튜디오(A.B.C.D.E), 제품 스튜디오(A.B), 제품실습공작실",
                        "3F": "실기실B, 의복구성실, CAD실, 패션정보실, 패션마케팅실, 패션소재실, 염색실",
                        "2F": "창작디자인실, 디자인개발실, 패션드로잉실, 패션상품기획실, 의복제작실",
                        "1F": "소강당, 패션정보분석실, 디자인대학 학생회실, 니트디자인실, 멀티디자인실",
                        "B1": "교직원식당, 학생식당, 매점, 이발소"
                    }
                },
                "낙산관": {
                    "description": "한성대학교 낙산관입니다.",
                    "floors": {
                        "4F": "체육관 관람석, 체력단련실",
                        "3F": "체육관, 체육관기자재실",
                        "2F": "대강당",
                        "1F": "무용학과 실습실",
                        "B1": "기계실"
                    }
                },
                "미래관": {
                    "description": "한성대학교 미래관입니다.",
                    "floors": {
                        "6F": "Design & IT 정보센터, 멀티미디어정보실, 그룹스터디실, 하늘정원",
                        "5F": "인문·자연과학자료실, 그룹스터디실, 상상커먼스",
                        "4F": "사회과학자료실, 그룹스터디실, 집중열람실",
                        "3F": "어문학자료실(언어,문학) 러닝커먼스, 그룹스터디실, 일반열람실(연결통로), 창의열람실",
                        "2F": "Information Desk, 학술정보팀, 학술정보관장실, 정보전산원장실, 정보화팀, 안내실",
                        "1F": "보존서고/ 학위논문실, 대학사료실, 디지털스튜디오, 실기수업콘텐츠제작실".
                        "B1": "DLC / ELC, 카페테리아, 통합기자재실, 학생상담센터, 자유실습실, 강의실(B103 / B104) (B106 ~ B108), 대기실(여), 미디어콘텐츠제작실(B105)",
                        "B2": "주차장, 중앙관제실, 기계실, 전기실, 대기실(여)"
                    }
                },
                "상상관": {
                    "description": "한성대학교 상상관입니다.",
                    "floors": {
                        "12F": "컨퍼런스홀, 멸가옥",
                        "11F": "연구실, 세미나실, 강의실, 박사원우회실, 석사원우회실",
                        "10F": "대학원장실, 교학부장실, 대학원교학팀, 대학원동문회실, 강의실, 컴퓨터실습실",
                        "9F": "이사장실, 법인사무국, 총장실, 비서실, 대회의실, 소회의실, 미팅룸, 네트워크실",
                        "8F": "경영기획팀, 전략평가팀, 총무인사팀, 중회의실, 미팅룸, 교무처장실, 기획처장실, 학생처장실, 총무처장실, 입학홍보처장실, 교수지원팀",
                        "7F": "강의실, 교강사휴게실, 학생휴게실",
                        "6F": "강의실",
                        "5F": "강의실",
                        "4F": "IPP사업단(단장실, 행정사무실, 컴퓨터세미나실, 컴퓨터실습실, 세미나실, 강의실)",
                        "3F": "컴퓨터실습실, 강의실, 컴퓨터실습실, 통합기자재실, 네크워크실, 스마트원격교육센터",
                        "2F": "강의실, 여학생휴게실, 팥고당",
                        "1F": "한성아키비움, 건강관리실",
                        "B1": "언어교육센터(강사실, 사무실, 조교실), 관람석, 주차장, 창업지원단(단장실, 사무실), 취업창업R&D센터, 진로상담실, 취업지원팀",
                        "B2": "취업창업상담실, 세미나실, SK청년비상 세미나실, 체육공간, G.X룸, 주차장"
                    }
                },
                "상상빌리지": {
                    "description": "한성대학교 기숙사입니다.",
                    "floors": {
                        "B1": "CU(편의점)"
                    }
                }
            }
        }
        """;

    public static final String NAV_PRE_PROMPT = """
        You are an assistant for navigation within Hansung University.
        Below is the navigation information for the university:
    
        Navigation Information:
        
        """
        + NAVIGATION_INFO
        + """
        
        User Query:
        
        """;

    public static final String NAV_AFTER_PROMPT = """
        
        Your task is to determine the destination building and provide a detailed response in JSON format.
        **Important Rules**:
        1. The "destination" field must be one of the building names provided in the "Navigation Information" section.
        2. If the user query does not match any of the buildings or their descriptions, respond with:
           {"destination": "none", "content": "한성대학교에는 찾고자하는곳이 존재하지 않습니다."}
    
        Strictly follow this response structure:
        {
            "destination": "<building name from Navigation Information>",
            "content": "<description>"
        }
    
        Example Responses:
        - If the input is "대강당이 어디야?", respond with {"destination": "낙산관", "content": "대강당은 낙산관 2층에 있습니다."}.
        - If the input is "한기준교수님을 만나려면 어디로 가야할까?", respond with {"destination": "연구관", "content": "교수은 연구관의 교수님 연구실에서 뵐 수 있습니다."}.
        - If the input is "서울대학교는 어떻게 가?", respond with {"destination": "none", "content": "한성대학교에는 찾고자하는곳이 존재하지 않습니다."}.
    
        Respond only in JSON format and strictly adhere to the structure. Do not include any additional text, explanations, or formatting outside of the JSON structure.
        """;
}
