## Intervals4j

Java library to operate with time intervals.

Supported intervals are:
- Custom interval with arbitrary start/end time
- Year
- Month
- Day

### Including as a dependency
```xml
<dependency>
    <groupId>ws.slink</groupId>
    <artifactId>intervals4j-core</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Usage Examples

#### Create Interval With Builder
- custom interval
```java
    Interval interval = new IntervalBuilder()
        .timezone(TimeZone.getTimeZone("UTC"))
        .start(LocalDateTime.of(2023, 10, 15, 10, 15, 0, 0))
        .end(LocalDateTime.of(2023, 10, 15, 16, 15, 0, 0))
        .build();
    System.out.println(interval);
```

- month interval
```java
    // create interval of October 2023 in "India Standard Time" timezone
    Month month = IntervalBuilder.month(2023, 10, "IST");
    // prints:
    // 2023-10-01 00:00:00.000 - 2023-10-31 23:59:59.999 IST 
```

- month interval with offset

offset allows to shift original interval bounds 
```java
    // create interval of October 2023 in "India Standard Time" timezone
    Month monthShiftPlus10 = IntervalBuilder.month(2023, 10, "IST", 10);
    System.out.println(monthShiftPlus10);
    // prints:
    // 2023-10-01 10:00:00.000 - 2023-11-01 09:59:59.999 IST

    // create interval of October 2023 in "India Standard Time" timezone
    Month monthShiftMinus10 = IntervalBuilder.month(2023, 10, "IST", -10);
    System.out.println(monthShiftMinus10);
    // prints:
    // 2023-09-30 14:00:00.000 - 2023-10-31 13:59:59.999 IST
```

- year interval
```java
    Year yearInUtc = IntervalBuilder.year(2023);
    System.out.println(yearInUtc);
    // prints:
    // 2023-01-01 00:00:00.000 - 2023-12-31 23:59:59.999 UTC
```

- day interval
```java
    Day dayInUtcShifted = IntervalBuilder.day(2023, 10, 15, -3);
    System.out.println(dayInUtcShifted);
    // prints:
    // 2023-10-14 21:00:00.000 - 2023-10-15 20:59:59.999 UTC
```

#### Parse interval from string
(only generic Interval can be received as a result of parsing procedure)
```java
    Interval interval = IntervalBuilder.parse("2023");
    System.out.println(interval);
    // prints:
    // 2023-01-01 00:00:00.000 - 2023-12-31 23:59:59.999 UTC
```

- parse with timezone
```java
    Interval interval = IntervalBuilder.parse("2023-10", "IST");
    System.out.println(interval);
    // prints:
    // 2023-10-01 00:00:00.000 - 2023-10-31 23:59:59.999 IST
```

- parse with offset
```java
    Interval interval = IntervalBuilder.parse("2023-10-15", 10);
    System.out.println(interval);
    // prints:
    // 2023-10-15 10:00:00.000 - 2023-10-16 09:59:59.999 UTC
```

- parse with offset and timezone
```java
    Interval interval = IntervalBuilder.parse("2023-10-15", "IST", 10);
    System.out.println(interval);
    // prints:
    // 2023-10-15 10:00:00.000 - 2023-10-16 09:59:59.999 IST
```

#### Check if interval contains time point
```java
    Interval interval = IntervalBuilder.parse("2023-10-15", "IST", 10);
    Interval check = IntervalBuilder.parse("2023-10-16", "IST");
    System.out.println(interval.contains(check.start()));
    System.out.println(interval.contains(check.end()));
    // prints:
    // true
    // false

```

### Serialization
For serialization purposes one can use intervals4j serialization libarary (based on Jackson)
```xml
    <dependency>
        <groupId>ws.slink</groupId>
        <artifactId>intervals4j-jackson</artifactId>
        <version>0.0.2</version>
    </dependency>
```

```java
    Interval interval = Month.of("2023-10");
    new IntervalSerializer().
    Interval check = IntervalBuilder.parse("2023-10-16", "IST");
    System.out.println(interval.contains(check.start()));
    System.out.println(interval.contains(check.end()));
    // prints:
    // {
    //    "timezone": "UTC",
    //    "start": "2023-10-01 00:00:00.000",
    //    "end": "2023-10-31 23:59:59.999",
    //    "type": "month"
    // }

```
