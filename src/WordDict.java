import com.grsisfee.Tools.FileOperator.ReadFile;
import com.grsisfee.Tools.Trie.Trie;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Grsis Fee
 * Date:   2016/1/18.
 */
public class WordDict {

    public WordDict(@NotNull String dictionaryFile) {
        wordTrieDict = new Trie();
        initDict(dictionaryFile);
    }

    public boolean wordExist(String word) {
        return wordTrieDict.contains(word);
    }

    public int judgeWordType(String word) {
        if (word == null || word.isEmpty()) return WORD_TYPE_NULL_OR_EMPTY;
        else if (word.trim().isEmpty()) return WORD_TYPE_TAB_OR_SPACE;
        else if (isNumber(word)) return WORD_TYPE_NM;
        else if (isEnglish(word)) return WORD_TYPE_EN;
        else if (isChinese(word)) return WORD_TYPE_CN;
        else return WORD_TYPE_UNDEFINED;
    }

    public final int WORD_TYPE_NULL_OR_EMPTY = -2;       // 字符串为空或其长度为空
    public final int WORD_TYPE_TAB_OR_SPACE = -1;        // 字符串为tab键或空格
    public final int WORD_TYPE_UNDEFINED = 0;            // 未定义的词语类型
    public final int WORD_TYPE_NM = 1;                   // 数值
    public final int WORD_TYPE_EN = 2;                   // 英文
    public final int WORD_TYPE_CN = 3;                   // 中文


    private boolean isChinese(String word) {
        return matchPattern(word, "^[\u4E00-\u9FA5]+$");            // 匹配：长度大于1的中文字符串
    }

    private boolean isEnglish(String word) {
        return matchPattern(word, "^[a-zA-Z]+$");                   // 匹配：长度大于1的英文字符串
    }

    private boolean isNumber(String word) {
        return matchPattern(word, "^[\\-\\+]?[0-9]+\\.?[0-9]+$|" +  // 匹配：正负小数
                                  "^[\\-\\+]?\\.[0-9]+$|" +         // 匹配：-.6 类型的小数
                                  "^[\\-\\+]?[0-9]+\\.$|" +         // 匹配：-6. 类型的小数
                                  "^[\\-\\+]?[0-9]+$");             // 匹配：整数
    }

    private boolean matchPattern(String word, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    private void initDict(@NotNull String dictionaryFile) {
        BufferedReader reader;
        try {
            reader = ReadFile.read(dictionaryFile);
            for (String line; (line = reader.readLine()) != null;) {
                String [] cols = line.split(COLUMN_SEPARATOR);
                // 只添加每一行的第一个词到词典
                wordTrieDict.put(cols[0]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Trie wordTrieDict;

    private static final String COLUMN_SEPARATOR = "\t";
}
