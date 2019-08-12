# [Get Feed Photo - Android App]

- Author: Soobin Park

## Summary

- 기능 요약: Twitter의 내 친구를 포함하여 Home에 나오는 Feed의 목록을 보여주고, Feed 선택시 상세한 내용을 출력
- 동작 필요조건: Twitter에서 OAuth 1.0을 위해 발급한 Consumer Key, Consumer Secret, Token Key, Token Secret을 준비
- 앱 이름: Get Feed Photo(Twitter Feed 목록 조회 앱)
- MVP 패턴 반영
- minSdkVersion: 24
- targetSdkVersion: 29

## 기능 정리

- 앱을 진입하면 최근 Feed를 30개 조회해 옴
- 미리 지정해 둔 사용자의 Feed만을 조회해 올 수 있음(로그인 기능은 없음)
- 사진이 없는 Feed는 목록상에 한줄로 된 트윗만 나옴
- 사진이 있는 Feed는 목록상에 사진과 함께 한줄로 된 트윗이 나옴
- 목록을 맨 아래로 내리면, 상단에 더보기 버튼이 활성화 됨
- 더보기 버튼을 누르면 추가로 목록을 조회해 옴(30개)
- 목록의 아이템을 누르면 상세보기 화면으로 진입
- 상세보기 화면에서는 작성자 정보, 트윗 게재 날짜/시간, 사진(큰 화면), 트윗 전체 내용이 나옴
- 트윗 전체내용에서는 URL이 함께 담겨 있을 경우, URL을 클릭하면 웹 브라우저나 관련앱을 통해 확인 가능(링크)

## 각 파일 역할

- adapter/FeedRecyclerAdapter: Feed 목록 출력을 위한 RecyclerView의 Adapter
- adapter/contract/FeedRecyclerAdapterContract: Adapter에 대해 View와 Model의 역할을 정의한 Contract Interface
- common/Constants: 상수 정의
- data/model/FeedData: Data Object 정의(JSON 포함)
- data/remote/IRetrofitTwitter: Retrofit에서 사용하는 REST API의 Interface를 정의
- data/remote/Okhttp3Retrofit2Manager: Retrofit을 사용하여 REST API를 조회해서 데이터를 가져옴
- data/FeedDataRespository: 데이터를 보관하고 가공하여 제공
- data/IFeedDataControl: FeedDataRepository를 이용하기 위한 Interface 정의
- ui/contract/FeedDetailContract: Feed 상세보기 화면에 관련된 View와 Model의 역할 정의
- ui/contract/MainContract: Feed 목록보기 화면에 관련된 View와 Model의 역할 정의
- ui/presenter/FeedDetailPresenter: Feed 상세보기 화면과 관련된 로직을 수행하는 Presenter
- ui/presenter/MainPresenter: Feed 목록보기 화면과 관련된 로직을 수행하는 Presenter
- ui/FeedDetailActivity: Feed 상세보기 화면에 대한 Activity
- ui/MainActivity: Feed 목록보기 화면에 대한 Activity(앱의 진입 화면)

## 외부 라이브러리

- Gson: JSON 자료를 Parsing하기 위한 모듈
- OKhttp3: Retrofit2에서 통신하기 위한 모듈
- Retrofit2: REST API를 조회하기 위한 모듈
- OAuth: REST API 사용시 Authorization과 관련한 유틸을 사용하기 위한 모듈
- Glide: Image load를 위한 모듈

## 기타 참고사항

- Twitter Android SDK를 사용하지 않고, REST API만을 이용하여 구현