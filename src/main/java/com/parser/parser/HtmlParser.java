package com.parser.parser;

import com.parser.model.Product;

import java.io.IOException;
import java.util.List;

public interface HtmlParser {
    List<Product> parser(String keyword) throws IOException;
}
