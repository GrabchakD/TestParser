package d.grabchak.Parser;

import d.grabchak.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static d.grabchak.Parser.ParserConstants.*;

public class HtmlParserImpl implements HtmlParser {

    @Override
    public List<Product> parser(String keyword) throws IOException {

        int countOfRequest = 0;
        List<Product> productList = new ArrayList<>();
        List<String> linksToProducts;

        Document doc = buildUrl(keyword);

        String redirectLink = getRedirectLintToPageWithProduct(doc);

        Document absDocument = Jsoup.connect(redirectLink).get();

        linksToProducts = getProductPagesLinks(absDocument);

        String nextPageLink = getNextPage(absDocument);

        if (nextPageLink != null) {
            Document next = Jsoup.connect(nextPageLink).get();
            linksToProducts.add(String.valueOf(getProductPagesLinks(next)));
        }

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

    //TODO separate Jsoup logic from url builder
    private Document buildUrl(String keyword) throws IOException {

        return Jsoup.connect(String.format("http://www.aboutyou.de/suche?term=%s", keyword)).get();
    }

    private String getNextPage(Document doc) {
        Element elements = doc.getElementsByClass("styles__paginationWrapper--SrlgQ").first();
        if (elements == null) {
            return null;
        }
        Element link = elements.select("a.styles__buttonLink--BgPaW").first();
        String result = link.attr(ABS_HREF_TAG);

        return result;
    }

    private String buildUrl2(String url, String ... args) {

        return String.format(url, args);
    }

    private List<String> getProductPagesLinks(Document doc) {
        return doc.select(A_TAG).eachAttr(HREF_TAG).stream()
                .filter(u -> u.startsWith(PRODUCT_INITIALIZER_TAG))
                .map(u -> buildUrl2(BASE_URL, u))
                .collect(Collectors.toList());
    }

    private String getRedirectLintToPageWithProduct(Document doc) {
        Elements result = doc.select(LINK_TAG).next();
        String absHref = result.attr(ABS_HREF_TAG);

        return absHref;
    }

    private String getName(Document doc) {
        Element element = doc.getElementsByClass("styles__titleContainer--33zw2").first();
        String result = element.select(H1_TAG).text();

        return result;
    }

    private String getBrand(Document doc) {
        Element element = doc.getElementsByClass("styles__titleContainer--33zw2").first();
        String result = element.select(H1_TAG).text();

        return result;
    }

    private String getColor(Document doc) {
        Element element = doc.getElementsByClass("styles__title--UFKYd").addClass("styles__isHoveredState--2BBt9").first();
        String result = element.select(SPAN_TEXT).text();

        return result;
    }

    private String getPrice(Document doc) {
        Element element = doc.getElementsByClass("productPrices").first();
        String result = element.select(DIV_TAG).text();

        return result;
    }

    private String getInitialPrice(Document doc) {
        Element element = doc.getElementsByClass("priceStyles__strike--PSBGK").first();
        String result = element.select(DIV_TAG).text();

        return result;
    }
    private String getDescription(Document doc) {
        Element element = doc.getElementsByClass("styles__textElement--3QlT_").first();
        String result = element.select(UL_TAG).text();

        return result;
    }

    private String getArticleId(Document doc) {
        Element element = doc.getElementsByClass("styles__articleNumber--1UszN").first();
        String result = element.select(LI_TAG).text();

        return result;
    }

    private String getShippingCosts(Document doc) {
        Elements element = doc.getElementsByClass("styles__label--1cfc7").next();
        String result = element.select(SPAN_TEXT).text();

        return result;
    }
}
