package d.grabchak;

import d.grabchak.Parser.HtmlParserImpl;

import java.io.IOException;

public class RunApp {

    public static void main(String[] args) throws IOException {
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        /*
        * Write some code
        * */
        HtmlParserImpl parser = new HtmlParserImpl();
        parser.parser("vans");

        long endTime = System.currentTimeMillis();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long totalTime = endTime - startTime;
        long actualMemUsed = afterUsedMem - beforeUsedMem;

        System.out.println("Run-time: " + totalTime / 1000 + "sec");
        System.out.println("Memory Footprint: " + actualMemUsed / 1048576 + "mb");
    }
}
