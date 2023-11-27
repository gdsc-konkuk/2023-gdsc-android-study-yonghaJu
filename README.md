## 🛠️ ViewModel 테스트 자동화 + @Before @After 어노테이션 제거 리펙토링 과정 💡

## 서론

GDSC Konkuk 안드로이드 스터디 5주차 과제는 자율 과제이다.

개인마다 하고 싶었던 것, 추가하고 싶은 것, 리펙토링 하고 싶은 것들에 대해 자유롭게 적용해보고 정리하면 되기 때문에 나는 5주차 과제로 뷰모델에 Unit 테스트 적용 및 GitAction을 통한 자동 테스트를 과제로 진행하기도 했다.

해당 과정에서 테스트 코드에 직접적인 연관이 없는 로직과 객체 관리에 대한 책임을 분리할 수 있는 방법에 대해 고민해보았고 내가 선택한 방법을 추가적으로 적어보았다.

__제가 선택한 방법은 정답이 아니며 언제든 피드백 주시면 감사하겠습니다.__

---

<br>

## __Unit Test 작성__

### 의존성 추가

viewModel 안에서 사용하는 로직은 대부분 Flow를 사용해 상태를 보관하기 때문에 아래와 같이 __kotlinx-coroutines-test__ 의존성을 추가해줘야 한다.
Kotest 등 코틀린으로 작성된 테스트 라이브러리도 있지만 프로젝트 생성 시 기본으로 추가되어있는 Junit을 사용해 테스트를 진행해보자.

추가적으로 collect 코루틴을 만드는 편리한 API와 Flow를 테스트하는 기타 편의 기능을 제공하는 서드파티 Turbine 라이브러리도 추가해주자.

```kotlin
    testImplementation("junit:junit:$junitVerison")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineTestVersion")
    testImplementation("app.cash.turbine:turbine:$turbineVersion")
```

---


<br>


### 더블 만들기

테스트 코드를 작성할 때 각 테스트는 실제 객체에 의존하면 안된다 따라서 실제 객체를 대신할 스턴트맨 같은 존재가 필요한데 이를 더블이라고 부른다.

따라서 뷰모델에서 사용하는 레포지토리들에 대한 Fake 객체를 만들어야 하는데 대부분 Flow를 반환하기 때문에 어떻게 Fake 객체를 만들지 고민했고 공식 문서를 살펴 보았다.

공식 문서 내용 중 [테스트 중 Flow 수집하기] 파트에서 다루는 예제 코드를 보고 답을 얻을 수 있었다.

__StateFlow__ 와 __SharedFlow__ 모두 __Flow__ 인터페이스를 상속하기 때문에 Fake 객체 내부에서 Shared, State Flow를 사용해 데이터를 보관하고 Flow로 타입 캐스팅 반환해주면 된다.

```kotlin
class FakeTodoRepository : TodoRepository {
    
    // 내부적으로 데이터를 보관
    private val todos = MutableStateFlow(listOf<TodoItem>())
    
    // 반환할 때 Flow로 타입으로 반환
    override fun getTodos(): Flow<List<TodoItem>> = todos

    override suspend fun setTodo(todoItem: TodoItem) {
        todos.emit(todos.value.map { item -> if (item.id == todoItem.id) todoItem else item })
    }

    // ...
}
```

그리고 테스트를 하는 쪽에서는 해당 코드를 수집해줘야 하는데 공식문서에서 작성된 코드는 아래와 같다.

```kotlin
    // Create an empty collector for the StateFlow
    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
        viewModel.score.collect()
    }
```

> 주의: 이러한 옵션으로 만든 StateFlow를 테스트할 때는 테스트 중에 수집기가 하나 이상 있어야 합니다. 그렇지 않으면 stateIn 연산자는 기본 흐름 수집을 시작하지 않고 StateFlow의 값은 업데이트되지 않습니다.



[테스트 중 Flow 수집하기]: https://developer.android.com/kotlin/flow/test?hl=ko#continuous-collection

---

<br>



### 테스트 코드 작성하기

테스트 과정에서 suspend 함수 실행하기 위해서는 아래와 같은 runTest 코드를 실행해줘야 한다.

이때 컨텍스트를 __EmptyCoroutineContext__ 로 받는 부분이 떄문에 dispatcher를 어떻게 정해줘야 할 지 고민이었지만 일단 공식문서 코드를 보며 뷰모델 테스트 코드를 마저 작성해보자

