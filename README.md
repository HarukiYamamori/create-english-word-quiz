# 英単語テスト作成プログラム

# 準備
\src\main\resources\config.properties の<br>
BASE_DIRに \src\main\resources\File\ <br>
RESULT_DIRに \src\main\resources\result<br>
のFull Pathを記述

# 実行
### テスト作成用プログラム
executor/Main.java を実行

引数： なし

### CSVファイル作成用プログラム
scraper/Scrape.java を実行<br>
https://ukaru-eigo.com/ <br>
上記サイトの表から「番号」「単語」「意味」を取得し、CSVファイルを作成する<br>

引数： [スクレイプ対象ページURL　番号の項目名　単語の項目名　意味の項目名]<br>

# 流れ
参照したい単語帳、section、出題数を入力<br>
　　↓<br>
条件に従いランダムに単語を抽出<br>
　　↓<br>
問題と解答の2ファイルをWord文書で出力
