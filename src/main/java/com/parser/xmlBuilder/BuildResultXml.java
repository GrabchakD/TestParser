package com.parser.xmlBuilder;

import com.parser.model.Product;

import java.util.List;

public interface BuildResultXml {

    StringBuilder build(List<Product> productList);
}
