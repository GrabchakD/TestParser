package d.grabchak.Parser;

import d.grabchak.model.Product;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public interface HtmlParser {

    List<Product> parser(String keyword) throws IOException;
}
