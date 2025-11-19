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
    
}

