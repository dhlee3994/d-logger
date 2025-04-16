# D-Logger

Spring 환경에서의 HTTP 요청/응답 로깅 라이브러리입니다.

## 왜 만들었나요?

Spring 환경에서 기본적인 로깅 기능은 HTTP 헤더 중심으로, HTTP 바디는 로깅하지 않습니다.  
이로 인해 디버깅 및 장애 분석 시 HTTP 요청 바디 유실로 인한 장애 재현의 어려움이 있습니다.  
또한, HTTP 요청/응답 바디에 민감 정보가 포함될 수 있어, 이를 안전하게 로깅하기 위한 기능이 필요합니다.  

## 주요 기능

- HTTP 요청/응답 바디 로깅
- 운영환경에서 예외 상황만 로깅
- URI 기반 조건부 로깅 (include/exclude)
- 민감 정보 마스킹
- Spring Boot Auto Configuration 지원
