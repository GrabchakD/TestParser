package d.grabchak;

import d.grabchak.Parser.HtmlParserImpl;
import d.grabchak.XmlBuilder.BuildResultXmlImpl;
import d.grabchak.model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class RunApp {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        HtmlParserImpl parser = new HtmlParserImpl();
        List<Product> productList = parser.parser(input);

        long endTime = System.currentTimeMillis();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long totalTime = endTime - startTime;
        long actualMemUsed = afterUsedMem - beforeUsedMem;

        System.out.println("Run-time: " + totalTime / 1000 + "sec");
        System.out.println("Memory Footprint: " + actualMemUsed / 1048576 + "mb");

        BuildResultXmlImpl buildResultXml = new BuildResultXmlImpl();
        buildResultXml.build(productList);
    }
}
