package org.model;

public class WordItem {
    public int num;
    public String section;
    public String word;
    public String meaning;

    public WordItem(int num, String section, String word, String meaning) {
        this.num = num;
        this.section = section;
        this.word = word;
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return num + ", " + section + ", " + word + ", " + meaning;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
