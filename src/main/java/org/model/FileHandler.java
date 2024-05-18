package org.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.xwpf.usermodel.*;
import org.util.ConfigUtil;

public class FileHandler {
    private static final String today = Date.getCurrentDate();
    private static final String baseDir = ConfigUtil.getProperty("BASE_DIR");
    private static final String resultDir = ConfigUtil.getProperty("RESULT_DIR") + today + "\\";
    private static final Path resultPath = Paths.get(resultDir);

    public static void writeToFile(List<ScrapeDataList> data, String fileName) {
        System.out.println("Csvファイル作成中...");
        String filePath = baseDir + fileName + ".csv";

        try(PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("番号,section,単語,意味");

            for(ScrapeDataList datum : data) {
                writer.println(datum);
            }

            System.out.println("Csvファイルにデータが書き込まれました");
            System.out.println("アップロード先： " + filePath);
        } catch (IOException e) {
            System.out.println("ファイル書き込む中にエラーが発生しました： " + e.getMessage());
        }
    }

    public static List<WordItem> readWordFile(String fileNm) throws IOException {
        List<WordItem> wordItemList = new ArrayList<>();

        String filePath = baseDir + fileNm + ".csv";
        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                int num = Integer.parseInt(record.get("番号"));
                String section = record.get("section");
                String word = record.get("単語");
                String meaning = record.get("意味");

                WordItem wordItem = new WordItem(num, section, word, meaning);
                wordItemList.add(wordItem);
            }
            in.close();

            return wordItemList;
        } catch (FileNotFoundException f) {
            System.err.println("指定されたファイルはみつかりませんでした。:" + fileNm);
            return null;
        }
    }

    public static List<String> getFileList() {
        List<String> fileList = new ArrayList<>();
        // ディレクトリオブジェクトを作成
        File directory = new File(baseDir);

        // ディレクトリが存在し、ディレクトリであることを確認
        if (directory.exists() && directory.isDirectory()) {
            // ディレクトリ内のファイルの一覧を取得
            File[] files = directory.listFiles();

            // ファイル名を出力
            if (files != null) {
                for (File file : files) {
                    String fileNm = file.getName().replace(".csv", "");
                    fileList.add(fileNm);
                }
                return fileList;
            }
        } else {
            System.out.println("指定されたパスはディレクトリではありません。");
            return fileList;
        }

        return fileList;
    }

    public static void writeToWordFile(List<String> data, String fileNm) {
        System.out.println("Wordファイルへの書き出しを開始します");
        // 新しいWord文書を作成
        XWPFDocument document = new XWPFDocument();

        // テキストを追加
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setText(fileNm);
        run.addCarriageReturn();
        run.addCarriageReturn();

        int cnt = 1;
        for(String str : data) {
            if(fileNm.contains("問題")) {
                run.setText(cnt + ", " + str);
                run.addCarriageReturn();
                run.setText("____________________");
                run.addCarriageReturn();
            } else if (fileNm.contains("解答")) {
                run.setText(cnt + ", " + str);
                run.addCarriageReturn();
            }
            cnt++;
        }

        // 文書を保存
        try {
            if(!Files.exists(resultPath)) {
                Files.createDirectories(resultPath);
            }
            FileOutputStream out = new FileOutputStream(resultDir + fileNm + ".docx");
            document.write(out);
            out.close();
            System.out.println("Word文書が正常に作成されました。");
        } catch (Exception e) {
            System.err.println("Word文書の作成中にエラーが発生しました。");
            e.printStackTrace();
        }
    }
}
