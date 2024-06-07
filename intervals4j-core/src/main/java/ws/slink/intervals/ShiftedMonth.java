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
    public Interval withPrevious() {
        Interval previous = this.getBase().previous();
        return ShiftedMonth.create(
            new IntervalBuilder()
                .start(previous.getStart())
                .end(this.base.getEnd())
                .timezone(this.base.timezone())
                .build(),
            this.shift
        );
    }

}
