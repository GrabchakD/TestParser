package com.parser.parser;

import com.parser.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlParserImpl implements HtmlParser {
    @Override
    public List<Product> parser(String keyword) throws IOException {

        int countOfRequest = 0;
        List<Product> productList = new ArrayList<>();
        List<String> linksToProducts;

        Document doc = buildUrl(keyword);

        linksToProducts = getProductPagesLinks(doc);

        for (String link : linksToProducts) {
            Document currentDoc = Jsoup.connect(link).get();
            countOfRequest++;

            Product product = new Product();

            product.setName(getName(currentDoc));
            product.setBrand(getBrand(currentDoc));
            product.setColor(getColor(currentDoc));
            product.setPrice(getPrice(currentDoc));
            product.setInitialPrice(getInitialPrice(currentDoc));
            product.setDescription(getDescription(currentDoc));
            product.setArticleId(getArticleId(currentDoc));
            product.setShippingCosts(getShippingCosts(currentDoc));

            productList.add(product);
        }

        //check if count == 0 but we always have 1 HTTP request, so we need sout always count >= 1!
        if (countOfRequest != 0) {
            System.out.println("Amount of triggered HTTP request: " + countOfRequest);
        } else {
            System.out.println("Amount of triggered HTTP request: " + (countOfRequest + 1));
        }

        System.out.println("Amount of extracted products: " + linksToProducts.size());

        return productList;
    }

    private Document buildUrl(String keyword) throws IOException {

        return Jsoup.connect(String.format("http://www.aboutyou.de/suche?term=%s", keyword)).get();
    }

    private String buildUrlForProduct(String url, String ... args) {

        return String.format(url, args);
    }

    private List<String> getProductPagesLinks(Document doc) {
        return doc.select(ParserConstants.A_TAG).eachAttr(ParserConstants.HREF_TAG).stream()
                .filter(u -> u.startsWith(ParserConstants.PRODUCT_INITIALIZER_TAG))
                .map(u -> buildUrlForProduct(ParserConstants.BASE_URL_PATTERN, u))
                .collect(Collectors.toList());
    }

    private String getName(Document doc) {
        Element element = doc.getElementsByClass("styles__titleContainer--33zw2").first();
        String result = element.select(ParserConstants.H1_TAG).text();

        return result;
    }

    private String getBrand(Document doc) {
        Element element = doc.getElementsByClass("styles__titleContainer--33zw2").first();
        String result = element.select(ParserConstants.H1_TAG).text();

        return result == null ? "" : result;
    }

    private String getColor(Document doc) {
        Element element = doc.getElementsByClass("styles__title--UFKYd").addClass("styles__isHoveredState--2BBt9").first();
        String result = element.select(ParserConstants.SPAN_TEXT).text();

        return result == null ? "" : result;
    }

    private String getPrice(Document doc) {
        Element element = doc.getElementsByClass("productPrices").first();
        String result = element.select(ParserConstants.DIV_TAG).text();

        return result == null ? "" : result;
    }

    private String getInitialPrice(Document doc) {
        Element element = doc.getElementsByClass("priceStyles__strike--PSBGK").first();

        return element == null ? "" : element.select(ParserConstants.DIV_TAG).text();
    }

    private String getDescription(Document doc) {
        Element element = doc.getElementsByClass("styles__textElement--3QlT_").first();
        String result = element.select(ParserConstants.UL_TAG).text();

        return result == null ? "" : result;
    }

    private String getArticleId(Document doc) {
        Element element = doc.getElementsByClass("styles__articleNumber--1UszN").first();
        String result = element.select(ParserConstants.LI_TAG).text();

        return result == null ? "" : result;
    }

    private String getShippingCosts(Document doc) {
        Elements element = doc.getElementsByClass("styles__label--1cfc7").next();
        String result = element.select(ParserConstants.SPAN_TEXT).text();

        return result == null ? "" : result;
    }
}
