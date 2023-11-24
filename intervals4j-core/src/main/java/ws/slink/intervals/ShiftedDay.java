package ws.slink.intervals;

/**
 * Shift-aware `Month` implementation of Interval interface
 */
public class ShiftedDay extends ShiftedInterval {

    private ShiftedDay(Interval month, String shift) {
        super(month, shift);
    }

    public static ShiftedDay of(Day day, String shift) {
        return new ShiftedDay(day, shift);
    }
    private static ShiftedDay create(Interval day, String shift) {
        return new ShiftedDay(day, shift);
    }

    @Override
    public Interval previous() {
        return ShiftedDay.create(
            new IntervalBuilder()
                .start(this.base.getStart().minusDays(1))
                .end(this.base.getEnd().minusDays(1))
                .timezone(this.base.timezone())
                .build(),
            this.shift
        );
    }
    @Override
    public Interval withPrevious() {
        return ShiftedDay.create(
            new IntervalBuilder()
                .start(this.base.getStart().minusDays(1))
                .end(this.base.getEnd())
                .timezone(this.base.timezone())
                .build(),
            this.shift
        );
    }

}
