#チームAndroid_devより
> 2015/09/15
手書きメモ帳の進捗報告

#### 先週からの進捗
- 全員でSkypeを使って会議した
- 各自の今後の仕事を改めて決め、完成に向けて動き出した
 
#### 各メンバータスク状況
###### [鈴木]
- (保存機能ができ次第)フォルダ分け機能を実装する

###### [濱崎]
- SQLiteを使ってデータを扱うことにしたので既存の保存機能を手直しする

###### [平原]
- 書き込み画面のスタイルを完成版に変更する

###### [山崎]
- 絵を描く機能を実装した
- 画像を保存してプレビュー表示などの機能を実装する

#### Skypeで会議した結果
- 手書き入力を断念

	OSSを色々と探してみたが、手書き文字認識が多く、ほしかったAndroid対応の手書き入力のOSSが思いのほか見つからなかった。

	書き込んだ文字を画像として認識させ、手書き文字認識のエンジンに投げるやC++で書かれた手書き文字認識のOSSをAndroidに対応させるなども思いついたが、できる気がしなかった。

	画像として認識させるには、1文字をどのように抜き出すかのアルゴリズムが必要になる

	C++のOSSをAndroidに対応させる、学習コスト的に間に合わない

- 方針として

	手書き入力を除く最低限の機能実装をしてしまおう、という方針に

#### 反省
- スケジュールが役に立っていなかった


#### 改善策

- スケジュールをしっかり立てる
- 1日1slack

#### 今後の方針

- 各自の分担を決めて1週間で進捗を生み出す。
- できなければ、そこから反省点を出して次週に生かす

#### 役割分担

- blog担当: 鈴木、濱崎
- スライド担当: 平原
- 記録担当: 山崎

#### 来週までの目標

- 引き続き各自作業

#### 今後のスケジュール

9月22日　夏休み終了(修正)
9月29日 最終発表
10月11日・12日　紅華祭