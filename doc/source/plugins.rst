============
 プラグイン
============

Diversityはプラグインにより、コマンドを追加することができます。
プラグインは以下の2つの方法で追加できます。

1. ファイルシステムプラグイン
2. jarパッケージ

   $DIVERSITY_HOME/plguins以下に

ディレクトリ構造
================


plugins 
　 <groupId:artifactId:version>
      pom.xml
      target/classes
   artifacts
     //ivyのディレクトリ

依存関係の解決
==============

* Grape(ivy)を利用し、mavenのpom.xmlに記載された依存関係の解決を行う。
* 現在はパッケージングされたjarに対してのみ、依存性の解決ができる。
* META-INF/plugin.groovyを解析し、Groovyクラスを取得。実行する
  こんなイメージとなる
　@Command (options  = [
     @Option(name="a");
  ])
  def execute(options) {
    if (option.a) {
    }
  }

* ファイルシステムからの読み込みの際にはivyのPomReaderを利用する
