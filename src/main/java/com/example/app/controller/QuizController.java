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
@RequiredArgsConstructor //finalや@NonNullのフィールドを引数に取るコンストラクタを自動生成します。
public class QuizController {
    private final QuizService quizService;

//    @GetMapping("/quiz")
    @GetMapping("/")
    public String quizPage() {
        return "quiz"; // src/main/resources/templates/quiz.html
    }

    @GetMapping("/api/question")
    @ResponseBody //メソッドの戻り値を JSON などの HTTP レスポンスの本文として返すことを示します。これがないと、戻り値は View 名として扱われます。
    public Map<String, Object> question() { //JSON に変換されてクライアントに返されます
        return quizService.generateQuestion(); //generateQuestion() を呼び出して 新しい問題 を生成
    }

    
    @PostMapping("/api/answer")
    @ResponseBody
    public Map<String, Object> answer(@RequestBody Map<String, String> req) { //@RequestBody により、JSON データを自動的に Map に変換。Map<String, String> に変換して受け取る。
        Long id = Long.valueOf(req.get("id")); //リクエスト JSON から "id" を取得し、Long 型に変換。
        String selected = req.get("selected"); //ユーザーが選択した答えを "selected" から取得。

        boolean result = quizService.isCorrect(id, selected); //quizService により、選択が正解かどうか判定。
        String correctText = quizService.getCorrectJapanese(id); //正解の日本語テキストを取得。

        Map<String,Object> res = new HashMap<>(); //レスポンス用の Map を作成。JSON に変換されてクライアントに返す。
        res.put("correct", result); //正解判定結果を "correct" キーに格納。
        res.put("correctAnswer", correctText); //正解のテキストを "correctAnswer" キーに格納。

        // 次の問題も同時に返す
        res.put("nextQuestion", quizService.generateQuestion()); //次の問題を生成して "nextQuestion" キーに格納。

        return res; //作成した Map を返す。JSON に変換され、フロントに送信される。
    }

}
