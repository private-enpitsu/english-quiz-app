package com.example.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.app.domain.Word;
import com.example.app.mapper.WordMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final WordMapper wordMapper;

    public Map<String, Object> generateQuestion() {
        Word correctWord = wordMapper.selectRandomWord();

        // 正解以外から3つ取得
        List<String> wrongs = wordMapper.selectRandomWrongAnswers(correctWord.getId(), 3);

        // 選択肢リスト作成
        List<String> choices = new ArrayList<>();
        choices.add(correctWord.getJapanese());
        choices.addAll(wrongs);
        Collections.shuffle(choices);

        Map<String, Object> dto = new HashMap<>();
        dto.put("id", correctWord.getId());
        dto.put("english", correctWord.getEnglish());
        dto.put("choices", choices);

        return dto;
    }

    public boolean isCorrect(Long questionId, String selected) {
        Word original = wordMapper.findById(questionId);
        if (original == null) return false;
        return original.getJapanese().equals(selected);
    }

    public String getCorrectJapanese(Long questionId) {
        return wordMapper.findById(questionId).getJapanese();
    }
}

//
//@Service
//@RequiredArgsConstructor
//public class QuizService {
//    private final WordMapper wordMapper;
//
//    public Word getRandomWord() {
//        return wordMapper.selectRandom();
//    }
//
//    // 表示用に選択肢をシャッフルして渡す
//    public Map<String, Object> makeQuestionDto(Word w) {
//        List<String> choices = new ArrayList<>();
//        choices.add(w.getCorrectJapanese());
//        choices.add(w.getWrong1());
//        choices.add(w.getWrong2());
//        choices.add(w.getWrong3());
//        Collections.shuffle(choices);
//        Map<String, Object> dto = new HashMap<>();
//        dto.put("id", w.getId());
//        dto.put("english", w.getEnglish());
//        dto.put("choices", choices);
//        // 正解の文字列はサーバー側で持っておく（クライアントには送らない）
//        dto.put("answer", w.getCorrectJapanese());
//        return dto;
//    }
//
//    public boolean isCorrect(Long questionId, String selected) {
//        Word w = wordMapper.findById(questionId);
//        if (w == null) return false;
//        return w.getCorrectJapanese().equals(selected);
//    }
//    
//    public Word getWordById(Long id) {
//        return wordMapper.findById(id);
//    }
//}
