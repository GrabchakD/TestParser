package d.grabchak.xmlBuilder;

import d.grabchak.model.Product;

import java.util.List;

public class BuildResultXmlImpl implements BuildResultXml {

    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public StringBuilder build(List<Product> productList) {

            stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            stringBuilder.append("<Products>\n");

            for (Product product : productList) {
                stringBuilder.append("<offer>\n");
                stringBuilder.append("<name>" + product.getName() + "</name>\n");
                stringBuilder.append("<brand>" + product.getBrand() + "</brand>\n");
                stringBuilder.append("<color>" + product.getColor() + "</color>\n");
                stringBuilder.append("<price>" + product.getPrice() + "</price>\n");
                stringBuilder.append("<initialPrice>" + product.getInitialPrice() + "</initialPrice>\n");
                stringBuilder.append("<description>" + product.getDescription() + "</description>\n");
                stringBuilder.append("<articleID>" + product.getArticleId() + "</articleID>\n");
                stringBuilder.append("<shippingCosts>" + product.getShippingCosts() + "</shippingCosts>\n");
                stringBuilder.append("</offer>\n");
        }

        stringBuilder.append("</Products>\n");

        return stringBuilder;
    }
}
