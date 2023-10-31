module intervals4j.jackson {

    // required in compile time
    requires intervals4j;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    // exported in compile time
    exports ws.slink.intervals.jackson;

}
