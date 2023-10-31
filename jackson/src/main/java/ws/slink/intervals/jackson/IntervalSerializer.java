package ws.slink.intervals.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ws.slink.intervals.Day;
import ws.slink.intervals.Interval;
import ws.slink.intervals.Month;
import ws.slink.intervals.Year;

import java.io.IOException;

import static ws.slink.intervals.jackson.internal.Constants.END_FIELD;
import static ws.slink.intervals.jackson.internal.Constants.FORMATTER;
import static ws.slink.intervals.jackson.internal.Constants.START_FIELD;
import static ws.slink.intervals.jackson.internal.Constants.TIMEZONE_FIELD;
import static ws.slink.intervals.jackson.internal.Constants.TYPE_FIELD;

/**
 * Jackson-based interval serializer
 */
public class IntervalSerializer extends StdSerializer<Interval> {


    public IntervalSerializer() {
        super(Interval.class);
    }

    @Override
    public void serialize(Interval value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
        gen.writeStartObject();
        gen.writeStringField(TIMEZONE_FIELD, value.timezone().getID());
        gen.writeStringField(START_FIELD, FORMATTER.format(value.getStart()));
        gen.writeStringField(END_FIELD, FORMATTER.format(value.getEnd()));
        if ((value instanceof Year) || (value instanceof Month) || (value instanceof Day)) {
            gen.writeStringField(TYPE_FIELD, value.getClass().getSimpleName().toLowerCase());
        }
        gen.writeEndObject();
    }

}
