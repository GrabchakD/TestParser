package d.grabchak.xmlBuilder;

import d.grabchak.model.Product;

import java.util.List;

public interface BuildResultXml {

    StringBuilder build(List<Product> productList);
}