```kotlin
public fun runTest(
    context: CoroutineContext = EmptyCoroutineContext,
    timeout: Duration = DEFAULT_TIMEOUT,
    testBody: suspend TestScope.() -> Unit
): TestResult {
    check(context[RunningInRunTest] == null) {
        "Calls to `runTest` can't be nested. Please read the docs on `TestResult` for details."
    }
    return TestScope(context + RunningInRunTest).runTest(timeout, testBody)
}
```

<br>
<br>

### 작성한 뷰모델 테스트 코드 및 이슈

```kotlin
// ...
    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `랜덤 사진을 눌렀을 때 랜덤하게 사진 URL이 받아와 지는지`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            editViewModel.userPhoto.collect()
        }

        // When
        editViewModel.setRandomPhoto()

        // Then
        assertEquals(FakePhotoRepository.RANDOM_URL, editViewModel.userPhoto.value)
    }
// ...
```

__EditViewModel__ 에서 랜덤한 사진을 가져오게하는 부분 테스트 코드를 작성해 실행시켜보니 아래와 같은 에러가 발생했다

> Exception in thread "Test worker" java.lang.IllegalStateException: Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used


<br>

보아하니 테스트가 실행되는 코루틴 컨텍스트 메인으로 지정해주지 않아서 모듈 초기화에 실패했다는 것으로 판단된다.

<br>

실제로 __EditViewModel__ 에서 사용하고 있는 __userPhoto: StateFlow<String?>__ 객체의 선언부분을 보면  __viewModelScope__ 를 사용해서 stateFlow를 만드는데 뷰모델 스코프는 기본적으로 __Dispatcher.Main.immediate__ 디스페처를 사용하기 때문에 테스트가 안되는 것으로 이해된다.

```kotlin
    // EditViewModel.kt
    val userPhoto = userRepository.userPhotoUrlFlow.stateIn(
        scope = viewModelScope, // Dispatcher.Main.immediate 사용
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT),
        initialValue = null,
    )
```

<br>

에러를 해결하는 여러 방법중 많이 채택하고 있는 방법을 찾아 사용해보았다. 아래와 같이 __TestWatcher()__ 클래스를 상속한 클래스를 만든다.

이 클래스는 테스트에 시작부분과 종료 시점에서 각각 starting, finished 콜백이 실행되게 할 수 있기 때문에 이 안에서 ```Dispatchers.setMain(testDispatcher)```, ```Dispatchers.resetMain()``` 함수를 호출해준다.

그리고 테스트 클래스 안에 MainDispatcherRule 객체를 생성하고  ```@get:Rule``` 어노테이션을 붙여주고 테스트를 재실행하면 에러가 발생하지 않게 된다.

```kotlin
// 테스트 룰
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

// 테스트 코드
class EditViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() 
    // ...
}
```

---

<br>



### @After, @Before -> Rule 리펙토링

#### __리펙토링이 필요하다 생각한 이유__

1. __@Before, @After__ 어노테이션 코드와 같은 역할을 하지만 로직이 분산됨.
2. 테스트와 직접적인 연관이 없는 로직과 객체들이 공개됨.

이전에 만들었던 __MainDispatcherRule__ 클래스에서 사용된 __Dispatcher.resetMain()__ 함수의 구현부를 보았을 때 아래와 같은 주석이 있었다.

> (...) and so should be used in tear down (@After) methods.

즉, __TestWatcher__ 클래스는 테스트 전후 동작을 설정해줄 수 있고, 이는 테스트 클래스 안에서 사용했던 __@Before__ __@After__ 어노테이션으로도 같은 역할을 할 수 있다.

기존 테스트 코드에서 각 테스트는 다른 테스트로부터 독립적이어야 했기 때문에 __@Before__ 어노테이션을 붙인 setUp() 함수를 작성해 객체들을 초기화해줘야 했다.

하지만 객체들이 점차 많아진다면 테스트 클래스안에 초기화, 메모리 해제 등 테스트와 직접적인 관련이 없는 코드들의 양이 많아질 수 있기 때문에 해당 코드들을 TestWatcher 클래스에 역할 위임하기로 했다.

