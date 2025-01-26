package constants;

public enum MongoCollection {
/*
    ESTACIONES("estaciones"),
    EMPRESAS("empresas"),
    CARBURANTES("carburantes"),
    PRECIOS("precios"),
    UBICACIONES("ubicaciones");
*/
    ESTACIONES("estaciones");

    private final String collection;

    MongoCollection(String collection) {
        this.collection = collection;
    }

    public String getCollection() {
        return collection;
    }
}
