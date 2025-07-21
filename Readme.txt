◆Spring Securityについてのメモ

◆認証（Authentication）とは？
ログイン画面でユーザー名とパスワードを入力し、利用者テーブル（users）などのDBと照合して本人確認を行うこと

◆applicationContext-security.xmlの補足
Spring Security機能を使用するため、Bean定義を行う。

補足１：<sec:http pattern="/resources/**" security="none" />
　⇒静的リソースをセキュリティ対象外とする
　⇒ログインしていなくても、resources配下にアクセス可能
　⇒この設定がないとログイン前にCSSが崩れたりすることがあるらしい

補足２：<sec:http>内部
　⇒「<intercept-url pattern="/**"」は、すべてのURLに対して認証を要求する
　⇒「access="isAuthenticated()"」は、ログイン済みであればアクセス可能

補足３：<form-login />のみ定義
　⇒Spring Security提供のデフォルトのログインフォームを使用する
　⇒「/login」がログインページ
　⇒認証成功後はデフォルトで「/」にリダイレクト
　※カスタムログインページを使いたい場合はlogin-page属性を指定する
　※リダイレクト先URLを定義したい場合、default-target-url属性を指定する

補足４：<sec:authentication-manager />
　⇒認証処理の中心となるAuthenticationManagerを定義
　⇒DB認証を使う場合は<authentication-provider>を追加する必要あり
 ⇒@Service("userDetailsService")のBeanを紐づけている

補足５：DIコンテナに登録
作成したapplicationContext-security.xmlをweb.xmlのコンテキストローダーに指定する

◆サーブレットフィルタの補足
Spring Securityが提供しているサーブレットフィルタをweb.xmlに登録する

※applicationContext-security.xmlにてhttpタグを定義すると、
SpringSecurityの設定が構成され、結果としてspringSecurityFilterChainという名前のフィルタBeanが内部で作成される
このBeanをweb.xml（サーブレットコンテナ）に登録して有効にする

補足１：
<filter-name>springSecurityFilterChain</filter-name>
<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
　⇒DelegatingFilterProxyを使用して、DIコンテナで管理しているBeanをサーブレットコンテナに登録する
　⇒springSecurityFilterChainはSpring Securityが内部で管理しているBeanの名前を指定している
　⇒このBeanはapplicationContext-security.xmlに定義した内容を保持するBeanとなり、サーブレットコンテナで動作する

補足２：
<filter-name>springSecurityFilterChain</filter-name>
<url-pattern>/*</url-pattern>
Spring Securityを適用させるURLのパターン
上記はすべてのURLパターンに対してSpring Securityを有効にしている（ただし、resources配下を覗く）


◆処理の流れ（デフォルトのログイン画面を使用）
1.Spring Securityのフィルターがリクエストを検査
　　springSecurityFilterChainにより、全てのリクエストはSpring Securityのフィルターを通る
　　認証が必要なURLに対して未認証の場合、デフォルトで/loginにリダイレクト

2.ログインフォームから認証要求を送信（POST /login）

3.UsernamePasswordAuthenticationFilterが起動
　　POST /loginリクエストをこのフィルターが処理
　　AuthenticationManager（実体はProviderManager）に認証要求

4.AuthenticationManager → DaoAuthenticationProviderを呼び出す
　　AuthenticationManagerは複数の認証オブジェクトを保持する
　　順次この認証オブジェクトを呼び出して、認証処理を行う
　　デフォルトの認証オブジェクトでは、DaoAuthenticationProviderがある

5.DaoAuthenticationProviderがUserDetailsServiceを呼び出す
　　applicationContext-security.xmlの<sec:authentication-provider>タグに定義したカスタムUserDetailsServiceを呼ぶ
　　loadUserByUsername(Stringusername)メソッドを呼び出して、ユーザー情報（UserDetails）を取得

6.UserDetailsServiceでDBからユーザー情報を取得
　　該当ユーザーがいなければUsernameNotFoundExceptionがスローされ、認証失敗

7.PasswordEncoderを使ってパスワード検証
　　DBから取得したUserDetailsのpasswordと、フォームからのパスワードを比較
　　不一致ならBadCredentialsExceptionがスローされ、ログイン失敗

8.認証成功時
　　SecurityContextHolder.getContext().setAuthentication(...)でSecurityContextに認証情報を保存
　　HttpSessionSecurityContextRepositoryによりHttpSessionに保存される

9.ログイン成功後のリダイレクト
　　applicationContext-security.xmlのdefault-target-urlが設定されていれば、そこへリダイレクト
　　指定がなければ「/」にリダイレクト

◆その他
・CSRFトークン
Spring Securityは、POST・PUT・DELETEなどの状態変更リクエストに対してCSRFトークンの検証を行う。
そのトークンがリクエストに含まれていないまたは一致しない場合、403を返すらしい。
⇒解決策：Spring Securityが自動で用意するCSRFトークンをフォームに埋め込む
⇒<sec:csrfInput/>をフォーム内に埋め込む

・{noop}
data.sqlのパスワードの前に{noop}を付与している
これはハッシュ化せず平文として使用するという意味

・認証ユーザー名を画面表示
【JSTL ＋ Spring Securityタグライブラリ】を使用することで認証したユーザーにアクセスできる
「<sec:authentication property="name" />」とすることで、Authentication#getName()（※UserDetails.getUsername()）にアクセスできる

・パスワードのハッシュ化
applicationContext-security.xmlにパスコードエンコーダーのBeanを用意して
「sec:authentication-manager」に参照させることでパスワードをハッシュ化して管理できる
認証処理にて内部でBCryptPasswordEncoderのmatchesメソッドを呼び出し、入力値とDBにあるパスワードのハッシュ値を照合する

・ログアウト
<sec:logout logout-url="/logout" logout-success-url="/" invalidate-session="true" />
上記を設定するだけでログアウト機能が有効になる
ログアウト時は「/logout」にリクエストし、ログアウト成功時は「 / 」にリクエストする
また、invalidate-sessionをtrueにすることで、内部でsession.invalidate()が呼ばれてセッション破棄される

・認証情報をハンドラ引数で取得したいとき
１．<mvc:annotation-driven>にAuthenticationPrincipalArgumentResolverのBeanを追加
２．ハンドラ引数にて「@AuthenticationPrincipal」を付与して取得したい型を指定する

・認証イベントのハンドリング
１．認証成功イベント： InteractiveAuthenticationSuccessEvent
　　ルートアプリケーションコンテキスト配下にイベント取得用のBeanを用意する
　　@EventListenerを付与したメソッドの引数に上記イベントを指定することで、画面遷移を除いた認証処理がすべて成功した通知を取得できる

２．認証失敗イベント： AbstractAuthenticationFailureEvent
　　すべての認証失敗イベントの親クラス
　　AbstractAuthenticationFailureEvent
　　├── AuthenticationFailureBadCredentialsEvent
　　├── AuthenticationFailureDisabledEvent
　　├── AuthenticationFailureLockedEvent
　　├── AuthenticationFailureExpiredEvent
　　├── AuthenticationFailureCredentialsExpiredEvent
　　├── AuthenticationFailureProviderNotFoundEvent
　　├── AuthenticationFailureServiceExceptionEvent

