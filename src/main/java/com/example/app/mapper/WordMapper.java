package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Word;

@Mapper
public interface WordMapper {
	
    Word selectRandomWord(); //ランダムな単語を 1 件取得するメソッド。

    List<String> selectRandomWrongAnswers( //誤答の候補をランダムに取得するメソッド。
            @Param("id") Long id, //対象の単語 ID（正解の単語を除外するために使う）
            @Param("limit") int limit //取得する誤答の数
    );
    //@Param アノテーションは SQL 内で名前付きパラメータとして参照するために必要。
    
    Word findById(Long id); //指定した ID の単語を取得するメソッド。
    
    
    // 全件取得
    List<Word> findAll();
    
    // キーワード検索
    List<Word> searchByKeyword(@Param("keyword") String keyword);
    
    
 // データ件数取得
    int countAll();

    int countByKeyword(@Param("keyword") String keyword);

    // ぺージネーション ページ取得
    List<Word> findPage(@Param("offset") int offset,
                        @Param("limit") int limit);

    List<Word> searchPage(@Param("keyword") String keyword,
                          @Param("offset") int offset,
                          @Param("limit") int limit);
    
    
    // 管理用 CRUD ▼

    // 新規登録
    void insert(Word word);

    // 更新
    void update(Word word);

    // 削除
    void deleteById(Long id);
    
}

