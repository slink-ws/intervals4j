module intervals4j {

    // required in compile time

    // exported in compile time
    exports ws.slink.intervals;
    exports ws.slink.intervals.exception;

    // opened in run time
    opens ws.slink.intervals.impl;
    opens ws.slink.intervals.tools;
    opens ws.slink.intervals;

}
