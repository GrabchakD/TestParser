package d.grabchak;

import d.grabchak.parser.HtmlParser;
import d.grabchak.parser.HtmlParserImpl;
import d.grabchak.xmlBuilder.BuildResultXml;
import d.grabchak.xmlBuilder.BuildResultXmlImpl;
import d.grabchak.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ParserApp {
    public static void main(String[] args) throws IOException {
        BuildResultXml buildResultXml = new BuildResultXmlImpl();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        long beforeUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        HtmlParser parser = new HtmlParserImpl();
        List<Product> productList = parser.parser(input);

        long endTime = System.currentTimeMillis();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long totalTime = endTime - startTime;
        long actualMemoryUsed = afterUsedMem - beforeUsedMemory;

        System.out.println(buildResultXml.build(productList));
        System.out.println("Run-time: " + totalTime / 1000 + "sec");
        System.out.println("Memory Footprint: " + actualMemoryUsed / 1048576 + "mb");
    }
}
