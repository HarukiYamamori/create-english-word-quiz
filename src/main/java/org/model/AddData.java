package org.model;

public class AddData {
    public static ScrapeDataList addData(int num, String section, String word, String meaning) {
        ScrapeDataList scrapeDataList = new ScrapeDataList();
        scrapeDataList.num = num;
        scrapeDataList.section = section;
        scrapeDataList.word = word;
        scrapeDataList.meaning = meaning;

        return scrapeDataList;
    }
}
