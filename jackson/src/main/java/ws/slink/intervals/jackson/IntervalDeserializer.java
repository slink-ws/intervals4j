package ws.slink.intervals.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

import static ws.slink.intervals.jackson.internal.Constants.END_FIELD;
import static ws.slink.intervals.jackson.internal.Constants.FORMATTER;
import static ws.slink.intervals.jackson.internal.Constants.START_FIELD;
import static ws.slink.intervals.jackson.internal.Constants.TIMEZONE_FIELD;
import static ws.slink.intervals.jackson.internal.Constants.TYPE_FIELD;

public class IntervalDeserializer extends StdDeserializer<Interval> {

    public IntervalDeserializer() {
        super(Interval.class);
    }

    @Override
    public Interval deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type =  node.has(TYPE_FIELD) ? node.get(TYPE_FIELD).asText() : "";
        String timezone =  node.has(TIMEZONE_FIELD) ? node.get(TIMEZONE_FIELD).asText() : "";
        String start =  node.has(START_FIELD) ? node.get(START_FIELD).asText() : "";
        String end =  node.has(END_FIELD) ? node.get(END_FIELD).asText() : "";

        if (timezone.equals("")) {
            throw new JsonParseException("invalid interval JSON: no 'timezone' set");
        }
        if (start.equals("")) {
            throw new JsonParseException("invalid interval JSON: no 'start' set");
        }
        if (end.equals("")) {
            throw new JsonParseException("invalid interval JSON: no 'end' set");
        }

        LocalDateTime sdt = parseDate(start);
        LocalDateTime edt = parseDate(end);

        switch (type) {
            case "": return new IntervalBuilder()
                .timezone(TimeZone.getTimeZone(timezone))
                .start(sdt)
                .end(edt)
                .build();
            case "year": return IntervalBuilder.year(sdt.getYear(), timezone);
            case "month": return IntervalBuilder.month(sdt.getYear(), sdt.getMonthValue(), timezone);
            case "day": return IntervalBuilder.day(sdt.getYear(), sdt.getMonthValue(), sdt.getDayOfMonth(), timezone);
            default:
                throw new JsonParseException("invalid interval type: " + type);
        }
    }

    private LocalDateTime parseDate(String input) throws IOException {
        try {
            return LocalDateTime.parse(input, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new JsonParseException("invalid date-time string: " + input);
        }
    }

}
