package d.grabchak.parser;

import d.grabchak.model.Product;

import java.io.IOException;
import java.util.List;

public interface HtmlParser {
    List<Product> parser(String keyword) throws IOException;
}
