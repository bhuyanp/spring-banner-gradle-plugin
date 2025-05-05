package io.github.bhuyanp.gradle.ansi;

class SimpleAttribute extends Attribute {

    private final String _code;
    private final String _referrer;

    /**
     * Constructor. Maps an attribute to an Ansi code.
     *
     * @param code Ansi code that represents the attribute.
     */
    SimpleAttribute(String code) {
        _referrer = getReferrer();
        _code = code;
    }

    @Override
    public String construct() {
        return _referrer;
    }

    @Override
    public String toString() {
        return _code;
    }

}
