import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Author: Grsis Fee
 * Date:   2016/1/18.
 */
public class ChineseSegment {

    public ChineseSegment(@NotNull String dictionaryFile) {
        wordDict = new WordDict(dictionaryFile);
    }

    public boolean ReverseMaxMatch(@NotNull String sentence) {
        int sentenceLength = sentence.length();
        if (sentence.length() <= 0) return false;

        matchWords = new ArrayList<>();
        dismatchWords = new ArrayList<>();
        dismatchWord = "";

        while (sentenceLength > 0) {
            String word = sentence;
            int wordLength = word.length();
            boolean hasWord = false;
            int index = 0;
            for (; index < wordLength; index++) {
                String tmpWord = word.substring(index);

                if (wordDict.wordExist(tmpWord)) {
                    if (!dismatchWord.isEmpty()) {
                        manageEnglishAndNumber("");
                    }
                    matchWords.add(tmpWord);
                    hasWord = true;
                    break;
                }
            }

            if (hasWord)
                sentence = sentence.substring(0, index);
            else {
                manageEnglishAndNumber(sentence.substring(sentenceLength - 1));
                sentence = sentence.substring(0, sentenceLength - 1);
            }

            sentenceLength = sentence.length();
        }

        if (!dismatchWord.isEmpty()) {
            manageEnglishAndNumber("");
        }

        return true;
    }

    public void printMatchWords() {
        System.out.println("Match Words: " + wordsToString(matchWords, "\t"));
        System.out.println("Dismatch Words: " + wordsToString(dismatchWords, "\t"));
    }

    private void manageEnglishAndNumber(@NotNull String newCharacter) {
        newCharacter = newCharacter.trim();
        int oldType = wordDict.judgeWordType(dismatchWord);
        int newType = wordDict.judgeWordType(newCharacter);

        oldType = oldType == wordDict.WORD_TYPE_NULL_OR_EMPTY ? newType : oldType;

        if (oldType == newType) {
            dismatchWord = newCharacter + dismatchWord;
        } else {
            if (oldType == wordDict.WORD_TYPE_NM || oldType == wordDict.WORD_TYPE_EN) {
                matchWords.add(dismatchWord);
            } else {
                dismatchWords.add(dismatchWord);
            }
            dismatchWord = newCharacter;
        }
    }

    private String wordsToString(@NotNull List<String> words, @NotNull String wordSeparator) {
        String resultStr = "";
        for (String word : words) {
            resultStr = word + wordSeparator + resultStr;
        }
        // 去除最后一个分隔符
        if (resultStr.lastIndexOf(wordSeparator) != -1)
            resultStr = resultStr.substring(0, resultStr.lastIndexOf(wordSeparator));
        return resultStr;
    }

    private String dismatchWord;

    private List<String> dismatchWords;
    private List<String> matchWords;
    private WordDict wordDict;
}
