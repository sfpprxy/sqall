package sqall.sql.ast;

import sqall.sql.parser.Token;

public class ElasticsearchTableSource implements TableSource {

    private String index;
    private String type;

    public ElasticsearchTableSource(Token index, Token type) {
        this.index = index.value();
        this.type = type.value();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
