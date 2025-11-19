package com.example.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.app.service.QuizService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/quiz")
    public String quizPage() {
        return "quiz"; // src/main/resources/templates/quiz.html
    }

    // API: 次の問題を取得
//    @GetMapping("/api/question")
//    @ResponseBody
//    public Map<String, Object> question() {
//        Word w = quizService.getRandomWord();
//        Map<String, Object> dto = quizService.makeQuestionDto(w);
//        // サーバー側では"answer"はチェック用に保持するがクライアントに送りたくない -> 送らない
//        Map<String,Object> safe = new HashMap<>();
//        safe.put("id", dto.get("id"));
//        safe.put("english", dto.get("english"));
//        safe.put("choices", dto.get("choices"));
//        return safe;
//    }

    @GetMapping("/api/question")
    @ResponseBody
    public Map<String, Object> question() {
        return quizService.generateQuestion();
    }
    // API: 回答チェック（選択肢を送る）
//    @PostMapping("/api/answer")
//    @ResponseBody
//    public Map<String, Object> answerCheck(@RequestBody Map<String, String> req) {
//        Long questionId = Long.valueOf(req.get("id"));
//        String selected = req.get("selected");
//        boolean correct = quizService.isCorrect(questionId, selected);
//
//        Map<String,Object> res = new HashMap<>();
//        res.put("correct", correct);
//
//        // 正解テキスト（クライアントで正解箇所を緑にするために返す）
//        // NOTE: これはクライアント側で正解を表示するために返す。セキュリティの必要はほぼ無し（学習アプリ）。
//        // 本当に隠したければ返さず、次の問題の判定時にだけ使用する設計にする。
//        Word w = quizService.getRandomWord(); // NOTE: ここでは間違いを防ぐため、正解の取得を明確にするためにmapperを使うのが正しい。例示のため簡略。
//        // better: retrieve by id to get correctJapanese; I'll get the correct by mapping findById
//        // but for brevity, call quizService.getRandomWord is not correct: change below:
//        // We'll fetch actual correct:
//        // (I'll update to proper)
//        // -- updated below:
//        return res;
//    }
    
    
    @PostMapping("/api/answer")
    @ResponseBody
    public Map<String, Object> answer(@RequestBody Map<String, String> req) {
        Long id = Long.valueOf(req.get("id"));
        String selected = req.get("selected");

        boolean result = quizService.isCorrect(id, selected);
        String correctText = quizService.getCorrectJapanese(id);

        Map<String,Object> res = new HashMap<>();
        res.put("correct", result);
        res.put("correctAnswer", correctText);

        // 次の問題も同時に返す
        res.put("nextQuestion", quizService.generateQuestion());

        return res;
    }
//    @PostMapping("/api/answer")
//    @ResponseBody
//    public Map<String, Object> answerCheck(@RequestBody Map<String, String> req) {
//        Long questionId = Long.valueOf(req.get("id"));
//        String selected = req.get("selected");
//        boolean correct = quizService.isCorrect(questionId, selected);
//
//        Map<String,Object> res = new HashMap<>();
//        res.put("correct", correct);
//
//        // 正解文字列を返す（UIのため）
//        Word target = quizService.getWordById(questionId);
//        res.put("correctAnswer", target.getCorrectJapanese());
//
//        // 次の問題も返す（クライアントがすぐ表示できるように）
//        Word next = quizService.getRandomWord();
//        Map<String,Object> nextDto = new HashMap<>();
//        nextDto.put("id", next.getId());
//        nextDto.put("english", next.getEnglish());
//        List<String> choices = new ArrayList<>();
//        choices.add(next.getCorrectJapanese());
//        choices.add(next.getWrong1());
//        choices.add(next.getWrong2());
//        choices.add(next.getWrong3());
//        Collections.shuffle(choices);
//        nextDto.put("choices", choices);
//        res.put("nextQuestion", nextDto);
//
//        return res;
//    }

}
