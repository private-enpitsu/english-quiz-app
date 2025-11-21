package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.Word;
import com.example.app.service.QuizService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // quizService をコンストラクタインジェクション
public class AdminWordController {

    // 単語管理用に QuizService を再利用
    private final QuizService quizService;

    // 管理用一覧画面
    @GetMapping("/admin/words")
    public String adminWordList(Model model) {
        // 全単語を取得して一覧表示に使う
        List<Word> words = quizService.getAllWords();
        model.addAttribute("words", words);
        return "admin-word-list"; // admin-word-list.html を表示
    }

    // 新規登録フォーム表示
    @GetMapping("/admin/words/new")
    public String showCreateForm(Model model) {
        // 空の Word オブジェクトをフォームのモデルとして渡す
        model.addAttribute("word", new Word());
        return "admin-word-form"; // admin-word-form.html を表示
    }

    // 新規登録処理
    @PostMapping("/admin/words/new")
    public String createWord(@ModelAttribute Word word) {
        // 入力された英単語・日本語を DB に登録
        quizService.createWord(word);
        // 登録後、一覧画面にリダイレクト
        return "redirect:/admin/words";
    }

    // 編集フォーム表示
    @GetMapping("/admin/words/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        // 編集対象の単語を取得
        Word word = quizService.getWord(id);
        if (word == null) {
            // 存在しない ID の場合は一覧に戻す
            return "redirect:/admin/words";
        }
        model.addAttribute("word", word);
        return "admin-word-form"; // 新規と同じテンプレートを使う
    }

    // 更新処理
    @PostMapping("/admin/words/{id}/edit")
    public String updateWord(@PathVariable Long id, @ModelAttribute Word word) {
        // パスの id を優先して設定（念のため）
        word.setId(id);
        quizService.updateWord(word);
        return "redirect:/admin/words";
    }

    // 削除処理
    @PostMapping("/admin/words/{id}/delete")
    public String deleteWord(@PathVariable Long id) {
        quizService.deleteWord(id);
        return "redirect:/admin/words";
    }
}
