package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Word;

@Mapper
public interface WordMapper {
//    @Select("SELECT * FROM word WHERE id = #{id}")
//    Word findById(Long id);
//
//    // ランダムに1件取得（簡易）
//    @Select("SELECT * FROM word ORDER BY RAND() LIMIT 1")
//    Word selectRandom();
	
//    @Select("SELECT * FROM word ORDER BY RAND() LIMIT 1")
//    Word selectRandomWord();
//
//    @Select("SELECT japanese FROM word WHERE id != #{id} ORDER BY RAND() LIMIT #{limit}")
//    List<String> selectRandomWrongAnswers(@Param("id") Long id, @Param("limit") int limit);
//
//    @Select("SELECT * FROM word WHERE id = #{id}")
//    Word findById(Long id);
	
    Word selectRandomWord();

    List<String> selectRandomWrongAnswers(
            @Param("id") Long id,
            @Param("limit") int limit
    );

    Word findById(Long id);
    
    
}

