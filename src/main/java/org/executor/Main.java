package org.executor;

import org.model.FileHandler;
import org.model.WordItem;

import java.io.IOException;
import java.util.*;

public class Main {
    public static final String BLANK = " ";
    public static final Set<String> sectionList = new LinkedHashSet<>();
    public static List<WordItem> wordItemList = new ArrayList<>();
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String wordBook = ask("以下から単語帳を指定してください\n" + FileHandler.getFileList());
        wordItemList = FileHandler.readWordFile(wordBook);

        createSectionList();
        String section = ask("出力したいsectionを以下から選択してください(半角スペースで複数選択可)\n" + sectionList);
        List<WordItem> newWordItemList = createWordListFromSelectedSection(section);

        int quizCnt = Integer.parseInt(ask("出題数を1~" + newWordItemList.size() + "で指定してください"));
        List<WordItem> randomWordList = createRandomWordList(newWordItemList, quizCnt);

        //Word文書への書き出し
        FileHandler.writeToWordFile(createListForWord(randomWordList, "word"), wordBook + " 問題");
        FileHandler.writeToWordFile(createListForWord(randomWordList, "meaning"), wordBook + "解答" );
    }

    private static void createSectionList() {
        sectionList.add("All");
        for(WordItem item : wordItemList) {
            sectionList.add(item.getSection());
        }
    }

    private static List<WordItem> createWordListFromSelectedSection(String str) {
        List<WordItem> newWordItemList = new ArrayList<>();
        String[] sections = str.split(BLANK);
        for(WordItem item : wordItemList) {
            String section = item.getSection();
            if(str.contains("All")) {
                return wordItemList;
            } else if(Arrays.asList(sections).contains(section)) {
                newWordItemList.add(item);
            }
        }

        return newWordItemList;
    }

    private static List<WordItem> createRandomWordList(List<WordItem> wordItemList, int quizCnt) {
        List<WordItem> randomWordList = new ArrayList<>();

        if(quizCnt >= wordItemList.size()) {
            return wordItemList;
        }

        Collections.shuffle(wordItemList);

        for(int i = 0; i < quizCnt; i++) {
            randomWordList.add(wordItemList.get(i));
        }

        return randomWordList;
    }

    public static String ask(String question) {
        System.out.println(question);
        System.out.print("入力：");
        return scan.nextLine();
    }

    public static List<String> createListForWord(List<WordItem> wordItemList, String target) {
        List<String> wordList = new ArrayList<>();
        for(WordItem item : wordItemList) {
            if("word".equals(target)) {
                wordList.add(item.getWord());
            } else if ("meaning".equals(target)) {
                wordList.add(item.getMeaning() + " (" + item.getSection() + ")");
            }
        }

        return wordList;
    }
}