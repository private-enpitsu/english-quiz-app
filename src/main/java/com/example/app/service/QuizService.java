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
@RequiredArgsConstructor //final フィールドを引数に取るコンストラクタを自動生成
public class QuizService {
	
    private final WordMapper wordMapper;

//    ユーザー用
    
    // 単語一覧取得
    public List<Word> getAllWords() {
        return wordMapper.findAll();
    }
    
    
    // 一覧画面、単語検索
    public List<Word> getWordsByKeyword(String keyword) {
        return wordMapper.searchByKeyword(keyword);
    }
    
    
    // 件数取得（検索あり／なし）
    public int countWords(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return wordMapper.countAll();
        } else {
            return wordMapper.countByKeyword(keyword);
        }
    }

    // ぺージネーション：ページ取得（検索あり／なし）
    public List<Word> getWordPage(String keyword, int offset, int limit) {
        if (keyword == null || keyword.isBlank()) {
            return wordMapper.findPage(offset, limit);
        } else {
            return wordMapper.searchPage(keyword, offset, limit);
        }
    }
    
    
 // 管理者用の CRUD メソッドを

    // 単語1件取得（管理画面の編集用）
    public Word getWord(Long id) {
        // 指定IDの単語を1件取得。存在しない場合は null になる想定
        return wordMapper.findById(id);
    }

    // 単語新規登録
    public void createWord(Word word) {
        // english / japanese フィールドを使って INSERT
        wordMapper.insert(word);
    }

    // 単語更新
    public void updateWord(Word word) {
        // id をキーに english / japanese を更新
        wordMapper.update(word);
    }

    // 単語削除
    public void deleteWord(Long id) {
        // 指定IDのレコードを削除
        wordMapper.deleteById(id);
    }


    
    
    
    
    //・新しいクイズ問題を生成するメソッド。
    public Map<String, Object> generateQuestion() {
        Word correctWord = wordMapper.selectRandomWord(); //WordMapper を使って 正解の単語 をランダムに1件取得。

        // 正解以外から3つ取得 selectRandomWrongAnswers を呼び出して、3つの誤答（日本語）を取得。正解単語は除外されます。
        List<String> wrongs = wordMapper.selectRandomWrongAnswers(correctWord.getId(), 3);
        
        // 選択肢リスト作成 正解と誤答を混ぜて選択肢リストを作成する処理。
        List<String> choices = new ArrayList<>(); //選択肢を格納するリストを新規作成。
        choices.add(correctWord.getJapanese()); //正解の日本語訳を選択肢リストに追加。
        choices.addAll(wrongs); //先ほど取得した3つの誤答リストを選択肢に追加。
        Collections.shuffle(choices); //選択肢の順番をランダムにシャッフル。正解の位置が毎回変わるようにするため。

        Map<String, Object> dto = new HashMap<>(); //クライアントに返すデータ用の Map を作成。dto（Data Transfer Object 的な使い方）として利用。
        dto.put("id", correctWord.getId()); //問題の単語 ID を Map に格納。フロントで回答確認に使用。
        dto.put("english", correctWord.getEnglish()); //英単語（問題文）を Map に格納。
        dto.put("choices", choices); //シャッフル済みの選択肢リストを Map に格納。

        return dto; //作成した Map を返す。JSON に変換され、フロントに送信される想定。
    }

    //・回答が正しいかどうか判定するメソッド。
    public boolean isCorrect(Long questionId, String selected) { //questionId →問題の単語 ID //selected → ユーザーが選択した日本語訳
        Word original = wordMapper.findById(questionId); //Mapper から問題の単語情報を取得。
        if (original == null) return false; //ID が存在しない場合は 不正解扱い。
        return original.getJapanese().equals(selected); //正解の日本語と選択肢が一致するかを判定して返す。
    }

    //・指定問題の正解日本語を取得するメソッド。
    public String getCorrectJapanese(Long questionId) {
        return wordMapper.findById(questionId).getJapanese(); //Mapper で問題を取得し、正解の日本語を返す。
    }
}
