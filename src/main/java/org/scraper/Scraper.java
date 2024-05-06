package org.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.model.AddData;
import org.model.FileHandler;
import org.model.ScrapeDataList;
import org.model.SubString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

    public static final List<ScrapeDataList> data = new ArrayList<>();
    private static String version;
    private static final String COMMA = ",";
    private static final String ZENKAKU_COMMA = "、";

    public static void main(String[] args) throws IOException {
        scrapeWord(args);
        FileHandler.writeToFile(data, version);
    }

    public static void scrapeWord(String[] args) throws IOException {
        System.out.println("スクレイプ中...");
        Document document = Jsoup.connect(args[0]).get();

        version = document.getElementsByClass("entry-title").text();
        version = SubString.getSubString(version, "", "熟語一覧").trim();

        Elements rows = document.select(".row-hover > tr");
        for(Element row : rows) {
            String word = replaceComma(row.getElementsByClass(getTargetClassNm(document, args[2])).text());
            String wordMeaning = replaceComma(row.getElementsByClass(getTargetClassNm(document, args[3])).text());
            int wordNum = Integer.parseInt(row.getElementsByClass(getTargetClassNm(document, args[1])).text());
            String section = parseNumToSection(wordNum);

            ScrapeDataList scrapeDataList = AddData.addData(wordNum, section, word, wordMeaning);
            data.add(scrapeDataList);
        }
    }

    private static String parseNumToSection(int num) {
        String section;
        int sectionNum = (num - 1) / 100 + 1;
        if(sectionNum <= 0) {
            return null;
        } else {
            section = "section" + sectionNum;
            return section;
        }
    }

    private static String replaceComma(String str) {
        if(str.contains(COMMA)) {
            str = str.replace(COMMA, ZENKAKU_COMMA);
        }
        return str;
    }

    private static String getTargetClassNm(Document document, String str) {
        Elements headerRow = document.select("thead th");
        for(Element headerItem : headerRow) {
            String rowStr = headerItem.text();
            if(str.equals(rowStr)) {
                 return headerItem.className().replace("sorting", "").trim();
            }
        }

        return null;

    }
}