英単語テスト作成プログラム

# 準備
\src\main\resources\config.properties の<br>
BASE_DIRに \src\main\resources\File\ <br>
RESULT_DIRに \src\main\resources\result<br>
のFull Pathを記述

# 実行
executor/Main.java がテスト作成用実行ファイル

scraper/Scrape.javaは参照元CSVファイル作成用のプログラム<br>
https://ukaru-eigo.com/ <br>
上記サイトの表から「番号」「単語」「意味」を取得し、CSVファイルを作成する<br>
実行時引数に<br>

[スクレイプ対象ページURL　番号の項目名　単語の項目名　意味の項目名]<br>

を設定し実行

# 流れ
参照したい単語帳、section、出題数を入力<br>
　　↓<br>
条件に従いランダムに単語を抽出<br>
　　↓
問題と解答の2ファイルをWord文書で出力