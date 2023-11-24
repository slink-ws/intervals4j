package ws.slink.intervals;

/**
 * Shift-aware `Month` implementation of Interval interface
 */
public class ShiftedMonth extends ShiftedInterval {

    private ShiftedMonth(Interval month, String shift) {
        super(month, shift);
    }

    public static ShiftedMonth of(Month month, String shift) {
        return new ShiftedMonth(month, shift);
    }
    private static ShiftedMonth create(Interval month, String shift) {
        return new ShiftedMonth(month, shift);
    }

    @Override
    public Interval previous() {
        return ShiftedMonth.create(
            new IntervalBuilder()
                .start(this.base.getStart().minusMonths(1))
                .end(this.base.getEnd().minusMonths(1))
                .timezone(this.base.timezone())
                .build(),
            this.shift
        );
    }
    @Override
    public Interval withPrevious() {
        return ShiftedMonth.create(
            new IntervalBuilder()
                .start(this.base.getStart().minusMonths(1))
                .end(this.base.getEnd())
                .timezone(this.base.timezone())
                .build(),
            this.shift
        );
    }

}
