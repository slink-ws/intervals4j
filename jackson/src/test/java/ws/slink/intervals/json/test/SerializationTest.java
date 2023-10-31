package ws.slink.intervals.json.test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ws.slink.intervals.Day;
import ws.slink.intervals.Interval;
import ws.slink.intervals.IntervalBuilder;
import ws.slink.intervals.Month;
import ws.slink.intervals.Year;
import ws.slink.intervals.impl.CustomInterval;
import ws.slink.intervals.jackson.IntervalDeserializer;
import ws.slink.intervals.jackson.IntervalSerializer;

import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SerializationTest {

    private static final String TEST_INPUT = "2023-10";
    private static final String EXPECTED_STR_SIMPLE =
        "{\"timezone\":\"UTC\",\"start\":\"2023-10-01 00:00:00.000\",\"end\":\"2023-10-31 23:59:59.999\",\"type\":\"month\"}";

    private static final String EXPECTED_STR_PRETTY =
        "{\n" +
        "  \"timezone\" : \"UTC\",\n" +
        "  \"start\" : \"2023-10-01 00:00:00.000\",\n" +
        "  \"end\" : \"2023-10-31 23:59:59.999\",\n" +
        "  \"type\" : \"month\"\n" +
        "}";
    private static final String INVALID_JSON_NO_TIMEZONE =
        "{\n" +
        "  \"start\" : \"2023-10-01 00:00:00.000\",\n" +
        "  \"end\" : \"2023-10-31 23:59:59.999\",\n" +
        "  \"type\" : \"month\"\n" +
        "}";
    private static final String INVALID_JSON_NO_START =
        "{\n" +
        "  \"timezone\" : \"UTC\",\n" +
        "  \"end\" : \"2023-10-31 23:59:59.999\",\n" +
        "  \"type\" : \"month\"\n" +
        "}";
    private static final String INVALID_JSON_NO_END =
        "{\n" +
        "  \"timezone\" : \"UTC\",\n" +
        "  \"start\" : \"2023-10-01 00:00:00.000\",\n" +
        "  \"type\" : \"month\"\n" +
        "}";
    private static final String INVALID_JSON_INVALID_START =
        "{\n" +
        "  \"timezone\" : \"UTC\",\n" +
        "  \"start\" : \"2023-1-1 00:00:00.000\",\n" +
        "  \"end\" : \"2023-10-31 23:59:59.999\",\n" +
        "  \"type\" : \"month\"\n" +
        "}";
    private static final String INVALID_JSON_INVALID_END =
        "{\n" +
        "  \"timezone\" : \"UTC\",\n" +
        "  \"start\" : \"2023-01-01 00:00:00.000\",\n" +
        "  \"end\" : \"2023-10-32 23:59:59.999\",\n" +
        "  \"type\" : \"month\"\n" +
        "}";
    private static final String INVALID_JSON_INVALID_TYPE =
        "{\n" +
        "  \"timezone\" : \"UTC\",\n" +
        "  \"start\" : \"2023-01-01 00:00:00.000\",\n" +
        "  \"end\" : \"2023-10-31 23:59:59.999\",\n" +
        "  \"type\" : \"mont\"\n" +
        "}";


    private ObjectMapper mapper;

    @Before
    public void init() {
        mapper = new ObjectMapper();
        mapper.registerModule(
            new SimpleModule()
                .addSerializer(Interval.class, new IntervalSerializer())
                .addDeserializer(Interval.class, new IntervalDeserializer())
        );
    }

    // region - 01: positive tests

    @Test
    public void t0101_can_serialize_simple() throws JsonProcessingException {
        Interval interval = Month.of(TEST_INPUT);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(Interval.class, new IntervalSerializer()));
        assertEquals(EXPECTED_STR_SIMPLE, mapper.writeValueAsString(interval));
    }

    @Test
    public void t0102_can_serialize_pretty() throws JsonProcessingException {
        Interval interval = Month.of(TEST_INPUT);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(Interval.class, new IntervalSerializer()));
        assertEquals(EXPECTED_STR_PRETTY, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(interval));
    }

    @Test
    public void t0103_can_deserialize_year() throws JsonProcessingException {
        Year year = IntervalBuilder.year(2023);
        String json = mapper.writeValueAsString(year);
        Interval parsed = mapper.readValue(json, Interval.class);
        assertEquals(year.getStart(), parsed.getStart());
        assertEquals(year.getEnd(), parsed.getEnd());
        assertEquals(year.timezone(), parsed.timezone());
        assertEquals(year.start(), parsed.start());
        assertEquals(year.end(), parsed.end());
        assertTrue(parsed instanceof Year);
    }

    @Test
    public void t0104_can_deserialize_month() throws JsonProcessingException {
        Month month = IntervalBuilder.month(2023, 10, "Asia/Vladivostok");
        String json = mapper.writeValueAsString(month);
        Interval parsed = mapper.readValue(json, Interval.class);
        assertEquals(month.getStart(), parsed.getStart());
        assertEquals(month.getEnd(), parsed.getEnd());
        assertEquals(month.timezone(), parsed.timezone());
        assertEquals(month.start(), parsed.start());
        assertEquals(month.end(), parsed.end());
        assertTrue(parsed instanceof Month);
    }

    @Test
    public void t0105_can_deserialize_day() throws JsonProcessingException {
        Day day = IntervalBuilder.day(2023, 10, 15, "Asia/Vladivostok");
        String json = mapper.writeValueAsString(day);
        Interval parsed = mapper.readValue(json, Interval.class);
        assertEquals(day.getStart(), parsed.getStart());
        assertEquals(day.getEnd(), parsed.getEnd());
        assertEquals(day.timezone(), parsed.timezone());
        assertEquals(day.start(), parsed.start());
        assertEquals(day.end(), parsed.end());
        assertTrue(parsed instanceof Day);
    }

    @Test
    public void t0106_can_deserialize_custom() throws JsonProcessingException {
        Interval day = new IntervalBuilder()
            .timezone(TimeZone.getTimeZone("Asia/Vladivostok"))
            .start(LocalDateTime.of(2023, 10, 15, 10, 15, 0, 0))
            .end(LocalDateTime.of(2023, 10, 15, 10, 30, 0, 0))
            .build();
        String json = mapper.writeValueAsString(day);
        Interval parsed = mapper.readValue(json, Interval.class);
        assertEquals(day.getStart(), parsed.getStart());
        assertEquals(day.getEnd(), parsed.getEnd());
        assertEquals(day.timezone(), parsed.timezone());
        assertEquals(day.start(), parsed.start());
        assertEquals(day.end(), parsed.end());
        assertFalse(parsed instanceof Day);
        assertFalse(parsed instanceof Month);
        assertFalse(parsed instanceof Year);
        assertTrue(parsed instanceof CustomInterval);
    }

    // endregion
    // region - 02: negative tests

    @Test(expected = JsonParseException.class)
    public void t0201_fails_on_empty_timezone() throws JsonProcessingException {
        mapper.readValue(INVALID_JSON_NO_TIMEZONE, Interval.class);
    }
    @Test(expected = JsonParseException.class)
    public void t0202_fails_on_empty_start() throws JsonProcessingException {
        mapper.readValue(INVALID_JSON_NO_START, Interval.class);
    }
    @Test(expected = JsonParseException.class)
    public void t0203_fails_on_empty_end() throws JsonProcessingException {
        mapper.readValue(INVALID_JSON_NO_END, Interval.class);
    }
    @Test(expected = JsonParseException.class)
    public void t0204_fails_on_invalid_start() throws JsonProcessingException {
        mapper.readValue(INVALID_JSON_INVALID_START, Interval.class);
    }
    @Test(expected = JsonParseException.class)
    public void t0205_fails_on_invalid_end() throws JsonProcessingException {
        mapper.readValue(INVALID_JSON_INVALID_END, Interval.class);
    }
    @Test(expected = JsonParseException.class)
    public void t0206_fails_on_invalid_type() throws JsonProcessingException {
        mapper.readValue(INVALID_JSON_INVALID_TYPE, Interval.class);
    }

    // endregion

}