```kotlin
// 기존 테스트 코드
class EditViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeUserRepository: UserRepository
    private lateinit var fakePhotoRepository: PhotoRepository // 테스트에 사용되지 않을 객체
    private lateinit var setRandomPhotoUseCase: SetRandomPhotoUseCase // 테스트에 사용되지 않을 객체
    private lateinit var editViewModel: EditViewModel

    // 테스트에 직적적인 연관이 없는 사전 로직
    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        fakePhotoRepository = FakePhotoRepository()
        setRandomPhotoUseCase = SetRandomPhotoUseCase(fakePhotoRepository, fakeUserRepository)
        editViewModel = EditViewModel(
            fakeUserRepository,
            setRandomPhotoUseCase,
        )
    }
        
    @Test
    fun test1() {
        // ... 
    }
}
```

<br>

### 리펙토링 후 얻은 장점

- __책임 분리: 직접적인 테스트 이외의 책임을 테스트 코드애서 분리__
    
    객체의 생성이나 메모리 관리 같은 코드들이 많아져 테스트 클래스가 난독화되는 것을 방지할 수 있음

- __캡슐화: 실제로 사용되지 않고 생성에만 필요한 fake 객체를 감춤__

    fakePhotoRepository, setRandomPhotoUseCase 같이 뷰모델 생성시에 필요하지만 직접 접근할 필요가 없는 객체들을 private 하게 감출 수 있다.

```kotlin
// 책임 분리 리펙토링 테스트 코드
class EditViewModelTest {

    @get:Rule
    val editViewModelTestRule = EditViewModelTestRule()

    @Test
    fun test1() = with(editViewModelTestRule){
        // ... 
    }
}

// 테스트 룰
// MainDispatcherRule을 상속해서 기존 동작(setMain)을 유지
class EditViewModelTestRule : MainDispatcherRule() {
    
    lateinit var editViewModel: EditViewModel               // 테스트코드에 공개 시킬 객체 
    lateinit var fakeUserRepository: FakeUserRepository     // 테스트코드에 공개 시킬 객체 
    
    override fun starting(description: Description) {
        super.starting(description)

        val fakePhotoRepository = FakePhotoRepository()
        fakeUserRepository = FakeUserRepository()
        val setRandomPhotoUseCase = SetRandomPhotoUseCase(fakePhotoRepository, fakeUserRepository)
        editViewModel = EditViewModel(
            fakeUserRepository,
            setRandomPhotoUseCase,
        )
    }
}
```

---

<br>

## 테스트 자동화

### GitAction으로 PR 단위로 테스트 수행

__Cucumber__ 등 서트파티 라이브러리를 사용하면 UI 테스트 자동화와 BDD(Back-End Driven Development)가 가능해진다.
BDD는 서버로부터 UI를 형식을 제공받기 때문에 앱을 업데이트, 배포하지 않아도 변경사항을 적용시킬 수 있는 장점이 있다.

하지만 이번 스터디 프로젝트에서는 간단하게 GitAction을 통해 PR 단위로 테스트 자동화하도록 하겠다.

방법은 간단한데 레포지토리 root 아래에 .github/workflows/ci.yml 파일을 만들어주면 된다.

겼었던 이슈들은 아래와 같다.

1. jdk 버전 호환 x, 11 -> 17로 업그레이드했더니 문제 해결
2. local.properties 파일 읽기 실패

    build.gradle 에서 api access token 을 로컬 프로퍼티에서 가져오고 있었기 때문에 git secret 에 추가해줬다.

<br>

추가적으로 apk 를 만들어 github에 업로드하거나 slack, discord 에 봇을 만들고 api를 요청하면 아래와 같이 로컬에서 확인할 수 있는 테스트 결과를 보낼 수 있다.

<img width="1025" alt="image" src="https://github.com/gdsc-konkuk/2023-gdsc-android-study-yonghaJu/assets/65655825/8ef326f4-8e17-41fd-93f9-43c72755c38f">

```yml
name: gdsc_test_ci
on:
  pull_request:
    branches: [ "main" ]
  workflow_dispatch:
    inputs:
      tags:
        description: 'Test scenario tags'

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'zulu'
          cache: gradle

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Create Local Properties
        run: echo '${{ secrets.LOCAL_PROPERTIES }}' > ./local.properties

      - name: Start gradlew test
        run: ./gradlew test
```

---
